import java.util.concurrent.locks.Lock;

/**
 * An action that repeatedly removes a greeting from a queue.
 */
public class Consumer implements Runnable 
{
    private BoundedQueue queue;
    private int count;
    private static final int DELAY = 2000;
    private static final int BURGERDELAY = 5000;

    /**
     * Constructs the consumer object.
     * @param aQueue the queue from which to retrieve messages
     * @param aLock a lock for the queue
     * @param count the number of messages to consume
     */
    public Consumer(BoundedQueue aQueue, int aCount) 
    {
        queue = aQueue;
        count = aCount;
    }

    public void run() 
    {
        try {
            int i = 1;
            
            while (i <= count) {
                Message msg = queue.remove();
                i++;
                
                if (msg.text.equals("burger"))
                	Thread.sleep((int) (Math.random() * BURGERDELAY));
                else
                	Thread.sleep((int) (Math.random() * DELAY));
            }
        } 
        catch (InterruptedException ex) {}
    }
}
