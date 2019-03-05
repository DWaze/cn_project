package Librifier;


import BEncoding.BReader;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class LibraryReader
{
    /**
     * Cutting Books method
     * @param _data Bytes to read
     * @return Hash book array
     * @throws Exception
     */
    int counter = 0;

    private ArrayList<byte[]> cutBooks(byte[] _data) throws Exception
    {
        byte[] result = new byte[20];

        ArrayList<byte[]> finalList = new ArrayList<>();
        int it;
        int count = 0;
        for(it = 0; it < _data.length; it++, count++)
        {
            if(count == 20)
            {
                if(counter == 720){
                    System.out.println("counter in : "+counter);
                }
                counter++;
                count = 0;
                finalList.add(result);
                result = new byte[20];
            }
            result[count] = _data[it];
        }
        finalList.add(result);
        result = new byte[20];
        System.out.println(counter);
        System.out.println(finalList.size());
        return finalList;
    }

    /**
     * Map to Library method
     * @param _map Map of the library
     * @return Library object
     * @throws Exception
     */
    private Library toLibrary(Map<String,Object> _map) throws Exception
    {
        Library result = new Library();
        SortedMap<String,Object> sortedMap = new TreeMap<>(_map);
        for (Map.Entry<String, Object> e : sortedMap.entrySet())
        {
            switch (e.getKey()) {
                case "announce":
                    result.setHubURL(new String((byte[])e.getValue(), "UTF-8"));
                    break;

                case "created by":
                    result.setAuthor(new String((byte[])e.getValue(), "UTF-8"));
                    break;

                case "creation date":
                    result.setCreationDate((int)e.getValue());
                    break;

                case "info":
                    SortedMap<String,Object> sortedMapInfo = new TreeMap<>((Map)e.getValue());
                    for (Map.Entry<String, Object> eInfo : sortedMapInfo.entrySet())
                    {
                        switch (eInfo.getKey()) {
                            case "length":
                                result.setStuffLength((int)eInfo.getValue());
                                break;

                            case "name":
                                result.setStuffName(new String((byte[])eInfo.getValue(), "UTF-8"));
                                break;

                            case "piece length":
                                result.setBookLength((int)eInfo.getValue());
                                break;

                            case "pieces":
                                result.setBooks(cutBooks((byte[])eInfo.getValue()));
                                break;

                            default:
                                throw new Exception();
                        }
                    }
                    break;

                default:
                    throw new Exception();
            }
        }

        return result;
    }

    /**
     * Read main method
     * @param _libraryPath Path to the .libr file
     * @return Library
     * @throws Exception
     */
    public Library read(String _libraryPath) throws Exception
    {
        byte[] libraryBytes;
        try (FileInputStream in = new FileInputStream(_libraryPath)) {
            libraryBytes = new byte[in.available()];
            in.read(libraryBytes);
        }

        int[] index = {0};

        Map<String,Object> dict = (Map<String,Object>) BReader.read(libraryBytes, index);

        return toLibrary(dict);
    }
}
