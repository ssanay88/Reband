package com.project.reband

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

    private val jwtTokenKey = stringPreferencesKey("jwtToken")
    private val instrumentKey = stringPreferencesKey("instrument")
    private val nickNameKey = stringPreferencesKey("nickName")
    private val experienceKey = stringPreferencesKey("experience")
    private val bandNoKey = stringPreferencesKey("bandNo")
    private val userGradeKey = stringPreferencesKey("userGrade")
    private val bandNameKey = stringPreferencesKey("bandName")
    private val locationListKey = stringPreferencesKey("locationList")
    private val instrumentListKey = stringPreferencesKey("instrumentList")
    private val hashTagListKey = stringPreferencesKey("hashTagList")

    val jwtToken: Flow<String> = context.dataStore.data
        .catch { exeception ->
            if (exeception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeception
            }
        }.map { preferences ->
            preferences[jwtTokenKey] ?: ""
        }

    suspend fun setJwtToken(jwtToken: String) {
        context.dataStore.edit { preferences ->
            preferences[jwtTokenKey] = jwtToken
        }
    }

    val instrument: Flow<String> = context.dataStore.data
        .catch { exeception ->
            if (exeception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeception
            }
        }.map { preferences ->
            preferences[instrumentKey] ?: ""
        }

    suspend fun setInstrument(instrument: String) {
        context.dataStore.edit { preferences ->
            preferences[instrumentKey] = instrument
        }
    }

    val nickName: Flow<String> = context.dataStore.data
        .catch { exeception ->
            if (exeception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeception
            }
        }.map { preferences ->
            preferences[nickNameKey] ?: ""
        }

    suspend fun setNickName(nickName: String) {
        context.dataStore.edit { preferences ->
            preferences[nickNameKey] = nickName
        }
    }

    val experience: Flow<String> = context.dataStore.data
        .catch { exeception ->
            if (exeception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeception
            }
        }.map { preferences ->
            preferences[experienceKey] ?: ""
        }

    suspend fun setExperience(experience: String) {
        context.dataStore.edit { preferences ->
            preferences[experienceKey] = experience
        }
    }

    val bandNo: Flow<String> = context.dataStore.data
        .catch { exeception ->
            if (exeception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeception
            }
        }.map { preferences ->
            preferences[bandNoKey] ?: ""
        }

    suspend fun setBandNo(bandNo: String) {
        context.dataStore.edit { preferences ->
            preferences[bandNoKey] = bandNo
        }
    }

    val userGrade: Flow<String> = context.dataStore.data
        .catch { exeception ->
            if (exeception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeception
            }
        }.map { preferences ->
            preferences[userGradeKey] ?: "MEMBER"
        }

    suspend fun setUserGrade(userGrade: String) {
        context.dataStore.edit { preferences ->
            preferences[userGradeKey] = userGrade
        }
    }

    val bandName: Flow<String> = context.dataStore.data
        .catch { exeception ->
            if (exeception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeception
            }
        }.map { preferences ->
            preferences[bandNameKey] ?: ""
        }

    suspend fun setBandName(bandName: String) {
        context.dataStore.edit { preferences ->
            preferences[bandNameKey] = bandName
        }
    }

    val locationList: Flow<String> = context.dataStore.data
        .catch { exeption ->
            if (exeption is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeption
            }
        }.map { preferences ->
            preferences[locationListKey] ?: ""
        }

    suspend fun setLocationList(locationList: String) {
        context.dataStore.edit { preferences ->
            preferences[locationListKey] = locationList
        }
    }

    val instrumentList: Flow<String> = context.dataStore.data
        .catch { exeption ->
            if (exeption is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeption
            }
        }.map { preferences ->
            preferences[instrumentListKey] ?: ""
        }

    suspend fun setInstrumentList(instrumentList: String) {
        context.dataStore.edit { preferences ->
            preferences[instrumentListKey] = instrumentList
        }
    }

    val hashTagList: Flow<String> = context.dataStore.data
        .catch { exeption ->
            if (exeption is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeption
            }
        }.map { preferences ->
            preferences[hashTagListKey] ?: ""
        }

    suspend fun setHashTagList(hashTagList: String) {
        context.dataStore.edit { preferences ->
            preferences[hashTagListKey] = hashTagList
        }
    }

}