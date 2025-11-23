package Lab_7;
public class ArraySumMultiThread {

    private static class SumCalculator extends Thread {
        private final int[] array;
        private final int start;
        private final int end;
        private long partialSum;

        public SumCalculator(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.partialSum = 0;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                partialSum += array[i];
            }
        }

        public long getPartialSum() {
            return partialSum;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int mid = array.length / 2;

        SumCalculator thread1 = new SumCalculator(array, 0, mid);
        SumCalculator thread2 = new SumCalculator(array, mid, array.length);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        long totalSum = thread1.getPartialSum() + thread2.getPartialSum();

        System.out.println("Сумма элементов массива: " + totalSum);
        System.out.println("Проверка: " + (1+2+3+4+5+6+7+8+9+10));
    }
}
