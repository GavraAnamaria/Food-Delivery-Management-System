package dataLayer;

import business.ClientB;
import business.MenuItem;
import business.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Serializator {
    ObjectOutputStream outC;
    ObjectOutputStream outO;
    FileOutputStream fileOutC;
    FileOutputStream fileOutO;




    public void writeClient(List<ClientB> clients) {
        try {
            fileOutC = new FileOutputStream("client.ser");
            outC = new ObjectOutputStream(fileOutC);
            outC.writeObject(clients);
            outC.close();
            fileOutC.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeOrder(Map<Order, List<MenuItem>> order) {
        try {
            fileOutO = new FileOutputStream("order.ser");
            outO = new ObjectOutputStream(fileOutO);
            outO.writeObject(order);
            outO.close();
            fileOutO.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<ClientB> readClient() {
        try {
            FileInputStream fileInC = new FileInputStream("client.ser");
            ObjectInputStream inC = new ObjectInputStream(fileInC);
            List<ClientB> clients = (List<ClientB>)inC.readObject();
            inC.close();
            fileInC.close();
            return clients;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public Map<Order, List<MenuItem>>  readOrder(){
        try {
            FileInputStream fileInO = new FileInputStream("order.ser");
            ObjectInputStream inO = new ObjectInputStream(fileInO);
            Map<Order, List<MenuItem>> order = (Map<Order, List<MenuItem>> )inO.readObject();
            inO.close();
            fileInO.close();
            return order;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Map<Order, List<MenuItem>> m = new HashMap<>();
        return m;
    }

}
