package com.example.toway.viewModels

import com.google.android.gms.maps.model.LatLng

data class Order(
    val id: String? = null,
    val customerName: String? = null,
//    val location: LatLng? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val plateNumber: String? = null,
    val phoneNumber: String? = null,
    val problem: String? = null
)
