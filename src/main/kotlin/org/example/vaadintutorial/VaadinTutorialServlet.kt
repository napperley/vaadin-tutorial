package org.example.vaadintutorial

import javax.servlet.annotation.WebInitParam
import javax.servlet.annotation.WebServlet
import com.vaadin.server.VaadinServlet

@WebServlet(
    asyncSupported = false,
    urlPatterns = arrayOf("/*", "/VAADIN/*"),
    initParams = arrayOf(WebInitParam(name = "ui", value = "org.example.vaadintutorial.VaadinTutorialUI"))
)
class VaadinTutorialServlet : VaadinServlet()
