package com.thdlopes.kotlingerenciame.data

import com.google.firebase.database.Exclude

data class Finance(
    @get:Exclude
    var id: String? = null,
    var name: String? = null,
    var day: String? = null,
    var month: String? = null,
    var year: String? = null
) {

}