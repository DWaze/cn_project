package Hub;

import Player.SendingTCP;

public class HubSending {

    public static void main(String[] args) {

        SendingTCP sendingTCP = new SendingTCP();
        sendingTCP.start();

    }
}
