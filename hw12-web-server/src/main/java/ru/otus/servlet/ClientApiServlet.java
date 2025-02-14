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
import java.util.List;

@SuppressWarnings({"java:S1989"})
public class ClientApiServlet extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        ClientDto clientDto = gson.fromJson(request.getReader(), ClientDto.class);
        List<Phone> phones = clientDto.getPhones().stream()
                .map(dto -> new Phone(null, dto.getNumber()))
                .toList();

        Address address = new Address(null, clientDto.getAddress());
        dbServiceClient.saveClient(new Client(null, clientDto.getName(), address, phones));
    }
}
