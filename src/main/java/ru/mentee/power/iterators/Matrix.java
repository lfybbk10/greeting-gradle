package ru.mentee.power.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс, представляющий двумерную матрицу с различными способами итерации
 */
public class Matrix<T> implements Iterable<T> {
    private final T[][] data;
    private final int rows;
    private final int columns;

    /**
     * Создает матрицу на основе двумерного массива
     * @param data двумерный массив данных
     * @throws IllegalArgumentException если data равен null или это не прямоугольная матрица
     */
    @SuppressWarnings("unchecked")
    public Matrix(T[][] data) {
        if (data == null) {
            throw new IllegalArgumentException("Массив данных не может быть null");
        }

        this.rows = data.length;
        if (rows == 0) {
            this.columns = 0;
        } else {
            this.columns = data[0].length;
            // Проверка на прямоугольность матрицы
            for (T[] row : data) {
                if (row.length != columns) {
                    throw new IllegalArgumentException("Все строки матрицы должны иметь одинаковую длину");
                }
            }
        }

        // Создаем копию массива для инкапсуляции
        this.data = (T[][]) new Object[rows][];
        for (int i = 0; i < rows; i++) {
            this.data[i] = (T[]) new Object[columns];
            System.arraycopy(data[i], 0, this.data[i], 0, columns);
        }
    }

    /**
     * @return количество строк в матрице
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return количество столбцов в матрице
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Получить элемент по индексам
     * @param row индекс строки
     * @param column индекс столбца
     * @return элемент матрицы
     * @throws IndexOutOfBoundsException если индексы за пределами матрицы
     */
    public T get(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Индексы за пределами матрицы");
        }
        return data[row][column];
    }

    /**
     * Установить значение элемента
     * @param row индекс строки
     * @param column индекс столбца
     * @param value новое значение
     * @throws IndexOutOfBoundsException если индексы за пределами матрицы
     */
    public void set(int row, int column, T value) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Индексы за пределами матрицы");
        }
        data[row][column] = value;
    }

    /**
     * Возвращает итератор по умолчанию (построчный)
     */
    @Override
    public Iterator<T> iterator() {
        return new RowMajorIterator();
    }

    /**
     * Итератор, обходящий матрицу построчно (слева направо, сверху вниз)
     */
    public Iterator<T> rowMajorIterator() {
        return new RowMajorIterator();
    }

    /**
     * Итератор, обходящий матрицу по столбцам (сверху вниз, слева направо)
     */
    public Iterator<T> columnMajorIterator() {
        return new ColumnMajorIterator();
    }

    /**
     * Итератор, обходящий матрицу по спирали от центра к краям
     */
    public Iterator<T> spiralIterator() {
        return new SpiralIterator();
    }

    /**
     * Итератор, обходящий матрицу зигзагом (змейкой)
     */
    public Iterator<T> zigzagIterator() {
        return new ZigzagIterator();
    }

    /**
     * Построчный итератор (основной)
     */
    private class RowMajorIterator implements Iterator<T> {
        private int currentRow = 0;
        private int currentColumn = 0;

        @Override
        public boolean hasNext() {
            return currentRow < rows && currentColumn < columns;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T element = data[currentRow][currentColumn];

            // Переход к следующему элементу
            currentColumn++;
            if (currentColumn >= columns) {
                currentRow++;
                currentColumn = 0;
            }

            return element;
        }
    }

    private class ColumnMajorIterator implements Iterator<T> {
        private int currentRow = 0;
        private int currentColumn = 0;

        @Override
        public boolean hasNext() {
            return currentRow < rows && currentColumn < columns;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T element = data[currentRow][currentColumn];

            // Переход к следующему элементу
            currentRow++;
            if (currentRow >= rows) {
                currentColumn++;
                currentRow = 0;
            }
            return element;
        }

    }

    private class SpiralIterator implements Iterator<T> {
        private final int rows = data.length;
        private final int cols = data[0].length;
        private final int total = rows * cols;

        private int count = 0;

        // Центр
        private int row = rows / 2;
        private int col = cols / 2;

        // Слои спирали
        private int step = 1;
        private int direction = 0; // 0 → вправо, 1 ↑ вверх, 2 ← влево, 3 ↓ вниз

        // Для контроля направления и шагов
        private int moveInCurrentDirection = 0;
        private int movesInThisLayer = 1;

        @Override
        public boolean hasNext() {
            return count < total;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T value = data[row][col];
            count++;

            // Двигаемся дальше только если не прошли все
            if (count < total) move();

            return value;
        }

        private void move() {
            switch (direction) {
                case 0 -> col++;  // вправо
                case 1 -> row--;  // вверх
                case 2 -> col--;  // влево
                case 3 -> row++;  // вниз
            }

            moveInCurrentDirection++;

            // Когда сделали нужное количество шагов в этом направлении:
            if (moveInCurrentDirection == movesInThisLayer) {
                moveInCurrentDirection = 0;
                direction = (direction + 1) % 4; // смена направления

                // После двух направлений увеличиваем длину шага
                if (direction == 0 || direction == 2) {
                    step++;
                }
                movesInThisLayer = step;
            }

            // Если выходим за границы — сдвигаемся внутрь
            if (row < 0) row = 0;
            if (col < 0) col = 0;
            if (row >= rows) row = rows - 1;
            if (col >= cols) col = cols - 1;
        }
    }

    private class ZigzagIterator implements Iterator<T> {
        private final int rows = data.length;
        private final int cols = data[0].length;
        private int row = 0;
        private int col = 0;
        private int count = 0;
        private final int total = rows * cols;

        private boolean leftToRight = true;

        @Override
        public boolean hasNext() {
            return count < total;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T value = data[row][col];
            count++;

            moveNext();

            return value;
        }

        private void moveNext() {
            if (leftToRight) {
                if (col < cols - 1) {
                    col++;
                } else {
                    row++;
                    leftToRight = false;
                }
            } else {
                if (col > 0) {
                    col--;
                } else {
                    row++;
                    leftToRight = true;
                }
            }
        }
    }
}
