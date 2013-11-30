import java.util.concurrent.locks.Lock;

/**
 * An action that repeatedly inserts a greeting into a queue.
 */
public class Producer implements Runnable 
{
    private String text;
    private BoundedQueue queue;
    private int index;
    private int count;
    private static final int DELAY = 1000;
    private static final int BURGERDELAY = 5000;
    
    /**
     * Constructs the producer object.
     * @param aMessage the message to insert into a queue
     * @param aQueue the queue into which to insert messages
     * @param aLock a lock for the queue
     * @param aCount the number of messages to produce
     */
    public Producer(String aText, BoundedQueue aQueue, 
                    int aIndex, int aCount) 
    {
        text = aText;
        queue = aQueue;
        index = aIndex;
        count = aCount;
    }

    public void run() 
    {
        try {
            int i = 1;
            
            while ( (i <= count)) {
                Message msg = new Message(index, i, text);
                queue.add(msg);
                i++;
                
                if (text.equals("burger"))
                	Thread.sleep((int) (Math.random() * BURGERDELAY));
                else
                	Thread.sleep((int) (Math.random() * DELAY));
            }
        } 
        catch (InterruptedException ex) {}
    }
}
