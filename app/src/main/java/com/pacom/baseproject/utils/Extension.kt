package com.pacom.baseproject.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pacom.baseproject.R
import org.json.JSONObject
import java.util.*

fun Context.informationScreen(){
    val metrics = resources.displayMetrics
    val dpHeight: Float = metrics.heightPixels / metrics.density
    val dpWidth: Float = metrics.widthPixels / metrics.density
    Log.d("TAG", "informationScreen: ${metrics.density}")
    Log.d("TAG", "informationScreen: ${dpWidth} - $dpHeight")
}

fun <T> T.parseGson(): String{
    return Gson().toJson(this)
}

fun <T>T.convertJsonObject(): JSONObject {
    return try {
        JSONObject(this.parseGson())
    } catch (e: Exception) {
        JSONObject()
    }
}

inline fun <reified T> Gson.fromJson(json: String): T = fromJson<T>(json, object : TypeToken<T>(){}.type)

fun Float.dpToPx(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    )
}

fun Float.spToPx(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        context.resources.displayMetrics
    )
}

fun Float.dpToSp(context: Context): Float {
    return (this.dpToPx(context) / context.resources.displayMetrics.scaledDensity)
}

fun Context.screenWidth(): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun Context.screenHeight(): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun View.setOnSingleClickListener(onClickListener: ()-> Unit){
    this.setOnClickListener(object: OnSingleClickListener(){
        override fun onSingleClick(v: View?) {
            onClickListener.invoke()
        }
    })
}

internal fun View.setWidth(width: Int) {
    layoutParams.apply {
        this.width = width
        requestLayout()
    }
}

internal val ViewPager2.isNotEmpty: Boolean get() = (adapter?.itemCount ?: 0) > 0
internal val ViewPager2?.isEmpty: Boolean get() = this?.adapter?.itemCount == 0

internal fun Context.getThemePrimaryColor(): Int {
    val value = TypedValue()
    this.theme.resolveAttribute(R.attr.colorPrimary, value, true)
    return value.data
}

fun Int.convertSecondToTime(): String{
    val minute = this/60
    val second = this%60
    return "$minute:$second"
}