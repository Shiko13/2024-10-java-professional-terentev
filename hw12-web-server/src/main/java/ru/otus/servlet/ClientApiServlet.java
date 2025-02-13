package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.jpql.crm.model.Address;
import ru.otus.jpql.crm.model.Client;
import ru.otus.jpql.crm.model.Phone;
import ru.otus.jpql.crm.service.DBServiceClient;
import ru.otus.mappers.ClientMapper;
import ru.otus.model.ClientDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"java:S1989"})
public class ClientApiServlet extends HttpServlet {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_ADDRESS = "address";
    private static final String PARAM_PHONES = "phones";
    private final DBServiceClient dbServiceClient;
    private final Gson gson;


    public ClientApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        List<ClientDto> clients = dbServiceClient.findAll()
                .stream()
                .map(ClientMapper::toDto)
                .toList();
        String res = gson.toJson(clients);
        out.print(res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(PARAM_NAME);
        String address = request.getParameter(PARAM_ADDRESS);
        String phonesParam = request.getParameter(PARAM_PHONES);
        List<Phone> phones = Arrays.stream(phonesParam.split(",")).map(it -> new Phone(null, it)).collect(Collectors.toList());
        dbServiceClient.saveClient(new Client(null, name, new Address(null, address), phones));
    }
}
