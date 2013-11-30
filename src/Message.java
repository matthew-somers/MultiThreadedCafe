/**
 * A message to put into the queue.
 */
public class Message 
{
    public int index;
    public int sequence;
    public String text;
    
    public Message(int index, int sequence, String text)
    {
        this.index = index;
        this.sequence = sequence;
        this.text = text;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int x = index; x > 1; x--) sb.append("\t\t");
        sb.append(String.format("%4d: %s", sequence, text));
        return sb.toString();
    }
}
