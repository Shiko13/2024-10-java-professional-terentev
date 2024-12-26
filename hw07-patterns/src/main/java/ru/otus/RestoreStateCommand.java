package ru.otus;

public class RestoreStateCommand implements ATMCommand {

    private final ATM atm;
    private final ATMMemento memento;

    public RestoreStateCommand(ATM atm, ATMMemento memento) {
        this.atm = atm;
        this.memento = memento;
    }

    @Override
    public void execute() {
        atm.restoreState(memento);
    }
}
