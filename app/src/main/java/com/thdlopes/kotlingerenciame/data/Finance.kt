package com.thdlopes.kotlingerenciame.data

import com.google.firebase.database.Exclude

data class Finance(
    @get:Exclude
    var id: String? = null,
    var name: String? = null,
    var day: String? = null,
    var month: String? = null,
    var year: String? = null,
    var value: String? = null,

    @get:Exclude
    var isDeleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return if(other is Finance){
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (day?.hashCode() ?: 0)
        result = 31 * result + (month?.hashCode() ?: 0)
        result = 31 * result + (year?.hashCode() ?: 0)
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + isDeleted.hashCode()
        return result
    }
}