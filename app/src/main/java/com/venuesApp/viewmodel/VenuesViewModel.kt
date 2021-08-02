package com.venuesApp.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.*
import com.venuesApp.data.VenuesRepository
import com.venuesApp.data.model.Venue
import com.venuesApp.data.model.VenueWithDetails
import com.venuesApp.utils.Resource
import com.venuesApp.utils.SharedPreferenceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val venuesRepository: VenuesRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _networkCheck: MutableLiveData<Boolean> = MutableLiveData()

    val selectedValue = MutableLiveData<Venue>()

    fun selectVenue(item: Venue) {
        selectedValue.value = item
    }

    init {
        if (!sharedPreferences.contains(SharedPreferenceKeys.ClientId.name)) {
            sharedPreferences.edit {
                this.putString(
                    SharedPreferenceKeys.ClientId.name,
                    "EK2XFUL55GKQKPXK3XTTO3AKL1SAND1VFYJHD0CQQGN3YB3H"
                )
                this.putString(
                    SharedPreferenceKeys.ClientSecret.name,
                    "5BJWV2L3OB0MDBZIRP1MRC420FA0M1ILJOPOWUFNI02BEQQ2"
                )
                this.putString(SharedPreferenceKeys.Version.name, "20210720")
                this.putString(SharedPreferenceKeys.LongLat.name, "51.9244,4.4777")
                this.putInt(SharedPreferenceKeys.Radius.name, 1000)
                this.putInt(SharedPreferenceKeys.Limit.name, 10)
            }
        }
    }

    val _venues: LiveData<Resource<List<Venue>>> = venuesRepository.getVenues(
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
                Resource.error(it.message ?: "Error", null)
            }
        }
    }.asLiveData(viewModelScope.coroutineContext)

    fun venueDetails(venueId: String): LiveData<Resource<VenueWithDetails>> {
        return venuesRepository.getVenueWithDetails(
            venueId,
            sharedPreferences.getString(SharedPreferenceKeys.ClientId.name, "") ?: "",
            sharedPreferences.getString(SharedPreferenceKeys.ClientSecret.name, "") ?: "",
            sharedPreferences.getString(SharedPreferenceKeys.Version.name, "") ?: ""
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

    fun setNetworkStatus(status: Boolean) {
        _networkCheck.value = status
    }

    fun getNetworkStatus(): LiveData<Boolean> {
        return _networkCheck
    }

    fun getVenuesByTitle(venuesByTitle: String): LiveData<List<Venue>?> {
        return venuesRepository.searchVenuesByTitle(venuesByTitle)
    }
}