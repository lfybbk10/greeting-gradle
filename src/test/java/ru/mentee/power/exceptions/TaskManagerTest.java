package ru.mentee.power.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class TaskManagerTest {

    private TaskManager account;
    private static final double INITIAL_BALANCE = 1000.0;
    private static final String ACCOUNT_ID = "ACC-123";

    @BeforeEach
    void setUp() {
        account = new TaskManager(ACCOUNT_ID, INITIAL_BALANCE);
    }

    @Test
    @DisplayName("Конструктор должен правильно устанавливать начальный баланс и ID")
    void constructorShouldSetInitialBalanceAndId() {
        assertThat(account.getBalance()).isEqualTo(INITIAL_BALANCE);
        assertThat(account.getId()).isEqualTo(ACCOUNT_ID);
    }

    @Test
    @DisplayName("Конструктор должен выбрасывать IllegalArgumentException при отрицательном балансе")
    void constructorShouldThrowIllegalArgumentExceptionForNegativeBalance() {
        assertThatThrownBy(() -> new TaskManager(ACCOUNT_ID, -1)).isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Начальный баланс не может быть отрицательным");
    }

    // --- Тесты для deposit ---
    @Test
    @DisplayName("Метод deposit должен увеличивать баланс при положительной сумме")
    void depositShouldIncreaseBalanceForPositiveAmount() {
        int depositAmount = 18;

        double oldBalance = account.getBalance();
        account.deposit(depositAmount);
        assertThat(account.getBalance()).isEqualTo(oldBalance + depositAmount);
    }

    @Test
    @DisplayName("Метод deposit должен допускать нулевую сумму")
    void depositShouldAllowZeroAmount() {
        int depositAmount = 0;

        double oldBalance = account.getBalance();
        account.deposit(depositAmount);
        assertThat(account.getBalance()).isEqualTo(oldBalance + depositAmount);
    }

    @Test
    @DisplayName("Метод deposit должен выбрасывать IllegalArgumentException при отрицательной сумме")
    void depositShouldThrowIllegalArgumentExceptionForNegativeAmount() {
        int depositAmount = -10;

        double oldBalance = account.getBalance();

        assertThatThrownBy(() -> account.deposit(depositAmount)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Сумма депозита не может быть отрицательной");
        assertThat(account.getBalance()).isEqualTo(oldBalance);
    }

    // --- Тесты для withdraw ---
    @Test
    @DisplayName("Метод withdraw должен уменьшать баланс при корректной сумме")
    void withdrawShouldDecreaseBalanceForValidAmount() throws TaskValidationException {
        int withdrawAmount = 20;
        double oldBalance = account.getBalance();
        account.withdraw(withdrawAmount);
        assertThat(account.getBalance()).isEqualTo(oldBalance - withdrawAmount);
    }

    @Test
    @DisplayName("Метод withdraw должен позволять снять полный баланс")
    void withdrawShouldAllowWithdrawingFullBalance() throws TaskValidationException {
        double withdrawAmount = account.getBalance();
        account.withdraw(withdrawAmount);
        assertThat(account.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Метод withdraw должен допускать нулевую сумму")
    void withdrawShouldAllowZeroAmount() throws TaskValidationException {
        double withdrawAmount = 0;
        double oldBalance = account.getBalance();
        account.withdraw(withdrawAmount);
        assertThat(account.getBalance()).isEqualTo(oldBalance);
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать IllegalArgumentException при отрицательной сумме")
    void withdrawShouldThrowIllegalArgumentExceptionForNegativeAmount() {
        int withdrawAmount = -10;
        double oldBalance = account.getBalance();
        assertThatThrownBy(() -> account.withdraw(withdrawAmount)).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Сумма снятия не может быть отрицательной");
        assertThat(account.getBalance()).isEqualTo(oldBalance);
    }

    @Test
    @DisplayName("Метод withdraw должен выбрасывать TaskValidationException при превышении баланса")
    void withdrawShouldThrowTaskValidationExceptionWhenAmountExceedsBalance() {
        double withdrawAmount = account.getBalance() + 100;
        double oldBalance = account.getBalance();
        String message = "Недостаточно средств на счете "+ account.getId() + " для снятия ";
        assertThatThrownBy(() -> account.withdraw(withdrawAmount)).isInstanceOf(TaskValidationException.class)
                .hasMessageContaining(message);
        assertThat(account.getBalance()).isEqualTo(oldBalance);
    }
}