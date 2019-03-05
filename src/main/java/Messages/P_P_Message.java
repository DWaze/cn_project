package Messages;

import be.adaxisoft.bencode.BEncodedValue;
import be.adaxisoft.bencode.BEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class P_P_Message {

    /* msgRequest Content Client to tracker message codes
    if == 0 request connection and ask for availability of the other client and the files in the other client
    if == 1 connection response when to the other client
    if == 2 requesting the download of the file
    if == 3 sending the content of the requested file
     */

    private int msgRequest;

    private int keep_alive;
    private int choke;
    private int unchoke;
    private int interested;
    private int not_interested;
    private int have;
    private int bitfield;
    private int request;
    private int book;
    private int handshake;


    public P_P_Message() {
    }

    public P_P_Message(int msgRequest, int keep_alive, int choke, int unchoke, int interested, int not_interested, int have, int bitfield, int request, int book, int handshake) {
        this.msgRequest = msgRequest;
        this.keep_alive = keep_alive;
        this.choke = choke;
        this.unchoke = unchoke;
        this.interested = interested;
        this.not_interested = not_interested;
        this.have = have;
        this.bitfield = bitfield;
        this.request = request;
        this.book = book;
        this.handshake = handshake;
    }


    public String bencoding(){

        Map<String, BEncodedValue> document = new HashMap<String,BEncodedValue>(){{
            put("msg_request", new BEncodedValue(msgRequest));
            put("keep_alive", new BEncodedValue(keep_alive));
            put("choke", new BEncodedValue(choke));
            put("unchoke", new BEncodedValue(unchoke));
            put("interested", new BEncodedValue(interested));
            put("not_interested", new BEncodedValue(not_interested));
            put("have", new BEncodedValue(have));
            put("bitfield", new BEncodedValue(bitfield));
            put("request", new BEncodedValue(request));
            put("book", new BEncodedValue(book));
            put("handshake", new BEncodedValue(handshake));
        }};
        return getString(document);
    }

    static String getString(Map<String, BEncodedValue> document) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BEncoder.encode(document, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encodedDocument = new String(baos.toByteArray());
        return encodedDocument;
    }


    public void setMsgRequest(int msgRequest) {
        this.msgRequest = msgRequest;
    }

    public void setKeep_alive(int keep_alive) {
        this.keep_alive = keep_alive;
    }

    public void setChoke(int choke) {
        this.choke = choke;
    }

    public void setUnchoke(int unchoke) {
        this.unchoke = unchoke;
    }

    public void setInterested(int interested) {
        this.interested = interested;
    }

    public void setNot_interested(int not_interested) {
        this.not_interested = not_interested;
    }

    public void setHave(int have) {
        this.have = have;
    }

    public void setBitfield(int bitfield) {
        this.bitfield = bitfield;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public void setBook(int book) {
        this.book = book;
    }

    public void setHandshake(int handshake) {
        this.handshake = handshake;
    }

    public int getMsgRequest() {
        return msgRequest;
    }

    public int getKeep_alive() {
        return keep_alive;
    }

    public int getChoke() {
        return choke;
    }

    public int getUnchoke() {
        return unchoke;
    }

    public int getInterested() {
        return interested;
    }

    public int getNot_interested() {
        return not_interested;
    }

    public int getHave() {
        return have;
    }

    public int getBitfield() {
        return bitfield;
    }

    public int getRequest() {
        return request;
    }

    public int getBook() {
        return book;
    }

    public int getHandshake() {
        return handshake;
    }

}
