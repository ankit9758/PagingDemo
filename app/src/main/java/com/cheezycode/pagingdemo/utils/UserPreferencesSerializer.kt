package com.cheezycode.pagingdemo.utils

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.cheezycode.pagingdemo.UserPreference
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreference> {
    override val defaultValue: UserPreference = UserPreference.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserPreference {
        try {
            return UserPreference.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserPreference, output: OutputStream) = t.writeTo(output)
}