package ru.mentee.power.methods.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Тесты для класса TaskManager
 */
class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();

        // Добавляем тестовые задачи
        taskManager.addTask("Срочная задача", "Выполнить скорее", createDate(2023, 5, 15), Task.Priority.HIGH);
        taskManager.addTask("Обычная задача", "В течение недели", createDate(2023, 6, 1), Task.Priority.MEDIUM);
        taskManager.addTask("Несрочная задача", "Когда будет время", createDate(2023, 7, 1), Task.Priority.LOW);
        taskManager.addTask("Задача без описания");
    }

    /**
     * Тест добавления задачи
     */
    @Test
    void testAddTask() {
        Task newTask = taskManager.addTask("Новая задача", "Описание", createDate(2023, 8, 1), Task.Priority.HIGH);

        assertThat(newTask).isNotNull();
        assertThat(newTask.getId()).isGreaterThan(0);
        assertThat(newTask.getTitle()).isEqualTo("Новая задача");
        assertThat(newTask.getDescription()).isEqualTo("Описание");
        assertThat(newTask.getPriority()).isEqualTo(Task.Priority.HIGH);
        assertThat(taskManager.getAllTasks()).contains(newTask);
    }

    /**
     * Тест получения задачи по ID
     */
    @Test
    void testGetTaskById() {
        Task task = taskManager.getTaskById(1);
        assertThat(task).isNotNull();
        assertThat(task.getTitle()).isEqualTo("Срочная задача");

        Task missing = taskManager.getTaskById(999);
        assertThat(missing).isNull();
    }

    /**
     * Тест получения задач по приоритету
     */
    @Test
    void testGetTasksByPriority() {
        List<Task> highPriority = taskManager.getTasksByPriority(Task.Priority.HIGH);
        assertThat(highPriority).hasSize(1);
        assertThat(highPriority.get(0).getTitle()).isEqualTo("Срочная задача");

        List<Task> lowPriority = taskManager.getTasksByPriority(Task.Priority.LOW);
        assertThat(lowPriority).hasSize(1);
        assertThat(lowPriority.get(0).getTitle()).isEqualTo("Несрочная задача");

        List<Task> none = taskManager.getTasksByPriority(null);
        assertThat(none).isEmpty();
    }

    /**
     * Тест поиска задач
     */
    @Test
    void testSearchTasks() {
        List<Task> found = taskManager.searchTasks("задача");
        assertThat(found).hasSize(4); // все задачи содержат "задача" в названии

        List<Task> partial = taskManager.searchTasks("Срочная");
        assertThat(partial).hasSize(2);
        assertThat(partial.get(0).getTitle()).isEqualTo("Срочная задача");

        List<Task> none = taskManager.searchTasks("несуществующее слово");
        assertThat(none).isEmpty();
    }

    /**
     * Тест сортировки задач по приоритету
     */
    @Test
    void testSortTasksByPriority() {
        List<Task> sorted = taskManager.sortTasksByPriority();

        assertThat(sorted).hasSize(4);
        // Приоритеты должны идти от HIGH к LOW
        assertThat(sorted.get(0).getPriority()).isEqualTo(Task.Priority.HIGH);
        assertThat(sorted.get(1).getPriority()).isEqualTo(Task.Priority.MEDIUM);
        assertThat(sorted.get(2).getPriority()).isEqualTo(Task.Priority.MEDIUM);
        assertThat(sorted.get(3).getPriority()).isEqualTo(Task.Priority.LOW);
    }

    /**
     * Вспомогательный метод для создания даты
     */
    private static Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }
}
