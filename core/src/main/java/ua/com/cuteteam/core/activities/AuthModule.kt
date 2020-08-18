package ua.com.cuteteam.core.activities

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import dagger.Module
import dagger.Provides

@Module
class AuthModule {

    @Provides
    fun providePhoneAuthProvider(): PhoneAuthProvider {
        return PhoneAuthProvider.getInstance()
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}