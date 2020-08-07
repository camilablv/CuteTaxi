import ua.com.cuteteam.passengerclient.di.ApplicationComponent
import ua.com.cuteteam.passengerclient.di.DaggerApplicationComponent

open class PassengerApplication {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
    }
}