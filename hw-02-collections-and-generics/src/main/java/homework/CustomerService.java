package homework;

import java.util.*;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return copy(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> nextEntry = map.higherEntry(customer);
        return nextEntry != null ? copy(nextEntry) : null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copy(Map.Entry<Customer, String> entry) {
        return Map.entry(new Customer(entry.getKey().getId(), entry.getKey().getName(),
                entry.getKey().getScores()), entry.getValue());
    }
}
