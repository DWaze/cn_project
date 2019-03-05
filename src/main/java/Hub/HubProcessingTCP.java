package Hub;

import Messages.H_P_Message;
import Messages.Player;
import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Map;

public class HubProcessingTCP extends Thread {

    private Socket s;
    private final H_P_Message h_p_message;
    private String player_id;
    private String ip;
    private int port;
    private int pComplete;

    HubProcessingTCP(Socket s, H_P_Message h_p_message) {
        this.s = s;
        this.h_p_message= h_p_message;
    }

    @Override
    public void run() {
        try {

            DataInputStream dataInputStream = new DataInputStream(s.getInputStream());
            String bEncoded = dataInputStream.readUTF();
            Bencode bencode = new Bencode();
            Map<String, Object> document = bencode.decode(bEncoded.getBytes(), Type.DICTIONARY);
            Long msg_request = (Long) document.get("msg_request");

            switch (msg_request.intValue()){
                case 10:
                    player_id = (String) document.get("player_id");
                    ip = (String) document.get("player_id");
                    port = ((Long) document.get("port")).intValue();
                    pComplete = ((Long) document.get("left")).intValue();
                    synchronized (h_p_message){
                        Player mPlayer = new Player(player_id,ip,port,pComplete);
                        h_p_message.addPlayer(mPlayer);
                    }
                    dataInputStream.close();
//                    in.close();
                    s.close();
                    break;
                case 11:
                    player_id = (String) document.get("player_id");
                    ip = (String) document.get("player_id");
                    port = ((Long) document.get("port")).intValue();
                    pComplete = ((Long) document.get("left")).intValue();
                    synchronized (h_p_message){
                        Player mPlayer = new Player(player_id,ip,port,pComplete);
                        h_p_message.removePlayer(mPlayer);
                    }
                    dataInputStream.close();
//                    in.close();
                    s.close();
                    break;
                case 12:
                    bEncoded = h_p_message.bencoding();

                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    out.writeUTF(bEncoded);

                    out.close();
                    s.close();

                    break;
                default:
                    System.out.println(msg_request);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
