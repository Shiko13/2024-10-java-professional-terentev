package ru.otus.domain;

import java.util.Map;

public class Atm {

    private final CashStorage cashStorage = new CashStorage();

    public void deposit(Denomination denomination, int count) {
        cashStorage.deposit(denomination, count);
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        return cashStorage.withdraw(amount);
    }

    public int getBalance() {
        return cashStorage.getBalance();
    }

    public Map<Denomination, Integer> getStorageState() {
        return cashStorage.getStorageState();
    }
}
