package Message;

public class MsgRequest extends Msg
{
    private int index;

    public MsgRequest(int _index)
    {
        super((byte) 6);
        index = _index;
    }

    public int getIndex() { return index; }

    /**
     * toString method
     */
    @Override
    public String toString()
    {
        String idx = new String(Integer.toString(index));
        return "MsgRequest : " + idx;
    }
}