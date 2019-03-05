package Player;

import Librifier.Library;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenningTCP extends Thread {

    private Library mLibrary;

    ListenningTCP(Library mLibrary) {
        this.mLibrary = mLibrary;
    }

    @Override
    public void run() {
        try {
            ServerSocket socket =new ServerSocket(8080);

            while(true){
                Socket s =socket.accept();
                ProcessingTCP processingTCP = new ProcessingTCP(s,mLibrary);
                processingTCP.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
