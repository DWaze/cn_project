package Message;

public class Msg
{
    private final byte id;
    protected short length;

    public Msg()
    {
        length = 0;
        id = -1;
    }

    public Msg(byte _id)
    {
        length = 1;
        id = _id;
    }

    public byte getId() { return id; }
    public short getLength() { return length; }

    /**
     * ToString method
     */
    @Override
    public String toString()
    {
        String idn;
        switch (id) {
            case 0:
                idn = "MsgID : Choke";
                break;

            case 1:
                idn = "MsgID : Unchoke";
                break;

            case 2:
                idn = "MsgID : Interested";
                break;

            case 3:
                idn = "MsgID : Not Interested";
                break;

            case 4:
                idn = "MsgID : Have";
                break;

            case 5:
                idn = "MsgID : Bitfield";
                break;

            case 6:
                idn = "MsgID : Request";
                break;

            case 7:
                idn = "MsgID : Book";
                break;

            case 8:
                idn = "MsgID : Handshake";
                break;

            case 10:
                idn = "MsgID : Notify";
                break;

            case 11:
                idn = "MsgID : Answer";
                break;

            case 12:
                idn = "MsgID : Invalid Adress";
                break;

            default:
                return "ERROR ID Not suported : " + id;
        }

        return "MSG : " + idn;
    }
}
