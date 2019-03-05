package Message;

public class MsgNotify extends Msg
{
    private byte[] hash;
    private byte[] player_id;
    private int port;
    private int downloaded;
    private int uploaded;
    private int left;
    private String event;

    public MsgNotify(byte[] _hash, byte[] _player_id, int _port, int _downloaded, int _uploaded, int _left, String _event)
    {
        super((byte) 10);
        hash = _hash;
        player_id = _player_id;
        port = _port;
        downloaded = _downloaded;
        uploaded = _uploaded;
        left = _left;
        event = _event;
    }

    public byte[] getHash() { return hash; }
    public byte[] getPlayer_id() { return player_id; }
    public int getPort() { return port; }
    public int getDownloaded() { return downloaded; }
    public int getUploaded() { return uploaded; }
    public int getLeft() { return left; }
    public String getEvent() { return event; }

    /**
     * ToString method
     */
    @Override
    public String toString()
    {
        return "MsgNotify :"
                + " Hash : " + hash.toString()
                + " Player ID : " + player_id.toString()
                + " Port : " + Integer.toString(port)
                + " Downloaded : " + Integer.toString(downloaded)
                + " Uploaded : " + Integer.toString(uploaded)
                + " Left : " + Integer.toString(left)
                + " Event : " + event;
    }
}
