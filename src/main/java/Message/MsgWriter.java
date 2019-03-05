package Message;

import BEncoding.BWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class MsgWriter
{
    /**
     * WriteShort method
     */
    private static void writeShort(byte[] _message, short _value, int _index)
    {
        ByteBuffer b = ByteBuffer.allocate(2);
        //b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putShort(_value);

        byte[] result = b.array();
        _message[_index] = result[0];
        _message[_index + 1] = result[1];
    }

    /**
     * WriteInt method
     */
    private static void writeInt(byte[] _message, int _value, int _index)
    {
        ByteBuffer b = ByteBuffer.allocate(4);
        //b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(_value);

        byte[] result = b.array();
        _message[_index] = result[0];
        _message[_index +1] = result[1];
        _message[_index +2] = result[2];
        _message[_index +3] = result[3];
    }

    /**
     * WriteHave method
     */
    private static byte[] writeHave(MsgHave _message)
    {
        byte[] result = new byte[7];
        writeShort(result, (short)5, 0);
        result[2] = (short)4;
        writeInt(result, _message.getIndex(), 3);

        return result;
    }

    /**
     * WriteBitfield method
     */
    private static byte[] writeBitfield(MsgBitfield _message)
    {
        int length = _message.getBitfield().length;
        byte[] result = new byte[3 + length];
        writeShort(result, (short)(length + 1), 0);
        result[2] = (short)5;
        for(int i = 0; i < length; i++)
        {
            result[i+2] = _message.getBitfield()[i];
        }

        return result;
    }

    /**
     * WriteRequest method
     */
    private static byte[] writeRequest(MsgRequest _message)
    {
        byte[] result = new byte[7];
        writeShort(result, (short)5, 0);
        result[2] = (short)6;
        writeInt(result, _message.getIndex(), 3);

        return result;
    }

    /**
     * WriteBook method
     */
    private static byte[] writeBook(MsgBook _message)
    {
        int length = _message.getPayload().length;
        byte[] result = new byte[3 + length];
        writeShort(result, (short)(length + 1), 0);
        result[2] = (short)7;
        for(int i = 0; i < length; i++)
        {
            result[i+2] = _message.getPayload()[i];
        }

        return result;
    }

    /**
     * WriteHandshake method
     */
    private static byte[] writeHandshake(MsgHandshake _message)
    {
        byte[] result = new byte[2];
        writeShort(result, (short)41, 0);

        result[2] = (byte)8;

        byte[] hash = _message.getHash();
        byte[] player_id = _message.getPlayer_id();

        int i;
        int index = 3;
        for(i = 0; i < 20; i++, index++)
        {
            result[index] = hash[i];
        }

        for(i = 0; i < 20; i++, index++)
        {
            result[index] = player_id[i];
        }

        return result;
    }

    /**
     * WriteNotify method
     */
    private static byte[] writeNotify(MsgNotify _message) throws IOException
    {
        Map<String,Object> dict = new HashMap<>();
        dict.put("info_hash", _message.getHash());
        dict.put("player_id", _message.getPlayer_id());
        dict.put("port", _message.getPort());
        dict.put("downloaded", _message.getDownloaded());
        dict.put("uploaded", _message.getUploaded());
        dict.put("left", _message.getLeft());
        dict.put("event", _message.getEvent());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BWriter.write(dict, out);

        byte[] dic = out.toByteArray();

        int length = dic.length + 3;

        byte[] result = new byte[length];
        writeShort(result, ((short)min(max((dic.length+1), Short.MIN_VALUE), Short.MAX_VALUE)), 0);
        result[2] = (byte)10;
        for(int i = 0; i < dic.length; i++)
        {
            result[i+3] = dic[i];
        }

        return result;
    }

    /**
     * WriteAnswer method
     */
    private static byte[] writeAnswer(MsgAnswer _message) throws IOException
    {
        Map<String,Object> dict = new HashMap<>();
        if(_message.getFailure() != null)
            dict.put("failure reason", _message.getFailure());
        else
        {
            if(_message.getWarning() != null) dict.put("warning message", _message.getWarning());
            dict.put("interval", _message.getInterval());
            if(_message.getMin_interval() != null) dict.put("min interval", _message.getMin_interval());
            dict.put("complete", _message.getComplete());
            dict.put("incomplete", _message.getIncomplete());
            dict.put("players", _message.getPlayers());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BWriter.write(dict, out);

        byte[] dic = out.toByteArray();

        int length = dic.length + 3;

        byte[] result = new byte[length];
        writeShort(result, ((short)min(max((dic.length+1), Short.MIN_VALUE), Short.MAX_VALUE)), 0);
        result[2] = (byte)11;
        for(int i = 0; i < dic.length; i++)
        {
            result[i+3] = dic[i];
        }

        return result;
    }

    /**
     * WriteInvalidAdress method
     */
    private static byte[] writeInvalidAdress(MsgInvalidAddress _message) throws IOException
    {
        Map<String,Object> dict = new HashMap<>();
        dict.put("list_player", _message.getPlayers());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BWriter.write(dict, out);

        byte[] dic = out.toByteArray();

        int length = dic.length + 3;

        byte[] result = new byte[length];
        writeShort(result, ((short)min(max((dic.length+1), Short.MIN_VALUE), Short.MAX_VALUE)), 0);
        result[2] = (byte)12;
        for(int i = 0; i < dic.length; i++)
        {
            result[i+3] = dic[i];
        }

        return result;
    }

    /**
     * WriteMsg method
     * Write keep alive
     * Write choke
     * Write unchoke
     * Write interest
     * Write not interest
     */
    private static byte[] writeMsg(Msg _message)
    {
        if(_message.getId() == -1) return new byte[] {0,0};
        byte[] result = {0,1,_message.getId()};

        return result;
    }

    /**
     * Writing main method
     */
    public static byte[] write(Msg _message) throws IOException, Exception
    {
        if(_message instanceof MsgHave) return writeHave((MsgHave) _message);
        else if(_message instanceof MsgBitfield) return writeBitfield((MsgBitfield) _message);
        else if(_message instanceof MsgRequest) return writeRequest((MsgRequest) _message);
        else if(_message instanceof MsgBook) return writeBook((MsgBook) _message);
        else if(_message instanceof MsgHandshake) return writeHandshake((MsgHandshake) _message);
        else if(_message instanceof MsgNotify) return writeNotify((MsgNotify) _message);
        else if(_message instanceof MsgAnswer) return writeAnswer((MsgAnswer) _message);
        else if(_message instanceof MsgInvalidAddress) return writeInvalidAdress((MsgInvalidAddress) _message);
        else return writeMsg(_message);
    }
}
