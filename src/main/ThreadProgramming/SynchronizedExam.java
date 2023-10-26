package ThreadProgramming;

public class SynchronizedExam {
    static Data data = new Data(0, 0, 0, 0);

    static void task1() {
        synchronized (data) {
            data.y = 1;
            data.r1 = data.x;
        }
    }

    static void task2() throws InterruptedException {
        synchronized (data) {
            data.x = 1;
            data.r2 = data.y;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        while(true) {

            Thread th1 = new Thread(() -> task1());
            Thread th2 = new Thread(() -> {
                try {
                    task2();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            th1.start();
            th2.start();
        }
    }
}
