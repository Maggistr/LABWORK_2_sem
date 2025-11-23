package Lab_7;
public class MatrixMaxMultiThread {

    private static class RowMaxFinder extends Thread {
        private final int[] row;
        private int rowMax;

        public RowMaxFinder(int[] row) {
            this.row = row;
            this.rowMax = Integer.MIN_VALUE;
        }

        @Override
        public void run() {
            for (int value : row) {
                if (value > rowMax) {
                    rowMax = value;
                }
            }
        }

        public int getRowMax() {
            return rowMax;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[][] matrix = {
                {1, 5, 3, 8},
                {9, 2, 7, 4},
                {6, 11, 3, 0},
                {2, 4, 6, 10}
        };

        RowMaxFinder[] threads = new RowMaxFinder[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            threads[i] = new RowMaxFinder(matrix[i]);
            threads[i].start();
        }

        for (RowMaxFinder thread : threads) {
            thread.join();
        }

        int globalMax = Integer.MIN_VALUE;
        for (RowMaxFinder thread : threads) {
            if (thread.getRowMax() > globalMax) {
                globalMax = thread.getRowMax();
            }
        }

        System.out.println("Наибольший элемент в матрице: " + globalMax);

        // Проверка
        int checkMax = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            for (int value : row) {
                if (value > checkMax) {
                    checkMax = value;
                }
            }
        }
        System.out.println("Проверка: " + checkMax);
    }
}