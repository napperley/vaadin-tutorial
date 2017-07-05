package com.example.vaadintutorial.model

import java.io.Serializable
import java.time.LocalDate

/**
 * A entity object, like in any other Java application. In a typical real world
 * application this could for example be a JPA entity.
 */
data class Customer(
    var id: Long? = null,
    var firstName: String = "",
    var lastName: String = "",
    var birthDate: LocalDate? = null,
    var status: CustomerStatus? = null,
    var email: String = ""
) : Serializable {
    val isPersisted: Boolean
        get() = id != null

    override fun toString(): String = "$firstName $lastName"
}