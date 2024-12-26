package ru.otus;

public class ATM {

    private int balance;

    public ATM(int initialBalance) {
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds in ATM.");
        }
        balance -= amount;
    }

    public ATMMemento saveState() {
        return new ATMMemento(balance);
    }

    public void restoreState(ATMMemento memento) {
        this.balance = memento.balance();
    }
}
