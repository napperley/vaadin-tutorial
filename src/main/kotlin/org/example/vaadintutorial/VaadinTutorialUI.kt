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
        val clearFilterBtn = Button(FontAwesome.TIMES).apply {
            description = "Clear the current filter"
            addClickListener { filterTxt.clear() }
        }
        val filterLayout = CssLayout().apply {
            addComponents(filterTxt, clearFilterBtn)
            styleName = ValoTheme.LAYOUT_COMPONENT_GROUP
        }
        val subLayout = HorizontalLayout(grid, form).apply {
            setSizeFull()
            setExpandRatio(grid, 1F)
        }

        with(filterTxt) {
            placeholder = "Filter by name..."
            addValueChangeListener { updateList() }
            valueChangeMode = ValueChangeMode.LAZY
        }
        with(grid) {
            setColumns("firstName", "lastName", "email")
            setSizeFull()
        }
        updateList()
        content = VerticalLayout(filterLayout, subLayout)
    }

    fun updateList() {
        val customers = service.findAll(filterTxt.value)

        grid.setItems(customers)
    }
}
