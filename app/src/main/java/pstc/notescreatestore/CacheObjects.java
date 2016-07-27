package pstc.notescreatestore;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by Chandrakant on 26-07-2016.
 */
public final class CacheObjects {
    private CacheObjects(){}
    public static void writeObject(Context c, String key,Object obj){
        try {
            FileOutputStream fos = c.openFileOutput(key,Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object readObject(Context c,String key){
        try {
            FileInputStream fis = c.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            return obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
