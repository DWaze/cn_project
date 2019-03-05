package Message;

public class MsgBitfield extends Msg
{
    private byte[] bitfield;

    public MsgBitfield(byte[] _bitfield)
    {
        super((byte) 5);
        bitfield = _bitfield;
    }

    public byte[] getBitfield() { return bitfield; }

    /**
     * ToString method
     */
    @Override
    public String toString()
    {
        return "MsgBitfield : " + bitfield.toString();
    }
}
