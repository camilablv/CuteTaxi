package ua.com.cuteteam.core.providers

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: FirebaseUser?)
    fun onFailure(errorCode: String)
    fun onResendCode()
    fun onTimeOut()
}

class AuthProvider @Inject constructor(
    private val phoneAuthProvider: PhoneAuthProvider,
    private val firebaseAuth: FirebaseAuth) {

    companion object {
        const val ERROR_INVALID_PHONE_NUMBER = "ERROR_INVALID_PHONE_NUMBER"
        const val ERROR_INVALID_VERIFICATION_CODE = "ERROR_INVALID_VERIFICATION_CODE"
        const val ERROR_SERVICE_UNAVAILABLE = "ERROR_SERVICE_UNAVAILABLE"
    }

    private lateinit var verificationId: String
    private lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken
    var authListener: AuthListener? = null

    val user get() = firebaseAuth.currentUser

    private val verificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                authListener?.onStarted()
                verificationId = id
                resendingToken = token
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                authListener?.onFailure(exceptionToCode(exception))
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                authListener?.onTimeOut()
            }
        }

    suspend fun verifyCurrentUser(): Boolean {
        return suspendCoroutine {
            firebaseAuth.currentUser?.reload()
                ?.addOnCompleteListener { task -> it.resume(task.isSuccessful) }
                ?: it.resume(false)
        }
    }

    fun signOutUser() = firebaseAuth.signOut()

    fun createCredential(smsCode: String) = PhoneAuthProvider.getCredential(verificationId, smsCode)

    fun resendVerificationCode(phoneNumber: String) {
        phoneAuthProvider.verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            verificationStateChangedCallbacks,
            resendingToken
        )
    }

    fun verifyPhoneNumber(number: String) {
        phoneAuthProvider.verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            verificationStateChangedCallbacks
        )
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(TaskExecutors.MAIN_THREAD, OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    authListener?.onSuccess(user)
                } else {
                    authListener?.onFailure(exceptionToCode(task.exception))
                }
            })
    }

    private fun exceptionToCode(exception: Exception?): String {
        return when(exception) {
            is FirebaseAuthInvalidCredentialsException -> exception.errorCode
            is FirebaseTooManyRequestsException -> ERROR_SERVICE_UNAVAILABLE
            else -> ""
        }
    }
}