package Hub;

public class HubListening {

    public static void main(String[] args) {

        HubListenningTCP listenningTCP = new HubListenningTCP();
        listenningTCP.start();
    }
}
