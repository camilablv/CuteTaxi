package ua.com.cuteteam.cutetaxi.fragments.passenger

interface BottomSheetFragment {

    fun setOnChildDrawnListener(callback: OnChildDrawnListener)
    fun removeOnChildDrawnListener(callback: OnChildDrawnListener)
}