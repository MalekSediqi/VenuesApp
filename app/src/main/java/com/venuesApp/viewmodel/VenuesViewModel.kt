package com.venuesApp.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.venuesApp.data.VenuesRepository
import com.venuesApp.data.model.Venue
import com.venuesApp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VenuesViewModel @Inject constructor(
    private val venuesRepository: VenuesRepository,
    private val sharedPreferences: SharedPreferences) {

    private val _venues: MutableLiveData<Resource<List<Venue>>> = MutableLiveData()

    init {
        sharedPreferences.edit {
            this.putString("client_id","EK2XFUL55GKQKPXK3XTTO3AKL1SAND1VFYJHD0CQQGN3YB3H")
            this.putString("client_secret","5BJWV2L3OB0MDBZIRP1MRC420FA0M1ILJOPOWUFNI02BEQQ2")
            this.putString("v", "20210720")
            this.putString("longLat","51.9244,4.4777")
            this.putInt("radius",1000)
            this.putInt("limit",10)
        }
    }


}