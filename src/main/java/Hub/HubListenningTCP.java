package Hub;

import Messages.H_P_Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HubListenningTCP extends Thread {

    @Override
    public void run() {
        try {
            ServerSocket socket =new ServerSocket(8000);
            H_P_Message h_p_message = new H_P_Message(3,1,1,2,1,0,1);

            while(true){
                Socket s =socket.accept();
                HubProcessingTCP processingTCP = new HubProcessingTCP(s,h_p_message);
                processingTCP.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
