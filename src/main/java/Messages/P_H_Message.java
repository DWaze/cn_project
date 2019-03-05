package Messages;

import be.adaxisoft.bencode.BEncodedValue;
import be.adaxisoft.bencode.BEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class P_H_Message {

    /* msgRequest Content Client to tracker message codes
    if == 0 request tracker to add the current client to the available lists
    if == 1 Not working ip address and send the list of the not working IP addresses
    if == 2 ask for a download peers from the tracker
    if == 3 ask the tracker to delete the current client from its list of available peers
    if == 4 send to the tracker a report that the file is not available in the peer
     */

    private int msgRequest;

//    Hub Notify
    private int info_hash;
    private String player_id;
    private int port;
    private int downloaded;
    private int uploaded;
    private int left;
    private int event;

//    not_working_ips is the list of ip addresses that are not working to be sent to the tracker

    public P_H_Message(int msgRequest, int info_hash, String player_id, int port, int downloaded, int uploaded, int left, int event) {
        this.msgRequest = msgRequest;
        this.info_hash = info_hash;
        this.player_id = player_id;
        this.port = port;
        this.downloaded = downloaded;
        this.uploaded = uploaded;
        this.left = left;
        this.event = event;
    }

    public void setMsgRequest(int msgRequest) {
        this.msgRequest = msgRequest;
    }

    public void setInfo_hash(int info_hash) {
        this.info_hash = info_hash;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDownloaded(int downloaded) {
        this.downloaded = downloaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getMsgRequest() {
        return msgRequest;
    }

    public int getInfo_hash() {
        return info_hash;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public int getPort() {
        return port;
    }

    public int getDownloaded() {
        return downloaded;
    }

    public int getUploaded() {
        return uploaded;
    }

    public int getLeft() {
        return left;
    }

    public int getEvent() {
        return event;
    }

    public String bencoding() throws UnsupportedEncodingException {

        Map<String, BEncodedValue> document = new HashMap<String,BEncodedValue>(){{
            put("msg_request", new BEncodedValue(msgRequest));
            put("info_hash", new BEncodedValue(info_hash));
            put("player_id", new BEncodedValue(player_id));
            put("port", new BEncodedValue(port));
            put("downloaded", new BEncodedValue(downloaded));
            put("uploaded", new BEncodedValue(uploaded));
            put("left", new BEncodedValue(left));
            put("event", new BEncodedValue(event));
        }};
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BEncoder.encode(document, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encodedDocument = new String(baos.toByteArray());
        return encodedDocument;
    }



//    private List<IP_Description> not_working_ips;
//
//    public P_H_Message(int msgRequest) {
//        this.msgRequest = msgRequest;
//        this.not_working_ips = new ArrayList<>();
//    }
//
//    public int getMsgRequest() {
//        return msgRequest;
//    }
//
//    public List<IP_Description> getNot_working_ips() {
//        return not_working_ips;
//    }
//
//    public boolean addIPAddress (IP_Description ipAddress){
//        if(!this.not_working_ips.contains(ipAddress)){
//            not_working_ips.add(ipAddress);
//            return true;
//        }
//        return false;
//    }
//
//    public void processing_ips(List<IP_Description> not_working_ips) {
//        this.not_working_ips = not_working_ips;
//    }

}
