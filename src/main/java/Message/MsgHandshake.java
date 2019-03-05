package Message;

public class MsgHandshake extends Msg
{
    private byte[] hash;
    private byte[] player_id;

    public MsgHandshake(byte[] _hash, byte[] _player_id)
    {
        super((byte) 8);
        hash = _hash;
        player_id = _player_id;
    }

    public byte[] getHash() { return hash; }
    public byte[] getPlayer_id() { return player_id; }

    /**
     * ToString method
     * @return
     */
    @Override
    public String toString()
    {
        return"MsgHandshake : PLAYER ID : " + player_id.toString() + " |||| HASH : " + hash.toString();
    }
}
