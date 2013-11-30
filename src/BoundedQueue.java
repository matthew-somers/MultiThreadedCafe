
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A first-in, first-out bounded collection of Message objects.
 */
public class BoundedQueue
{
    private Message elements[];
    private int head;
    private int tail;
    private int size;
    private boolean debug;
    private boolean burger;
    private long start;
    
    /**
     * Constructs an empty queue.
     * @param capacity the maximum capacity of the queue
     */
    public BoundedQueue(int capacity) 
    {
        elements = new Message[capacity];
        head = 0;
        tail = 0;
        size = 0;
        debug = true;
        burger = false;
        start = System.nanoTime();
    }
    
    public void setDebug(boolean aDebug) { debug = aDebug; }

    /**
     * Removes the object at the head.
     * @return the object that has been removed from the queue 
     * @precondition !isEmpty()
     */
    public synchronized Message remove() throws InterruptedException
    {
        while (isEmpty()) wait();
        Message msg = elements[head];
        if (msg.text.equals("BURGER"))
        	burger = false;

        head++;
        size--;
        if (head == elements.length) head = 0;

        if (debug) {
            msg.sequence = -msg.sequence;
            long current = System.nanoTime() - start;
            long seconds = current / 1000000000;
            long ns = ((current-(seconds*1000000000))) / 1000000 * 60 % 1000 / 10;
            long ms = ((current-(seconds*1000000000))) / 1000000 * 60 / 1000;
            String sms;
            if (ms < 10)
            	sms = ("0" + ms + "." + ns);
            else
            	 sms = ms + "." + ns;
            String toprint = seconds + ":" + sms;
            System.out.printf("%8s", toprint);
            System.out.printf(" (%d) %s\n", size, msg);
            msg.sequence = -msg.sequence;
        }

        notifyAll();
        return msg;
    }

    /**
     * Appends an object at the tail.
     * @param newValue the object to be appended 
     * @precondition !isFull();
     */
    public synchronized void add(Message newValue) throws InterruptedException
    {
        while ( isFull() || burger) wait();
        
        if (newValue.text.equals("BURGER"))
        	burger = true;
        
        elements[tail] = newValue;

        tail++;
        size++;
        if (tail == elements.length) tail = 0;
        long current = System.nanoTime() - start;
        long seconds = current / 1000000000;
        long ns = ((current-(seconds*1000000000))) / 1000000 * 60 % 1000 / 10;
        long ms = ((current-(seconds*1000000000))) / 1000000 * 60 / 1000;
        String sms;
        if (ms < 10)
        	sms = ("0" + ms + "." + ns);
        else
        	 sms = ms + "." + ns;
        String toprint = seconds + ":" + sms;
        System.out.printf("%8s", toprint);
        if (debug) System.out.printf(" (%d) %s\n", size, newValue);
        notifyAll();
    }
    
    public boolean isFull() 
    {
        return size == elements.length;
    }

    public boolean isEmpty() 
    {
        return size == 0;
    }
}
