package com.rice.model

import java.util.HashMap

class Ice {

    var id: String? = null
    var firstName: String? = null
    var lastName: String? = null

    constructor() {}

    constructor(id: String, firstName: String, lastName: String) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
    }

    constructor(firstName: String, lastName: String) {
        this.firstName = firstName
        this.lastName = lastName
    }

    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("firstName", firstName!!)
        result.put("lastName", lastName!!)

        return result
    }
}