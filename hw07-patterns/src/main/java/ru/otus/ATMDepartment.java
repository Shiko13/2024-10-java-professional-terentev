package ru.otus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMDepartment {

    private final List<ATM> atms = new ArrayList<>();
    private final Map<ATM, ATMMemento> initialStates = new HashMap<>();
    private final List<ATMCommand> commandHistory = new ArrayList<>();

    public void addATM(ATM atm) {
        atms.add(atm);
        initialStates.put(atm, atm.saveState());
    }

    public int collectTotalBalance() {
        return atms.stream().mapToInt(ATM::getBalance).sum();
    }

    public void executeCommand(ATMCommand command) {
        command.execute();
        commandHistory.add(command);
    }

    public void restoreAll() {
        for (ATM atm : atms) {
            ATMMemento memento = initialStates.get(atm);
            if (memento != null) {
                executeCommand(new RestoreStateCommand(atm, memento));
            }
        }
    }
}
