package Message;

public class MsgHave extends Msg
{
    private int index;

    public MsgHave(int _index)
    {
        super((byte) 4);
        index = _index;
    }

    public int getIndex() { return index; }

    /**
     * ToString method
     */
    @Override
    public String toString()
    {
        return "MsgHave : " + Integer.toString(index);
    }
}
