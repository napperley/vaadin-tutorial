package org.example.vaadintutorial.model

import java.time.LocalDate
import java.util.ArrayList
import java.util.Random
import java.util.logging.Level
import java.util.logging.Logger

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 *
 * In demos/tutorials/examples, get a reference to this service class with [CustomerService.instance].
 */
class CustomerService {
    private val contacts = mutableMapOf<Long, Customer>()
    @Suppress("Destructure")
    private val customerSorter = Comparator<Customer> { c1: Customer, c2: Customer ->
        val id1 = c1.id ?: 0
        val id2 = c2.id ?: 0

        id2.toInt() - id1.toInt()
    }
    private var nextId: Long = 0

    init {
        ensureTestData()
    }

    /**
     * Finds all Customer's that match given filter.
     * @param stringFilter filter that returned objects should match or empty string if all objects should be
     * returned.
     * @return list a Customer objects
     */
    @Synchronized fun findAll(stringFilter: String = ""): List<Customer> {
        val arrayList = mutableListOf<Customer>()

        for (contact in contacts.values) {
            val passesFilter = stringFilter.isEmpty() || contact.toString().toLowerCase().contains(
                stringFilter.toLowerCase())

            if (passesFilter) arrayList += contact.copy()
        }
        arrayList.sortWith(customerSorter)
        return arrayList
    }

    /**
     * Finds all Customer's that match given filter and limits the resultset.
     * @param stringFilter filter that returned objects should match or empty string if all objects should be
     * returned.
     * @param start the index of first result
     * @param maxresults maximum result count
     * @return list a Customer objects
     */
    @Synchronized fun findAll(stringFilter: String = "", start: Int, maxresults: Int): List<Customer> {
        val arrayList = ArrayList<Customer>()

        for (contact in contacts.values) {
            val passesFilter = stringFilter.isEmpty() || contact.toString().toLowerCase().contains(
                stringFilter.toLowerCase())
            if (passesFilter) arrayList += contact.copy()
        }

        arrayList.sortWith(customerSorter)
        var end = start + maxresults
        if (end > arrayList.size) {
            end = arrayList.size
        }
        return arrayList.subList(start, end)
    }

    /**
     * @return the amount of all customers in the system
     */
    @Synchronized fun count(): Long {
        return contacts.size.toLong()
    }

    /**
     * Deletes a customer from a system
     * @param value the Customer to be deleted
     */
    @Synchronized fun delete(value: Customer) {
        contacts.remove(value.id)
    }

    /**
     * Persists or updates customer in the system. Also assigns an identifier
     * for new Customer instances.
     * @param entry
     */
    @Synchronized fun save(entry: Customer?) {
        var tmpEntry = entry

        if (tmpEntry == null) {
            LOGGER.log(Level.SEVERE,
                "Customer is null. Are you sure you have connected your form to the application as described in " +
                    "tutorial chapter 7?")
            return
        }
        if (tmpEntry.id == null) tmpEntry.id = nextId++

        try {
            tmpEntry = tmpEntry.copy()
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
        contacts[tmpEntry.id ?: 0L] = tmpEntry
    }

    /**
     * Sample data generation
     */
    fun ensureTestData() {
        if (findAll().isEmpty()) {
            val names = arrayOf(
                "Gabrielle Patel",
                "Brian Robinson",
                "Eduardo Haugen",
                "Koen Johansen",
                "Alejandro Macdonald",
                "Angel Karlsson",
                "Yahir Gustavsson",
                "Haiden Svensson",
                "Emily Stewart",
                "Corinne Davis",
                "Ryann Davis",
                "Yurem Jackson",
                "Kelly Gustavsson",
                "Eileen Walker",
                "Katelyn Martin",
                "Israel Carlsson",
                "Quinn Hansson",
                "Makena Smith",
                "Danielle Watson",
                "Leland Harris",
                "Gunner Karlsen",
                "Jamar Olsson",
                "Lara Martin",
                "Ann Andersson",
                "Remington Andersson",
                "Rene Carlsson",
                "Elvis Olsen",
                "Solomon Olsen",
                "Jaydan Jackson",
                "Bernard Nilsen"
            )
            val r = Random(0)

            for (name in names) {
                val split = name.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val c = Customer()

                c.firstName = split[0]
                c.lastName = split[1]
                c.email = split[0].toLowerCase() + "@" + split[1].toLowerCase() + ".com"
                c.status = CustomerStatus.values()[r.nextInt(CustomerStatus.values().size)]
                val daysOld = 0 - r.nextInt(365 * 15 + 365 * 60)
                c.birthDate = LocalDate.now().plusDays(daysOld.toLong())
                save(c)
            }
        }
    }

    companion object {
        val instance by lazy { CustomerService() }
        val LOGGER: Logger = Logger.getLogger(CustomerService::class.java.name)
    }
}