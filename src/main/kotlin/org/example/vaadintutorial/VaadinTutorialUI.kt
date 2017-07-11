@file:Suppress("DEPRECATION")

package org.example.vaadintutorial

import org.example.vaadintutorial.model.Customer
import org.example.vaadintutorial.model.CustomerService
import com.vaadin.server.VaadinRequest
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.server.FontAwesome
import com.vaadin.shared.ui.ValueChangeMode
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme

@Suppress("unused")
@Theme("VaadinTutorial")
@Title("Vaadin Tutorial")
class VaadinTutorialUI : UI() {
    private val service = CustomerService.instance
    private val grid = Grid<Customer>(Customer::class.java)
    private val filterTxt = TextField()
    private val form = CustomerForm(this)

    override fun init(request: VaadinRequest) {
        setupControls()
        updateList()
        content = createLayouts(createControls())["mainLayout"]
        form.isVisible = false
    }

    private fun createLayouts(controls: Map<String, Component>): Map<String, Component> {
        val filterLayout = CssLayout().apply {
            addComponents(filterTxt, controls["clearFilterBtn"])
            styleName = ValoTheme.LAYOUT_COMPONENT_GROUP
        }
        val subLayout = HorizontalLayout(grid, form).apply {
            setSizeFull()
            setExpandRatio(grid, 1F)
        }
        val toolbar = HorizontalLayout(filterLayout, controls["addCustomerBtn"])
        val mainLayout = VerticalLayout(toolbar, subLayout)

        return mapOf("filterLayout" to filterLayout, "subLayout" to subLayout, "toolbar" to toolbar,
            "mainLayout" to mainLayout)
    }

    private fun setupControls() {
        with(filterTxt) {
            placeholder = "Filter by name..."
            addValueChangeListener { updateList() }
            valueChangeMode = ValueChangeMode.LAZY
        }
        with(grid) {
            setColumns("firstName", "lastName", "email")
            setSizeFull()
            asSingleSelect().addValueChangeListener { event ->
                if (event.value == null) form.isVisible = false else form.changeCustomer(event.value)
            }
        }
    }

    private fun createControls(): Map<String, Component> {
        val clearFilterBtn = Button(FontAwesome.TIMES).apply {
            description = "Clear the current filter"
            addClickListener { filterTxt.clear() }
        }
        val addCustomerBtn = Button("Add a new customer").apply {
            addClickListener {
                grid.asSingleSelect().clear()
                form.changeCustomer(Customer())
            }
        }

        return mapOf("clearFilterBtn" to clearFilterBtn, "addCustomerBtn" to addCustomerBtn)
    }

    fun updateList() {
        val customers = service.findAll(filterTxt.value)

        grid.setItems(customers)
    }
}
