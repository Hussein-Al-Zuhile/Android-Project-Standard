package com.hussein.androidprojectstandard.presentation.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object DataStoreManager {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    val Context.examplePreference: JsonPreferenceWrapper<String>
        get() = JsonPreferenceWrapper(
            stringPreferencesKey("example"),
            dataStore,
            String.serializer()
        )

}

open class PreferenceWrapper<T>(
    private val preferenceKey: Preferences.Key<T>,
    private val dataStore: DataStore<Preferences>
) {
    fun preferenceFlow(): Flow<T?> = dataStore.data.map {
        it[preferenceKey]
    }

    fun preferenceValue(): T? = runBlocking {
        dataStore.data.map {
            it[preferenceKey]
        }.first()
    }

    fun editPreference(value: T) = runBlocking {
        dataStore.edit {
            it[preferenceKey] = value
        }
    }

    suspend fun removePreference() = runBlocking {
        dataStore.edit {
            it.remove(preferenceKey)
        }
    }
}


class JsonPreferenceWrapper<T : @Serializable Any>(
    preferenceKey: Preferences.Key<String>,
    dataStore: DataStore<Preferences>,
    private val serializer: KSerializer<T>
) : PreferenceWrapper<String>(preferenceKey, dataStore) {

    companion object {
        private val JsonObject = Json {
            ignoreUnknownKeys = true
        }
    }

    fun preferenceFlowAsObject(): Flow<T?> = preferenceFlow().map {
        it?.let {
            JsonObject.decodeFromString(serializer, it)
        }
    }

    fun preferenceValueAsObject(): T? =
        preferenceValue()?.let {
            JsonObject.decodeFromString(serializer, it)
        }

    fun editPreferenceAsObject(value: T) =
        editPreference(JsonObject.encodeToString(serializer, value))
}
