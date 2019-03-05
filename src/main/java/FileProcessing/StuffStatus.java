package FileProcessing;

import java.util.ArrayList;
import java.util.List;

public class StuffStatus {
    private Boolean[] booksStatus;
    private List<Integer> rBooks;

    public StuffStatus(Boolean[] booksStatus) {
        this.booksStatus = booksStatus;
        rBooks = new ArrayList<>();
    }

    public void setBooksStatus(int book, boolean status) {
        booksStatus[book]=status;
    }

    public List<Integer> getNotDownFile(){
        rBooks = new ArrayList<>();
        for(int i = 0; i<booksStatus.length; i++){
            if(!booksStatus[i]){
                rBooks.add(i);
            }
        }
        return rBooks;
    }
}
