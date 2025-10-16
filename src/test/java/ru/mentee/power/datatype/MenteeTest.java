package ru.mentee.power.datatype;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MenteeTest {

    @Test
    public void testMenteeCreation() {
        Mentee mentee = new Mentee("Иван", "Иванов", 20, 4.2, 2);

        assertEquals("Иван", mentee.getFirstName());
        assertEquals("Иванов", mentee.getLastName());
        assertEquals(20, mentee.getAge());
        assertEquals(4.2, mentee.getAverageGrade(), 0.001);
        assertEquals(2, mentee.getLevel());
    }

    @Test
    public void testIsExcellent() {
        Mentee excellentMentee = new Mentee("Мария", "Петрова", 19, 4.8, 1);
        Mentee averageMentee = new Mentee("Петр", "Сидоров", 21, 3.9, 3);

        assertTrue(excellentMentee.isExcellent());
        assertFalse(averageMentee.isExcellent());
    }

    @Test
    public void testAdvanceToNextLevel() {
        Mentee junior = new Mentee("Алексей", "Смирнов", 18, 4.0, 1);
        Mentee senior = new Mentee("Ольга", "Козлова", 22, 4.7, 5);

        assertTrue(junior.advanceToNextLevel());
        assertEquals(2, junior.getLevel());

        assertFalse(senior.advanceToNextLevel());
        assertEquals(5, senior.getLevel());
    }

    @Test
    public void testInvalidLevelValue() {
        Mentee mentee1 = new Mentee("Дмитрий", "Попов", 19, 4.3, 0);
        Mentee mentee2 = new Mentee("Анна", "Соколова", 20, 4.5, 6);

        // Значения уровня должны быть скорректированы до 1
        assertEquals(1, mentee1.getLevel());
        assertEquals(1, mentee2.getLevel());
    }

    @Test
    public void testCalculateScholarship() {
        // Отличник - высшая стипендия
        Mentee excellentMentee = new Mentee("Мария", "Ивановна", 19, 4.8, 1);
        assertEquals(2000, excellentMentee.calculateScholarship());

        // Хорошист - средняя стипендия
        Mentee goodMentee = new Mentee("Алексей", "Смирнов", 18, 4.2, 1);
        assertEquals(1500, goodMentee.calculateScholarship());

        // Средний балл ниже 4.0 - базовая стипендия
        Mentee averageMentee = new Mentee("Петр", "Сидоров", 21, 3.9, 3);
        assertEquals(1000, averageMentee.calculateScholarship());

        // Граничные значения
        Mentee borderlineMentee1 = new Mentee("Ольга", "Козлова", 22, 4.5, 5); // Ровно 4.5
        assertEquals(2000, borderlineMentee1.calculateScholarship());

        Mentee borderlineMentee2 = new Mentee("Иван", "Иванов", 20, 4.0, 2); // Ровно 4.0
        assertEquals(1500, borderlineMentee2.calculateScholarship());
    }
}