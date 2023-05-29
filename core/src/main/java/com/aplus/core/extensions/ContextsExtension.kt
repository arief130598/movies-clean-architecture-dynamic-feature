package com.aplus.core.extensions

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.DEVICE_POLICY_SERVICE
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.BoolRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

inline fun <reified T : Activity> Context?.startActivity() =
    this?.startActivity(Intent(this, T::class.java))

inline fun <reified T : Service> Context?.startService() =
    this?.startService(Intent(this, T::class.java))

inline fun <reified T : Activity> Context.startActivityWithAnimation(
    enterResId: Int = 0,
    exitResId: Int = 0
) {
    val intent = Intent(this, T::class.java)
    val bundle = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId).toBundle()
    ContextCompat.startActivity(this, intent, bundle)
}

fun Context?.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, text, duration).show() }

fun Context?.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, textId, duration).show() }

fun Context.getInteger(@IntegerRes id: Int) = resources.getInteger(id)

fun Context.getBoolean(@BoolRes id: Int) = resources.getBoolean(id)

fun Context.getColorResources(@ColorRes id: Int) = ContextCompat.getColor(this, id)

fun Context.getDrawableResources(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

inline val Context.inputManager: InputMethodManager?
    get() = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager

inline val Context.notificationManager: NotificationManager?
    get() = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager

inline val Context.telephonyManager: TelephonyManager?
    get() = getSystemService(TELEPHONY_SERVICE) as? TelephonyManager

inline val Context.devicePolicyManager: DevicePolicyManager?
    get() = getSystemService(DEVICE_POLICY_SERVICE) as? DevicePolicyManager

inline val Context.connectivityManager: ConnectivityManager?
    get() = getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager

inline val Context.alarmManager: AlarmManager?
    get() = getSystemService(ALARM_SERVICE) as? AlarmManager