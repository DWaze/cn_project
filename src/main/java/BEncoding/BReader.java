package BEncoding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BReader
{
    /**
     * Int debencoding method
     * @param _data Byte array
     * @param _index Index of start point
     * @return Long value
     * @throws Exception
     */

    private static int readInt(byte[] _data, int[] _index) throws Exception
    {
        if((char)_data[_index[0]++] != 'i') throw new Exception();

        ArrayList<Character> result = new ArrayList<>();
        char read;
        while((read = (char)_data[_index[0]++]) != 'e')
        {
            if(read >= 'a') throw new Exception();
            result.add(read);
        }

        int resultInt = Integer.parseInt(result.stream().map(e->e.toString()).collect(Collectors.joining()));

//        System.out.println(resultInt);

        return resultInt;
    }

    /**
     * Bytes debencoding method
     * @param _data Byte array
     * @param _index Index of the array
     * @return The byte value
     */
    private static byte[] readBytes(byte[] _data, int[] _index) throws Exception
    {
        ArrayList<Character> lengthList = new ArrayList<>();
        char read;
        while((read = (char)_data[_index[0]]) != ':')
        {
            if(read >= 'a') throw new Exception();
            lengthList.add(read);
            _index[0]++;
        }

        _index[0]++;

        int length = Integer.parseInt(lengthList.stream().map(e->e.toString()).collect(Collectors.joining()));

        byte[] result = new byte[length];
        int it;
        for(it = 0; it < length; it++, _index[0]++)
        {
            result[it] = _data[_index[0]];
        }

//        System.out.println(new String(result, "UTF-8"));

        return result;
    }

    /**
     * String debencoding method
     * @param _data Byte array
     * @param _index Index of the array
     * @return String value
     * @throws Exception
     */
    private static String readString(byte[] _data, int[] _index) throws Exception
    {
        return new String(readBytes(_data, _index), "UTF-8");
    }

    /**
     * List debencoding method
     * @param _data Byte array
     * @param _index Index of the array
     * @return List<Object>
     * @throws Exception
     */
    private static List<Object> readList(byte[] _data, int[] _index) throws Exception
    {
        List<Object> result = new ArrayList<>();
        _index[0]++;

        while((char)_data[_index[0]] != 'e')
            result.add(read(_data, _index));

        return result;
    }

    /**
     * Dictionary reader method
     * @param _data Byte array to decode
     * @param _index Index of start point
     * @return Map<String,Object> the dictionary
     * @throws Exception
     */
    private static Map<String,Object> readMap(byte[] _data, int[] _index) throws Exception
    {
        Map<String,Object> result = new HashMap<>();
        _index[0] ++;

        while((char)_data[_index[0]] != 'e')
        {
            //System.out.println(readString(_data, _index) + " ---- " + read(_data, _index));
            result.put(readString(_data, _index), read(_data, _index));

        }

        return result;
    }

    /**
     * Bencoding reader main method
     * @param _data Byte array to decode
     * @param _index Index of starting point
     * @return Object value
     * @throws Exception
     */
    public static Object read(byte[] _data, int[] _index) throws Exception
    {
        //Args test
        if (_data.length <= _index[0]) throw new Exception();

        char test = (char)_data[_index[0]];
        switch (test) {
            case 'd':
                return readMap(_data, _index);

            case 'l':
                return readList(_data, _index);

            case 'i':
                return readInt(_data, _index);

            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return readBytes(_data, _index);

            default:
                throw new Exception();
        }
    }
}
