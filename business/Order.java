package business;

import dataLayer.Serializator;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Order implements Serializable {
    private int id;
    private String date;
    private int clientId;
    private int price;
    private static AtomicInteger nr = new AtomicInteger(0);

    public Order(int clientId, String date, int price){
        this.id = nr.get();
        nr.getAndIncrement();
        this.date = date;
        this.clientId = clientId;
        this.price = price;
    }

    public static void setId(int id) {
        nr.getAndSet(id);
    }

    public int getPrice(){
        return price;
    }
    public String getDate(){
        return date;
    }

    public String toString1(){
        return "ID comanda: " + id +" ID Client: " + clientId + " Data: " + date + " Valoare: "+ price;
    }

    public int hashCode(){
        return id;
    }

    @Override
    public String toString() {
        return "_______________________________________________________________________________\n| ID comanda: " + id +"\n| ID Client: " + clientId + "\n| Plasata in data de: " + date + "\n| Contine: \n";
    }

    public int getClientId() {
        return this.clientId;
    }
}
