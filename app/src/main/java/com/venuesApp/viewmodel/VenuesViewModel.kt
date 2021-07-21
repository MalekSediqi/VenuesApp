package com.venuesApp.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.venuesApp.data.VenuesRepository
import com.venuesApp.data.model.Venue
import com.venuesApp.utils.Resource
import com.venuesApp.utils.SharedPreferenceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val venuesRepository: VenuesRepository,
    sharedPreferences: SharedPreferences
) : ViewModel() {
    companion object {
       val clientId:String = "client_id"

    }

    init {
        if (!sharedPreferences.contains("client_id")) {
            sharedPreferences.edit {
                this.putString("client_id", "EK2XFUL55GKQKPXK3XTTO3AKL1SAND1VFYJHD0CQQGN3YB3H")
                this.putString("client_secret", "5BJWV2L3OB0MDBZIRP1MRC420FA0M1ILJOPOWUFNI02BEQQ2")
                this.putString("v", "20210720")
                this.putString("longLat", "51.9244,4.4777")
                this.putInt("radius", 1000)
                this.putInt("limit", 10)
            }
        }
    }

    private val _venues: LiveData<Resource<List<Venue>>> = venuesRepository.getVenues(
        sharedPreferences.getString(SharedPreferenceKeys.ClientId.name, "") ?: "",
        sharedPreferences.getString(SharedPreferenceKeys.ClientSecret.name, "") ?: "",
        sharedPreferences.getString(SharedPreferenceKeys.Version.name, "") ?: "",
        sharedPreferences.getString(SharedPreferenceKeys.LongLat.name, "") ?: "",
        sharedPreferences.getInt(SharedPreferenceKeys.Radius.name, 1000),
        sharedPreferences.getInt(SharedPreferenceKeys.Limit.name, 10)
    ).map {
        when (it.status) {
            Resource.Status.LOADING -> {
                Resource.loading(null)
            }
            Resource.Status.SUCCESS -> {
                Resource.success(it.data)
            }
            Resource.Status.ERROR -> {
                Resource.error(it.message!!, null)
            }
        }
    }.asLiveData(viewModelScope.coroutineContext)

}