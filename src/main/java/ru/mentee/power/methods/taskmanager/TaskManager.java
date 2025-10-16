package ru.mentee.power.methods.taskmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс для управления задачами
 */
public class TaskManager {
    private List<Task> tasks;
    private int nextId = 1;

    /**
     * Конструктор
     */
    public TaskManager() {
        tasks = new ArrayList<>();
    }

    /**
     * Добавление задачи с полным набором параметров
     */
    public Task addTask(String title, String description, Date dueDate, Task.Priority priority) {
        Task task = new Task(nextId++, title, description, dueDate, priority);
        tasks.add(task);
        return task;
    }

    /**
     * Добавление задачи только с названием (перегрузка)
     */
    public Task addTask(String title) {
        return addTask(title, "", null, Task.Priority.MEDIUM);
    }

    /**
     * Добавление задачи с названием и описанием (перегрузка)
     */
    public Task addTask(String title, String description) {
        return addTask(title, description, null, Task.Priority.MEDIUM);
    }

    /**
     * Получение задачи по ID
     */
    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    /**
     * Удаление задачи по ID
     */
    public boolean removeTask(int id) {
        Task task = getTaskById(id);
        if (task != null) {
            tasks.remove(task);
            return true;
        }
        return false;
    }

    /**
     * Маркировка задачи как выполненной
     */
    public boolean markTaskAsCompleted(int id) {
        Task task = getTaskById(id);
        if (task != null && !task.isCompleted()) {
            task.markAsCompleted();
            return true;
        }
        return false;
    }

    /**
     * Получение всех задач
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Получение выполненных задач
     */
    public List<Task> getCompletedTasks() {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Получение невыполненных задач
     */
    public List<Task> getIncompleteTasks() {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Получение просроченных задач
     */
    public List<Task> getOverdueTasks() {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isOverdue()) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Получение задач с заданным приоритетом
     */
    public List<Task> getTasksByPriority(Task.Priority priority) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Поиск задач по фрагменту названия или описания
     */
    public List<Task> searchTasks(String query) {
        List<Task> result = new ArrayList<>();
        if (query == null || query.isEmpty()) return result;
        String lower = query.toLowerCase();
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(lower) ||
                    task.getDescription().toLowerCase().contains(lower)) {
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Сортировка задач по сроку выполнения (пузырьком)
     */
    public List<Task> sortTasksByDueDate() {
        List<Task> sorted = new ArrayList<>(tasks);
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size() - i - 1; j++) {
                Date d1 = sorted.get(j).getDueDate();
                Date d2 = sorted.get(j + 1).getDueDate();

                if (d1 == null && d2 != null) continue;
                if (d2 == null || (d1 != null && d1.after(d2))) {
                    Task temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }
        return sorted;
    }

    /**
     * Сортировка задач по приоритету (вставками)
     */
    public List<Task> sortTasksByPriority() {
        List<Task> sorted = new ArrayList<>(tasks);
        for (int i = 1; i < sorted.size(); i++) {
            Task key = sorted.get(i);
            int j = i - 1;
            while (j >= 0 && sorted.get(j).getPriority().ordinal() < key.getPriority().ordinal()) {
                sorted.set(j + 1, sorted.get(j));
                j--;
            }
            sorted.set(j + 1, key);
        }
        return sorted;
    }

    /**
     * Вывод всех задач в консоль
     */
    public void printAllTasks() {
        printTasks(tasks, "Все задачи");
    }

    /**
     * Вывод задач из списка с заголовком
     */
    public void printTasks(List<Task> taskList, String header) {
        System.out.println("\n=== " + header + " ===");
        if (taskList.isEmpty()) {
            System.out.println("Нет задач для отображения.");
            return;
        }
        for (Task task : taskList) {
            System.out.println(task);
        }
    }
}
