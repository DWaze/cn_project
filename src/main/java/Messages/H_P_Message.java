package Messages;

import be.adaxisoft.bencode.BEncodedValue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Messages.P_P_Message.getString;

/**
 *
 */
public class H_P_Message {

    /* msgRequest Content Client to tracker message codes
    if == 0 request tracker to add the current client to the available lists
    if == 1 Not working ip address and send the list of the not working IP addresses
    if == 2 ask for a download peers from the tracker
    if == 3 ask the tracker to delete the current client from its list of available peers
    if == 4 send to the tracker a report that the file is not available in the peer
     */

    private int msgRequest;

//    Hub Answer
    private int failure_reason;
    private int warning_message;
    private int interval;
    private int min_interval;
    private int complete;
    private int incomplete;
    private ArrayList<ArrayList<BEncodedValue>> players;
    private int num_peers;


//    not_working_ips is the list of ip addresses that are not working to be sent to the tracker

    public H_P_Message() {
    }


    public H_P_Message(int msgRequest, int failure_reason, int warning_message, int interval, int min_interval, int complete, int incomplete) {
        this.msgRequest = msgRequest;
        this.failure_reason = failure_reason;
        this.warning_message = warning_message;
        this.interval = interval;
        this.min_interval = min_interval;
        this.complete = complete;
        this.incomplete = incomplete;
        this.players = new ArrayList<ArrayList<BEncodedValue>>();
    }

    public void setMsgRequest(int msgRequest) {
        this.msgRequest = msgRequest;
    }

    public void setFailure_reason(int failure_reason) {
        this.failure_reason = failure_reason;
    }

    public void setWarning_message(int warning_message) {
        this.warning_message = warning_message;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setMin_interval(int min_interval) {
        this.min_interval = min_interval;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public void setIncomplete(int incomplete) {
        this.incomplete = incomplete;
    }

    public void setPlayers(ArrayList<ArrayList<BEncodedValue>> players) {
        this.players = players;
    }

    public int getMsgRequest() {
        return msgRequest;
    }

    public int getFailure_reason() {
        return failure_reason;
    }

    public int getWarning_message() {
        return warning_message;
    }

    public int getInterval() {
        return interval;
    }

    public int getMin_interval() {
        return min_interval;
    }

    public int getComplete() {
        return complete;
    }

    public int getIncomplete() {
        return incomplete;
    }

    public ArrayList<ArrayList<BEncodedValue>> getPlayers() {
        return players;
    }



    public void addPlayer(Player player) throws UnsupportedEncodingException {
        if(player != null){
            ArrayList<BEncodedValue> pEncoded = new ArrayList<BEncodedValue>();
            pEncoded.add(new BEncodedValue(player.getIp()));
            pEncoded.add(new BEncodedValue(player.getPort()));
            pEncoded.add(new BEncodedValue(player.getPlayer_id()));
            pEncoded.add(new BEncodedValue(player.getPcomplete()));
            players.add(pEncoded);
            }
    }

    public void removePlayer(Player player) throws UnsupportedEncodingException {
        if(player != null){
            players.remove(player);
        }
    }


    public String bencoding(){

        Map<String, BEncodedValue> document = new HashMap<String,BEncodedValue>(){{
            put("msg_request",new BEncodedValue(msgRequest));
            put("failure_reason", new BEncodedValue(failure_reason));
            put("warning_message", new BEncodedValue(warning_message));
            put("interval", new BEncodedValue(interval));
            put("min_interval", new BEncodedValue(min_interval));
            put("complete", new BEncodedValue(complete));
            put("incomplete", new BEncodedValue(incomplete));
            put("num_peers", new BEncodedValue(players.size()));
        }};
        int i =1;
        for( ArrayList<BEncodedValue> player:players){
            document.put("player"+i, new BEncodedValue(player));
            i++;
        }

        return getString(document);
    }

}
