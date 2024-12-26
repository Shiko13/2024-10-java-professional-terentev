package ru.otus;

public class DepositCommand implements ATMCommand {

    private final ATM atm;
    private final int amount;

    public DepositCommand(ATM atm, int amount) {
        this.atm = atm;
        this.amount = amount;
    }

    @Override
    public void execute() {
        atm.deposit(amount);
    }
}
