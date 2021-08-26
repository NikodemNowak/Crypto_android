package com.nikodem.crypto.repositories

import android.content.SharedPreferences
import androidx.core.content.edit

interface UserRepository {
    var username: String?
    var favoriteCoins: MutableSet<String>
}

class SharedPrefUserRepository(
    private val sharedPreferences: SharedPreferences
) : UserRepository {
    override var username: String?
        get() = sharedPreferences.getString("username", null)
        set(value) {
            sharedPreferences.edit {
                putString("username", value)
            }
        }
    override var favoriteCoins: MutableSet<String>
        get() = sharedPreferences.getStringSet("favoriteCoins", mutableSetOf())!!
        set(value) {
            sharedPreferences.edit {
                putStringSet("favoriteCoins", value)
            }
        }
}
