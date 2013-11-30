import java.util.Timer;
import java.util.TimerTask;

/**
 * This program runs threads in parallel.
 */
public class ThreadTester 
{
    public static void main(String[] args) 
    {
        BoundedQueue queue = new BoundedQueue(3);
        
        final int PRODUCER_COUNT = 4;
        final int MESSAGE_COUNT = 5;
        
        Runnable run1 = new Producer("COFFEE",
                                     queue, 1, MESSAGE_COUNT);
        Runnable run2 = new Producer("COFFEE",
                                     queue, 2, MESSAGE_COUNT);
        Runnable run3 = new Producer("COFFEE",
                                     queue, 3, MESSAGE_COUNT);
        Runnable run4 = new Producer("BURGER",
                                     queue, 4, MESSAGE_COUNT);
        Runnable run6 = new Consumer(queue, PRODUCER_COUNT*MESSAGE_COUNT);

        Thread thread1 = new Thread(run1);
        Thread thread2 = new Thread(run2);
        Thread thread3 = new Thread(run3);
        Thread thread4 = new Thread(run4);
        Thread thread6 = new Thread(run6);

        long start = System.currentTimeMillis();
        long time = System.currentTimeMillis()-start;
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread6.start();
    }
}
