package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.otus.jpql.crm.model.Client;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"java:S1989"})
public class ClientServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private final TemplateProcessor templateProcessor;

    public ClientServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        Client client = (session != null) ? (Client) session.getAttribute("client") : null;

        Map<String, Object> params = new HashMap<>();
        params.put("client", Objects.requireNonNullElseGet(client, () -> new Client("")));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, params));
    }
}
