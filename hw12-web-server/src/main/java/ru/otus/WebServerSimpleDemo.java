package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.jpql.DbServiceFactory;
import ru.otus.jpql.crm.service.DBServiceClient;
import ru.otus.server.UsersWebServerSimple;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

/*
    // Стартовая страница
    http://localhost:8080

    // Страница клиентов
    http://localhost:8080/clients
*/
public class WebServerSimpleDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        DBServiceClient dbServiceClient = DbServiceFactory.getDBServiceClient();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        UserDao userDao = new InMemoryUserDao();
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        UsersWebServerSimple usersWebServer = new UsersWebServerSimple(WEB_SERVER_PORT, dbServiceClient, gson, templateProcessor, authService);

        usersWebServer.start();
        usersWebServer.join();
    }
}
