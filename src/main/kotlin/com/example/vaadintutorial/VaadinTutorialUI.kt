package com.example.vaadintutorial

import com.example.vaadintutorial.model.Customer
import com.example.vaadintutorial.model.CustomerService
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.UI
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.ui.Grid
import com.vaadin.ui.VerticalLayout

@Suppress("unused")
@Theme("VaadinTutorial")
@Title("Vaadin Tutorial")
class VaadinTutorialUI : UI() {
    private val service = CustomerService.instance
    private val grid = Grid<Customer>(Customer::class.java)

    override fun init(request: VaadinRequest) {
        val mainLayout = VerticalLayout(grid)

        grid.setColumns("firstName", "lastName", "email")
        updateList()
        content = mainLayout
    }

    fun updateList() {
        val customers = service?.findAll() ?: listOf()

        grid.setItems(customers)
    }
}
