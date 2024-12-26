package ru.otus;

public class ATMDepartmentApp {

    public static void main(String[] args) {
        ATM atm1 = new ATM(1000);
        ATM atm2 = new ATM(2000);
        ATM atm3 = new ATM(500);

        ATMDepartment department = new ATMDepartment();
        department.addATM(atm1);
        department.addATM(atm2);
        department.addATM(atm3);

        System.out.println("Initial total balance: " + department.collectTotalBalance()); // 3500

        department.executeCommand(new WithdrawCommand(atm1, 200));
        department.executeCommand(new DepositCommand(atm2, 500));
        System.out.println("Total balance after operations: " + department.collectTotalBalance()); // 3800

        department.restoreAll();
        System.out.println("Total balance after restore: " + department.collectTotalBalance()); // 3500
    }
}
