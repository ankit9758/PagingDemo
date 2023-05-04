package com.cheezycode.pagingdemo.utils

import android.content.Context
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {
//
//    // Create the dataStore and give it a name same as shared preferences
//    private val dataStore = context.createDataStore(name = "user_prefs")
//
//    // Create some keys we will use them to store and retrieve the data
//    companion object {
//        val USER_EMAIL_KEY = preferencesKey<String>("USER_EMAIL")
//        val USER_PHONE_KEY = preferencesKey<String>("USER_PHONE")
//        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")
//        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
//    }
//
//    // Store user data
//    // refer to the data store and using edit
//    // we can store values using the keys
//    suspend fun storeUser(age: Int, name: String, email: String, phone: String) {
//        dataStore.edit {
//            it[USER_EMAIL_KEY] = email
//            it[USER_PHONE_KEY] = phone
//            it[USER_NAME_KEY] = name
//            it[USER_AGE_KEY] = age
//
//            // here it refers to the preferences we are editing
//
//        }
//    }
//
//    // Create an age flow to retrieve age from the preferences
//    // flow comes from the kotlin coroutine
//    val userAgeFlow: Flow<Int> = dataStore.da.map {
//        it[USER_AGE_KEY] ?: 0
//    }
//
//    // Create a name flow to retrieve name from the preferences
//    val userNameFlow: Flow<String> = dataStore.data.map {
//        it[USER_NAME_KEY] ?: ""
//    }
//    val userData: Flow<Preferences> = dataStore.data
}