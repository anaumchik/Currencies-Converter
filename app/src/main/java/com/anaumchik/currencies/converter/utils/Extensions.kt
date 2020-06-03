package com.anaumchik.currencies.converter.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.toolbar

fun Fragment.string(@StringRes stringRes: Int): String = this.getString(stringRes)
fun <T> Fragment.string(@StringRes stringRes: Int, value: T): String = this.getString(stringRes, value)
fun View.string(@StringRes stringRes: Int): String = this.context.getString(stringRes)
fun Context.string(@StringRes stringRes: Int): String = this.getString(stringRes)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(this.context).inflate(layoutRes, this, false)

fun Fragment.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(requireContext()).inflate(layoutRes, null)

fun Fragment.toolbarTitle(@StringRes stringRes: Int) {
    requireActivity().toolbar.title = string(stringRes)
}

fun ImageView.loadDrawable(@DrawableRes drawableRes: Int) = Glide
    .with(this.context)
    .load(drawableRes)
    .fitCenter()
    .into(this)

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()