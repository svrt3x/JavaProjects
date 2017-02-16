package scjd.jd.myportfolio.myservlets.addressbook.objects;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Andrey on 22.02.2016.
 */
public class AllContacts implements Serializable {
    private ArrayList<Contact> contacts = new ArrayList<>();

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public static AllContacts readContacts(String fileDirectory){
        AllContacts allContacts = null;
        if((new File(fileDirectory)).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileDirectory))) {
               allContacts = (AllContacts) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            allContacts = new AllContacts();
        }


        return allContacts;
    }

    public static void writeContacts(String fileDirectory, AllContacts allContacts){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileDirectory))) {
            oos.writeObject(allContacts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
