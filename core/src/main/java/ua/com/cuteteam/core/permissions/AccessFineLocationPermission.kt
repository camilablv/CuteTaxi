package ua.com.cuteteam.core.permissions

import android.Manifest
import android.content.Context
import ua.com.cuteteam.core.R
import ua.com.cuteteam.core.application.AppClass

data class AccessFineLocationPermission(val context: Context = AppClass.appContext()) : Permission(
    context.getString(R.string.location_permission_rationale),
    Manifest.permission.ACCESS_FINE_LOCATION,
    PermissionProvider.LOCATION_REQUEST_CODE,
    context.getString(R.string.location_permission_required_dialog_title),
    context.getString(R.string.location_permission_required_dialog_message)
)
