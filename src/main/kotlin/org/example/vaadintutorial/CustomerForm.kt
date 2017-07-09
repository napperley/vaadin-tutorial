package org.example.vaadintutorial

import com.vaadin.data.Binder
import com.vaadin.event.ShortcutAction.KeyCode
import com.vaadin.ui.*
import com.vaadin.ui.themes.ValoTheme
import org.example.vaadintutorial.model.Customer
import org.example.vaadintutorial.model.CustomerService
import org.example.vaadintutorial.model.CustomerStatus


class CustomerForm(val ui: VaadinTutorialUI) : FormLayout() {
    private val firstName = TextField("First name")
    private val lastName = TextField("Last name")
    private val email = TextField("Email")
    private val status = NativeSelect<CustomerStatus>("Status")
    private val birthDate = DateField("Birthday")
    private val save = Button("Save")
    private val delete = Button("Delete")
    private val service = CustomerService.instance
    private lateinit var customer: Customer
    private val binder = Binder(Customer::class.java)

    init {
        val btnLayout = HorizontalLayout(save, delete)

        setSizeUndefined()
        addComponents(firstName, lastName, email, status, birthDate, btnLayout)
        status.setItems(*CustomerStatus.values())
        with(save) {
            styleName = ValoTheme.BUTTON_PRIMARY
            setClickShortcut(KeyCode.ENTER)
            addClickListener { save() }
        }
        binder.bindInstanceFields(this)
        delete.addClickListener { delete() }
    }

    fun changeCustomer(customer: Customer) {
        this.customer = customer
        binder.bean = this.customer
        delete.isVisible = this.customer.persisted
        isVisible = true
        firstName.selectAll()
    }

    fun delete() {
        service.delete(customer)
        ui.updateList()
        isVisible = false
    }

    fun save() {
        service.save(customer)
        ui.updateList()
        isVisible = false
    }
}