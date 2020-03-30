package ua.com.cuteteam.cutetaxiproject.activities

import android.app.Service
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_map_driver.*
import ua.com.cuteteam.cutetaxiproject.R
import ua.com.cuteteam.cutetaxiproject.data.entities.Order
import ua.com.cuteteam.cutetaxiproject.extentions.showInfoSnackBar
import ua.com.cuteteam.cutetaxiproject.fragments.adapters.OrdersAdapter
import ua.com.cuteteam.cutetaxiproject.repositories.DriverRepository
import ua.com.cuteteam.cutetaxiproject.services.ACCEPTED_ORDER_ID
import ua.com.cuteteam.cutetaxiproject.services.DriverService
import ua.com.cuteteam.cutetaxiproject.shPref.AppSettingsHelper
import ua.com.cuteteam.cutetaxiproject.viewmodels.BaseViewModel
import ua.com.cuteteam.cutetaxiproject.viewmodels.DriverViewModel

class DriverActivity : BaseActivity(), OrdersAdapter.OnOrderAccept {

    private val model by lazy {
        ViewModelProvider(this, BaseViewModel.getViewModelFactory(DriverRepository()))
            .get(DriverViewModel::class.java)
    }

    override val menuResId: Int get() = R.menu.nav_menu_settings_driver
    override val layoutResId: Int get() = R.layout.activity_driver
    override val service: Class<out Service>
        get() = DriverService::class.java

    override fun onNetworkAvailable() = show()
    override fun onNetworkLost() = hide()
    override fun onHasActiveOrder(orderId: String?) {
        model.subscribeOnOrder(orderId)
        navController.setGraph(R.navigation.nav_graph_driver)
    }

    override fun onNoActiveOrder() {
        navController.navigate(R.id.action_home_to_new_orders)
    }

    override fun onAccept(order: Order) {
        order.carInfo = with(AppSettingsHelper(this)) {
            getString(
                R.string.order_message_from_dirver,
                carBrand,
                carModel,
                carColor,
                carNumber,
                order.arrivingTime
            )
        }
        model.obtainOrder(order)
        navController.navigate(R.id.action_new_orders_to_home)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        super.onSharedPreferenceChanged(sharedPreferences, key)
        when (key) {
            getString(R.string.key_send_notifications_preference) -> stopService()
            else -> model.updateOrders()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(ACCEPTED_ORDER_ID)?.let {
            onHasActiveOrder(it)
        }
    }

    private fun show() {
        restoreStateVisibility()
    }


    private fun hide() {
        saveStateVisibility()
        info_boxes?.visibility = View.GONE
        bottom_sheet?.visibility = View.GONE
        btn_orders_list?.visibility = View.GONE
        cart_badge?.visibility = View.GONE

        container?.let {
            showInfoSnackBar(it, "No internet connection!")
        }
    }

    private fun saveStateVisibility(){
        val map = mapOf(
            info_boxes to info_boxes.visibility,
            bottom_sheet to bottom_sheet.visibility,
            btn_orders_list to btn_orders_list.visibility,
            cart_badge to cart_badge.visibility
        )
        model.mapOfVisibility = map
    }

    private fun restoreStateVisibility(){
        val map = model.mapOfVisibility ?: return
        info_boxes?.visibility = map.getValue(info_boxes)
        bottom_sheet?.visibility = map.getValue(bottom_sheet)
        btn_orders_list?.visibility = map.getValue(btn_orders_list)
        cart_badge?.visibility = map.getValue(cart_badge)
    }

}