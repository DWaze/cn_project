package Messages;

public class Player {
    private String player_id;
    private String ip;
    private int port;
    private int pcomplete;

    public Player(String player_id, String ip, int port, int pcomplete) {
        this.player_id = player_id;
        this.ip = ip;
        this.port = port;
        this.pcomplete = pcomplete;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPcomplete(int pcomplete) {
        this.pcomplete = pcomplete;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getPcomplete() {
        return pcomplete;
    }

    @Override
    public boolean equals(Object obj) {
        Player player = (Player)obj;
        return ( player.ip.equals(this.ip) && player.player_id == this.player_id && player.port == this.port
                && player.pcomplete == this.pcomplete );
    }
}
