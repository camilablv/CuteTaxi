package ua.com.cuteteam.cutetaxi.activities

import android.app.Service
import androidx.lifecycle.ViewModelProvider
import ua.com.cuteteam.cutetaxi.R
import ua.com.cuteteam.cutetaxi.repositories.PassengerRepository
import ua.com.cuteteam.cutetaxi.services.PassengerService
import ua.com.cuteteam.cutetaxi.viewmodels.PassengerViewModel
import ua.com.cuteteam.cutetaxi.viewmodels.viewmodelsfactories.PassengerViewModelFactory

class PassengerActivity : BaseActivity() {

    override val model by lazy {
        ViewModelProvider(this, PassengerViewModelFactory(PassengerRepository()))
            .get(PassengerViewModel::class.java)
    }

    override val menuResId: Int get() = R.menu.nav_menu_settings_pass
    override val layoutResId: Int get() = R.layout.activity_passenger
    override val service: Class<out Service>
        get() = PassengerService::class.java

    override fun onHasActiveOrder(orderId: String?) {

    }

    override fun onNoActiveOrder() {

    }

    override fun onNetworkAvailable() {

    }

    override fun onNetworkLost() {

    }
}