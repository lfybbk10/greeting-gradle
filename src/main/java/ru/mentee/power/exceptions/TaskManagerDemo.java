package ru.mentee.power.exceptions;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Демонстрационное приложение для работы с TaskManager.
 */
public class TaskManagerDemo {

    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            TaskManager account = null;
            boolean running = true;
            while (running) {
                System.out.println("Введите ID счёта: ");
                String id = scanner.nextLine();
                int balance = 0;
                System.out.println("Введите начальный баланс: ");
                try {
                    balance = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("Ошибка ввода: Пожалуйста, введите корректное число.");
                    scanner.nextLine(); // Очистить буфер сканера
                    continue; // Перейти к следующей итерации
                }
                catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    continue;
                }

                account = new TaskManager(id, balance);

                System.out.println();
                break; // Пустая строка для разделения
            }

            while (running){
                printMenu();
                int choice = -1;

                try {
                    System.out.print("Выберите действие (1-4): \n");
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Поглотить перевод строки
                } catch (InputMismatchException e) {
                    System.out.println("Ошибка ввода: Пожалуйста, введите номер действия (число).");
                    scanner.nextLine(); // Очистить буфер сканера
                    continue; // Перейти к следующей итерации
                }

                switch (choice) {
                    case 1:
                        try {
                            System.out.println("Введите сумму денег: ");
                            double deposit = scanner.nextDouble();
                            account.deposit(deposit);
                        } catch (InputMismatchException e){
                            System.out.println("Ошибка ввода: Пожалуйста, введите корректное число.");
                            scanner.nextLine(); // Очистить буфер сканера
                            continue; // Перейти к следующей итерации
                        }
                        catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                            continue;
                        }
                        continue;
                    case 2:
                        try {
                            System.out.println("Введите сумму денег: ");
                            double withdraw = scanner.nextDouble();
                            account.withdraw(withdraw);
                        } catch (InputMismatchException e){
                            System.out.println("Ошибка ввода: Пожалуйста, введите корректное число.");
                            scanner.nextLine(); // Очистить буфер сканера
                            continue; // Перейти к следующей итерации
                        }
                        catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                            continue;
                        } catch (TaskValidationException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        continue;
                    case 3:
                        System.out.println("Текущий баланс: "+account.getBalance());
                        continue;
                    case 4:
                        break;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите от 1 до 4.");
                        continue;
                }

                System.out.println("Действие выполнено успешно!");
                break;
            }

        }

        System.out.println("Программа завершена успешно!");
    }

    private static void printMenu() {
        System.out.println("===== МЕНЮ =====");
        System.out.println("1. Внести деньги");
        System.out.println("2. Снять деньги");
        System.out.println("3. Проверить баланс");
        System.out.println("4. Выход");
        System.out.println("==============");
    }
}