package pstc.notescreatestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chandrakant on 26-07-2016.
 */
public class NoteContent {

    public static List<Note> ITEMS = new ArrayList<Note>();

    public static Map<String, Note> ITEM_MAP = new HashMap<String, Note>();


    public static void addItem(Note item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public static class Note implements Serializable{
        public String id;
        public String heading;
        public String detail;

        Note(String _id,String _heading,String _detail){
            this.id = _id;
            this.heading = _heading;
            this.detail = _detail;
        }

        public String getId(){
            return this.id;
        }

        public void setId(String _id){
            this.id = _id;
        }

        public void setHeading(String head){
            this.heading = head;
        }
        public String getHeading(){
            return this.heading;
        }

        public void setDetail(String details){
            this.detail = details;
        }
        public String getDetail(){
            return this.detail;
        }

        @Override
        public String toString() {
            return this.heading;
        }

    }


}
