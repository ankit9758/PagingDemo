package com.cheezycode.pagingdemo.paging


import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.paging.*
import com.cheezycode.pagingdemo.User
import com.cheezycode.pagingdemo.UserPreference
import com.cheezycode.pagingdemo.db.QuoteDatabase
import com.cheezycode.pagingdemo.models.Result
import com.cheezycode.pagingdemo.models.Users
import com.cheezycode.pagingdemo.network.QuotesAPI
import com.cheezycode.pagingdemo.utils.Constants.DATA_STORE_FILE_NAME
import com.cheezycode.pagingdemo.utils.UserPreferencesSerializer
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalPagingApi
class QuoteRepository @Inject constructor(
    private val context: Context, private val quotesAPI: QuotesAPI,
    private val quoteDatabase: QuoteDatabase
) {

    private val Context.userPreferencesStore: DataStore<UserPreference> by dataStore(
        fileName = DATA_STORE_FILE_NAME, serializer = UserPreferencesSerializer
    )

    suspend fun writeToLocal(name: String, email: String, age: Int, phone: String) =
        context.userPreferencesStore.updateData { user ->
            user.toBuilder().setEmail(email).setName(name).setPhone(phone).setAge(age).build()
        }

    val readToLocal: Flow<Users> = context.userPreferencesStore.data.catch {
        if (this is java.lang.Exception) {
            Log.e("Eroor-----", it.message.toString())
        }
    }.map {
        Users(it.name, it.email, it.age, it.phone)
    }


    fun getQuotes(): Flow<PagingData<Result>> {
        val pager = Pager(config = PagingConfig(
            pageSize = 20, maxSize = 60
        ), pagingSourceFactory = { QuotePagingSource(quotesAPI) })
        return pager.flow
    }

}