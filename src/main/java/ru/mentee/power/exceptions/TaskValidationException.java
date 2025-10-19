package ru.mentee.power.exceptions;

/**
 * Собственное проверяемое исключение для ситуации нехватки средств.
 */
public class TaskValidationException extends Exception {
    private final double balance;
    private final double withdrawAmount;

    public TaskValidationException(String message, double balance, double withdrawAmount) {
        super(message);
        this.balance = balance;
        this.withdrawAmount = withdrawAmount;
    }

    public double getBalance() {
        return balance;
    }

    public double getWithdrawAmount() {
        return withdrawAmount;
    }

    public double getDeficit() {
        return withdrawAmount - balance;
    }
}