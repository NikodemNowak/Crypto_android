package com.nikodem.crypto.repositories

import android.content.SharedPreferences
import androidx.core.content.edit

interface UserRepository {
    var username: String?
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
}
