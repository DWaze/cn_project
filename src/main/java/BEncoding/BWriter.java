package BEncoding;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class BWriter
{
    /**
     * Long bencoding method
     * @param _long Long to bencode
     * @param _out Stream to write on
     * @throws IOException
     */
    public static void writeLong(long _long, OutputStream _out) throws IOException
    {
        _out.write('i');
        _out.write(Long.toString(_long).getBytes("ASCII"));
        _out.write('e');
    }

    /**
     * Bytes bencoding method
     * @param _bytes Bytes to write
     * @param _out Stream to write on
     * @throws IOException
     */
    private static void writeBytes(byte[] _bytes, OutputStream _out) throws IOException
    {
        _out.write(Integer.toString(_bytes.length).getBytes("ASCII"));
        _out.write(':');
        _out.write(_bytes);
    }

    /**
     * String bencoding method
     * @param _str String to bencode
     * @param _out Strem to write on
     * @throws IOException
     */
    private static void writeString(String _str, OutputStream _out) throws IOException
    {
        writeBytes(_str.getBytes("UTF-8"), _out);
    }

    /**
     * List bencoding method
     * @param _list List to bencode
     * @param _out Stream to write on
     * @throws IOException
     */
    private static void writeList(List<Object> _list, OutputStream _out) throws IOException
    {
        _out.write('l');
        for(Object obj : _list)
        {
            write(obj, _out);
        }
        _out.write('e');
    }

    /**
     * Dictionary bencoding method
     * @param _map Map to bencode
     * @param _out Stream to write on
     * @throws IOException
     */
    private static void writeMap(Map<String,Object> _map, OutputStream _out) throws IOException
    {
        SortedMap<String,Object> sortedMap = new TreeMap<>(_map);
        _out.write('d');
        for (Map.Entry<String, Object> e : sortedMap.entrySet())
        {
            writeString(e.getKey(), _out);
            write(e.getValue(), _out);
        }
        _out.write('e');
    }

    /**
     * Bencoding writter main method
     * @param _object Object to bencode
     * @param _out Stream to write on
     * @throws IOException
     */
    public static void write(Object _object, OutputStream _out) throws IOException
    {
        if(_object instanceof String) writeString((String)_object, _out);
        else if(_object instanceof Map) writeMap((Map)_object, _out);
        else if(_object instanceof List) writeList((List)_object, _out);
        else if(_object instanceof byte[]) writeBytes((byte[])_object, _out);
        else if(_object instanceof Number) writeLong(((Number)_object).longValue(), _out);
        else throw new Error("Unencodable type");
    }
}
