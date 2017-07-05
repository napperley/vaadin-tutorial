package com.example.vaadintutorial.model

class CustomerComparator : Comparator<Customer> {
    override fun compare(o1: Customer, o2: Customer): Int {
        val id1 = o1.id ?: 0
        val id2 = o2.id ?: 0

        return id2.toInt() - id1.toInt()
    }
}