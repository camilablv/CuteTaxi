package ua.com.cuteteam.passengerclient.viewmodels

import ua.com.cuteteam.core.viewmodels.MapViewModel
import ua.com.cuteteam.passengerclient.repositories.PassengerRepository

class PassengerVieModel: MapViewModel(PassengerRepository()) {
}