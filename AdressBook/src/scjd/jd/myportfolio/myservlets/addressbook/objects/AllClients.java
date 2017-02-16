package scjd.jd.myportfolio.myservlets.addressbook.objects;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Andrey on 20.04.2016.
 */
public class AllClients implements Serializable {
    private HashMap<String, Client> clients = new HashMap<>();

    public HashMap<String, Client> getClients() {
        return clients;
    }

    public void setClients(HashMap<String, Client> clients) {
        this.clients = clients;
    }

    public static boolean isUserNameInDB(String fileName, String userName) {
        if (new File(fileName).exists()) {
            AllClients allClients = AllClients.readClients(fileName);
            HashMap<String, Client> clients = allClients.getClients();
            if (!clients.isEmpty()) {
                for (Iterator<HashMap.Entry<String, Client>> iterator = clients.entrySet().iterator(); iterator.hasNext(); ) {
                    HashMap.Entry<String, Client> pair = iterator.next();
                    String userNameDB = pair.getKey();
                    if (userName.equals(userNameDB)) {
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void addClient(String fileName, Client client) {
        AllClients allClients = AllClients.readClients(fileName);

        HashMap<String, Client> clients = allClients.getClients();
        clients.put(client.getUserName(), client);
        allClients.setClients(clients);

        //writing to file
        writeClients(fileName, allClients);

        //Debugging
        System.out.println("AllClients.addClient " + fileName);


    }

    public static AllClients readClients(String fileName) {
        AllClients allClients = null;
        if (new File(fileName).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                allClients = (AllClients) ois.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            allClients = new AllClients();
        }
        //debugging
        System.out.println("AllClients.readClients " + fileName);

        return allClients;
    }

    public static void writeClients(String fileName, AllClients allClients) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(allClients);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
