@file:OptIn(ExperimentalPagingApi::class)

package com.cheezycode.pagingdemo.ui.activity

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.cheezycode.pagingdemo.R
import com.cheezycode.pagingdemo.databinding.ActivityLoginBinding
import com.cheezycode.pagingdemo.paging.QuoteViewModel
import com.cheezycode.pagingdemo.utils.UserManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var userManager: UserManager
    lateinit var quoteViewModel: QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        // Get reference to our userManager class
        userManager = UserManager(this)
        quoteViewModel = ViewModelProvider(this)[QuoteViewModel::class.java]
        clickEvents()


    }

    private fun observeData() {
//        this.userManager.userData.asLiveData().observe(this) {
//           binding.tvGetData.text= "${it.asMap().get( UserManager.USER_PHONE_KEY)},${it.asMap().get( UserManager.USER_EMAIL_KEY)}"
//                it.asMap().entries
//        }
        lifecycleScope.launchWhenStarted {
            quoteViewModel.readToLocal.collect {
                binding.tvGetData.text = "Hello m${it.email},${it.phone}"

            }
        }







    }

    @OptIn(ExperimentalPagingApi::class)
    private fun clickEvents() {
        binding.buttonSave.setOnClickListener {
            saveDataToProtoDataStore(
                age = 15,
                email = binding.editTextTextEmailAddress.text.toString(),
                phone = binding.editTextPhone.text.toString()
            )
//            saveSharedPreference(
//                binding.editTextTextEmailAddress.text.toString(),
//                binding.editTextPhone.text.toString()
//            )
//            saveEncryptedSharedPreference( binding.editTextTextEmailAddress.text.toString(),
//                binding.editTextPhone.text.toString())
//            startActivity(Intent(this, MainActivity::class.java))

            // saveDataToDataStore(age = 15, email = binding.editTextTextEmailAddress.text.toString() , phone =  binding.editTextPhone.text.toString())
        }
        binding.buttonGetData.setOnClickListener {
            // binding.tvGetData.text=getEncyptedSharedPreference().getString("Email","")+","+getEncyptedSharedPreference().getString("Phone","")
            observeData()
        }
    }

    private fun saveDataToDataStore(age: Int, email: String, phone: String) {
//        lifecycleScope.launch {
//            userManager.storeUser(age, "Ankit 15",email,phone)
//        }

    }

    private fun saveDataToProtoDataStore(age: Int, email: String, phone: String) {
//        lifecycleScope.launch {
//            userManager.storeUser(age, "Ankit 15",email,phone)
//        }

        quoteViewModel.writeToLocal("Ankit 15", email, age, phone)

    }

    private fun saveSharedPreference(email: String, phone: String) {
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        preference.edit().apply {
            putString("Name", "Ankit Singh")
            putString("Email", email)
            putString("Phone", phone)
            putInt("Age", 26)
        }.also {
            it.apply()
            it.commit()
        }
    }


    private fun saveEncryptedSharedPreference(email: String, phone: String) {
        getEncyptedSharedPreference().edit {
            putString("Name", "Ankit Singh")
            putString("Email", email)
            putString("Phone", phone)
            putInt("Age", 26).apply()
        }

    }

    private fun getEncyptedSharedPreference(): SharedPreferences {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "secure_pref_ankit",
            masterKey,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


//        val  readToLocal: Flow<Users>=context.
//    private val Context.userPreferencesStore: DataStore<UserPreference> by dataStore(
//        fileName = DATA_STORE_FILE_NAME, serializer = UserPreferencesSerializer
//    )
//
//    suspend fun writeToLocal(name: String, email: String, age: Int, phone: String) =
//        userPreferencesStore.updateData { user ->
//            user.toBuilder().setEmail(email).setName(name).setPhone(phone).setAge(age).build()
//        }

}


