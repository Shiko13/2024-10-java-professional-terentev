package ru.otus;

public class WithdrawCommand implements ATMCommand {

    private final ATM atm;
    private final int amount;

    public WithdrawCommand(ATM atm, int amount) {
        this.atm = atm;
        this.amount = amount;
    }

    @Override
    public void execute() {
        atm.withdraw(amount);
    }
}
