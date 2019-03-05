package Message;

public class MsgBook extends Msg
{
    private int index;
    private byte[] payload;

    public MsgBook(int _index, byte[] _payload)
    {
        super((byte) 7);
        index = _index;
        payload = _payload;
    }

    public int getIndex() { return index; }
    public byte[] getPayload() { return payload; }

    /**
     * ToString method
     */
    @Override
    public String toString()
    {
        return "MsgBook : index: " + Integer.toString(index) + " || payload: " + payload.toString();
    }
}
