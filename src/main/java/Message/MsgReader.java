package Message;


import BEncoding.BReader;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MsgReader {
    /**
     * ReadShort method
     *
     * @param _data
     * @param _index
     * @param _length
     * @return
     */
    private static short readShort(byte[] _data, int[] _index, int _length) {
        byte[] arr = new byte[_length];
        for (int i = 0; i < _length; i++, _index[0]++) {
            arr[i] = _data[_index[0]];
        }
        _index[0]++;

        ByteBuffer wrapped = ByteBuffer.wrap(arr); // big-endian by default
        short result = wrapped.getShort();

        return result;
    }

    /**
     * ReadInt method
     */
    private static int readInt(byte[] _data, int[] _index, int _length) {
        byte[] arr = new byte[_length];
        for (int i = 0; i < _length; i++, _index[0]++) {
            arr[i] = _data[_index[0]];
        }

        ByteBuffer wrapped = ByteBuffer.wrap(arr); // big-endian by default
        int result = wrapped.getInt();

        return result;
    }

    /**
     * ReadByte method
     */
    private static byte[] readByte(byte[] _data, int[] _index, int _length) {
        byte[] result = new byte[_length];
        for (int i = 0; i < _length; i++, _index[0]++) {
            result[i] = _data[_index[0]];
        }

        return result;
    }

    /**
     * ReadHave method
     */
    private static MsgHave readHave(byte[] _message, int[] _index, int _length) {
        int book_index = readInt(_message, _index, 4);

        return new MsgHave(book_index);
    }

    /**
     * ReadBitfield method
     */
    private static MsgBitfield readBitfield(byte[] _message, int[] _index, int _length) {
        byte[] bitfield = readByte(_message, _index, _length);

        return new MsgBitfield(bitfield);
    }

    /**
     * ReadRequest method
     */
    private static MsgRequest readRequest(byte[] _message, int[] _index, int _length) {
        int book_index = readInt(_message, _index, 4);

        return new MsgRequest(book_index);
    }

    /**
     * ReadBook method
     */
    private static MsgBook readBook(byte[] _message, int[] _index, int _length) {
        int book_index = readInt(_message, _index, 4);
        byte[] payload = readByte(_message, _index, _length - 4);

        return new MsgBook(book_index, payload);
    }

    /**
     * ReadHandshake method
     */
    private static MsgHandshake readHandshake(byte[] _message, int[] _index, int _length) {
        byte[] player_id = readByte(_message, _index, 20);
        byte[] hash = readByte(_message, _index, 20);

        return new MsgHandshake(player_id, hash);
    }

    /**
     * ReadNotify method
     */
    private static MsgNotify readNotify(byte[] _message, int[] _index, int length) throws Exception {
        String event = "";
        int port = 0;
        int downloaded = 0;
        int uploaded = 0;
        int left = 0;
        byte[] player_id = {};
        byte[] hash = {};

        Map<String, Object> content = (Map<String, Object>) BReader.read(_message, _index);
        SortedMap<String, Object> sortedMap = new TreeMap<>(content);
        for (Map.Entry<String, Object> e : sortedMap.entrySet()) {
            switch (e.getKey()) {
                case "info_hash":
                    hash = (byte[]) e.getValue();
                    break;

                case "player_id":
                    player_id = (byte[]) e.getValue();
                    break;

                case "port":
                    port = (int) e.getValue();
                    break;

                case "downloaded":
                    downloaded = (int) e.getValue();
                    break;

                case "uploaded":
                    uploaded = (int) e.getValue();
                    break;

                case "left":
                    left = (int) e.getValue();
                    break;

                case "event":
                    event = new String((byte[]) e.getValue());
                    break;

                default:
                    //System.out.println("Key : " + e.getKey());
                    throw new Exception();
            }
        }

        return new MsgNotify(hash, player_id, port, downloaded, uploaded, left, event);
    }

    /**
     * ReadAnswer method
     */
    private static MsgAnswer readAnswer(byte[] _message, int[] _index, int _length) throws Exception {
        String warning = null;
        Integer interval = null;
        Integer min_interval = null;
        Integer complete = null;
        Integer incomplete = null;
        ArrayList<Map<String, Object>> players = new ArrayList<>();


        Map<String, Object> content = (Map<String, Object>) BReader.read(_message, _index);
        SortedMap<String, Object> sortedMap = new TreeMap<>(content);
        for (Map.Entry<String, Object> e : sortedMap.entrySet()) {
            switch (e.getKey()) {
                case "failure reason":
                    return new MsgAnswer(new String((byte[]) e.getValue()));

                case "warning message":
                    warning = new String((byte[]) e.getValue());
                    break;

                case "interval":
                    interval = (int) e.getValue();
                    break;

                case "min interval":
                    min_interval = (int) e.getValue();
                    break;

                case "complete":
                    complete = (int) e.getValue();
                    break;

                case "incomplete":
                    incomplete = (int) e.getValue();
                    break;

                case "players":
                    players = (ArrayList<Map<String, Object>>) e.getValue();

                default:
                    throw new Exception();
            }
        }
        MsgAnswer result = new MsgAnswer(interval, complete, incomplete, players);
        if (warning != null) result.setWarning(warning);
        if (min_interval != null) result.setMin_interval(min_interval);

        return result;
    }

    /**
     * ReadInvalidAddress method
     */
    private static MsgInvalidAddress readInvalidAddress(byte[] _message, int[] _index, int length) throws Exception {
        ArrayList<Object> objects;
        ArrayList<String> players = new ArrayList<>();

        Map<String, Object> content = (Map<String, Object>) BReader.read(_message, _index);
        SortedMap<String, Object> sortedMap = new TreeMap<>(content);
        for (Map.Entry<String, Object> e : sortedMap.entrySet()) {
            if (e.getKey().equals("list_player")) {
                objects = ((ArrayList<Object>) e.getValue());

                for (Object obj : objects) {
                    players.add(new String((byte[]) obj));
                }
            } else throw new Exception();
        }

        return new MsgInvalidAddress(players);
    }

    /**
     * Reading main method
     *
     * @param _message
     * @return
     * @throws Exception
     */
    public static Msg read(byte[] _message) throws Exception {
        int index[] = {0};
        int length = readShort(_message, index, 2);
        System.out.println("Length : " + length);

        if (length == 0) return new Msg();

        switch (_message[2]) {
            case 0:
            case 1:
            case 2:
            case 3:
                return new Msg(_message[2]);

            case 4:
                return readHave(_message, index, length - 1);

            case 5:
                return readBitfield(_message, index, length - 1);

            case 6:
                return readRequest(_message, index, length - 1);

            case 7:
                return readBook(_message, index, length - 1);

            case 8:
                return readHandshake(_message, index, length - 1);

            case 10:
                return readNotify(_message, index, length - 1);

            case 11:
                return readAnswer(_message, index, length - 1);

            case 12:
                return readInvalidAddress(_message, index, length - 1);

            default:
                System.out.println((byte) _message[0]);
                System.out.println((byte) _message[1]);
                System.out.println((byte) _message[2]);
                throw new Exception();
        }
    }


    public static byte[] MsgStreamReader(DataInputStream dataInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {

            byteArrayOutputStream = new ByteArrayOutputStream();
            int counter = 1;
            int msgSize = (int) Double.POSITIVE_INFINITY - 2;
            while (true) {
                int line = dataInputStream.read();
                long after_time = System.currentTimeMillis();
                if (counter == 1) {
                    System.out.println("Do something with the bit" + counter);
                }
                if (counter == 2) {
                    System.out.println("Size of the message equals" + line);
                    msgSize = line;
                }
                if (counter >= msgSize + 2) {
                    byteArrayOutputStream.write(line);
                    break;
                }
                counter++;
                byteArrayOutputStream.write(line);
            }

            byte[] msg = byteArrayOutputStream.toByteArray();
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(byteArrayOutputStream != null){
                byteArrayOutputStream.close();
                dataInputStream.close();
            }
        }
        return null;
    }
}
