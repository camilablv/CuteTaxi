package ua.com.cuteteam.passengerclient.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.com.cuteteam.core.fragments.MapsFragment
import ua.com.cuteteam.core.helpers.GoogleMapsHelper
import ua.com.cuteteam.core.viewmodels.MapViewModel
import ua.com.cuteteam.passengerclient.R
import ua.com.cuteteam.passengerclient.viewmodels.PassengerVieModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PassengerMapsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PassengerMapsFragment : MapsFragment() {
    override val viewModel: MapViewModel = PassengerVieModel()

    override fun initMap(googleMapsHelper: GoogleMapsHelper) {
        TODO("Not yet implemented")
    }

}