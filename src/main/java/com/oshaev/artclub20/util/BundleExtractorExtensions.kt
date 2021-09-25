package com.sovcom.util.kt

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Fragment> T.withArgs(block: Bundle.() -> Unit): T {
    return this.apply {
        arguments = Bundle().apply(block)
    }
}

fun <T : Fragment> T.withArgs(key: String, args: Parcelable?, block: (Bundle.() -> Unit)? = null): T {
    return this.apply {
        arguments = Bundle().apply {
            putParcelable(key, args)
            block?.invoke(this)
        }
    }
}

inline fun <reified T> Fragment.args(key: String? = null, defaultValue: T? = null): ReadWriteProperty<Fragment, T> {
    return BundleExtractorDelegate { thisRef, property ->
        val bundleKey = key ?: property.name
        extractFromBundle(thisRef.arguments, bundleKey, defaultValue)
    }
}

inline fun <reified T> Activity.args(key: String? = null, defaultValue: T? = null): ReadWriteProperty<Activity, T> {
    return BundleExtractorDelegate { thisRef, property ->
        val bundleKey = key ?: property.name
        extractFromBundle(thisRef.intent.extras, bundleKey, defaultValue)
    }
}


inline fun <reified T> extractFromBundle(bundle: Bundle?,
                                         key: String? = null,
                                         defaultValue: T? = null): T {

    val result = bundle?.get(key) ?: defaultValue

    if (result != null && result !is T) {
        throw ClassCastException("Property for $key has different class type")
    }

    return result as T
}


class BundleExtractorDelegate<in R, T>(private val initializer: (R, KProperty<*>) -> T) : ReadWriteProperty<R, T> {

    private object EMPTY

    private var value: Any? = EMPTY

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (value == EMPTY) {
            value = initializer(thisRef, property)
        }

        @Suppress("UNCHECKED_CAST")
        return value as T
    }
}

