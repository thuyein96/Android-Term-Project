package com.example.toway.viewModels

import com.google.android.gms.maps.model.LatLng

data class Order(
    val id: String? = null,
    val customerName: String,
    val location: LatLng? = null,
    val plateNumber: String,
    val phoneNumber: String,
    val problem: String,
    val image: String
)
