package ua.com.cuteteam.core.viewmodels

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.FirebaseApp
import getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ua.com.cuteteam.core.providers.AuthProvider
import org.hamcrest.MatcherAssert.assertThat


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class AuthViewModelTest {

    private val authProvider: AuthProvider = Mockito.mock(AuthProvider::class.java)
    private val authViewModel = AuthViewModel(authProvider)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun initFireBase() {
        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun onStarted_stateChangedForEnteringVerificationCode() {
        //Given
        authViewModel.onStarted()

        //When
        val value = authViewModel.state.getOrAwaitValue()

        //Then
        assertThat(value, equalTo(AuthViewModel.State.ENTERING_VERIFICATION_CODE))
    }
}