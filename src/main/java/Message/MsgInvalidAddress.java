package Message;

import java.util.ArrayList;

public class MsgInvalidAddress extends Msg
{
    private ArrayList<String> players;

    public MsgInvalidAddress(ArrayList<String> _players)
    {
        super((byte) 12);
        players = _players;
    }

    public ArrayList<String> getPlayers() { return players; }

    /**
     * ToString method
     */
    @Override
    public String toString()
    {
        return "MsgInvalidAddress : " + players.toString();
    }
}