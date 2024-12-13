package ru.otus;

import ru.otus.domain.Atm;
import ru.otus.domain.Denomination;
import ru.otus.exception.TransactionException;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Atm atm = new Atm();

        atm.deposit(Denomination.HUNDRED, 10);
        atm.deposit(Denomination.FIFTY, 20);
        atm.deposit(Denomination.TEN, 100);
        System.out.println("Текущий баланс: " + atm.getBalance());

        try {
            Map<Denomination, Integer> withdrawn = atm.withdraw(580);
            System.out.println("Выданные купюры: " + withdrawn);
        } catch (TransactionException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("Текущий баланс после снятия: " + atm.getBalance());
        System.out.println("Состояние хранилища: " + atm.getStorageState());
    }
}