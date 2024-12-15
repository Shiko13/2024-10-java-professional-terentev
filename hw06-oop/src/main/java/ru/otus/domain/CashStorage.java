package ru.otus.domain;

import ru.otus.exception.TransactionException;

import java.util.EnumMap;
import java.util.Map;

public class CashStorage {

    private final Map<Denomination, Integer> storage = new EnumMap<>(Denomination.class);

    public CashStorage() {
        for (Denomination denomination : Denomination.values()) {
            storage.put(denomination, 0);
        }
    }

    public void deposit(Denomination denomination, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }
        storage.put(denomination, storage.get(denomination) + count);
    }

    public Map<Denomination, Integer> withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Map<Denomination, Integer> result = new EnumMap<>(Denomination.class);

        for (Denomination denomination : Denomination.values()) {
            int value = denomination.getValue();
            int available = storage.get(denomination);
            int count = Math.min(amount / value, available);
            if (count > 0) {
                result.put(denomination, count);
                amount -= count * value;
            }
        }

        if (amount > 0) {
            throw new TransactionException("Cannot dispense the requested amount with available banknotes");
        }

        result.forEach((denomination, count) ->
                storage.put(denomination, storage.get(denomination) - count));

        return result;
    }

    public int getBalance() {
        return storage.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getValue() * entry.getValue())
                .sum();
    }

    public Map<Denomination, Integer> getStorageState() {
        return Map.copyOf(storage);
    }
}
