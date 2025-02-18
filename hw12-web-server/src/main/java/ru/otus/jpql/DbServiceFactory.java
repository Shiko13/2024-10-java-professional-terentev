package ru.otus.jpql;

import org.hibernate.cfg.Configuration;
import ru.otus.jpql.core.repository.DataTemplateHibernate;
import ru.otus.jpql.core.repository.HibernateUtils;
import ru.otus.jpql.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.jpql.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.jpql.crm.model.Address;
import ru.otus.jpql.crm.model.Client;
import ru.otus.jpql.crm.model.Phone;
import ru.otus.jpql.crm.service.DBServiceClient;
import ru.otus.jpql.crm.service.DbServiceClientImpl;

public class DbServiceFactory {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static DBServiceClient getDBServiceClient() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Phone.class, Address.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        return new DbServiceClientImpl(transactionManager, clientTemplate);
    }
}
