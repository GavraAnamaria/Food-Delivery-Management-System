package business;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientB implements Serializable {
    private int id;
    private String uname;
    private String password;
    private static AtomicInteger nr = new AtomicInteger(0);

    public ClientB(String name, String password){
        nr.getAndIncrement();
        this.id = nr.get();
        this.uname = name;
        this.password = password;
    }

    public static void setId(int id) {
        nr.getAndSet(id);
    }
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object anotherObject){
        return (uname.equals(((ClientB)anotherObject).uname)) && (this.password.equals(((ClientB)anotherObject).password));
    }

    public String toString(){
        return "Username: " + uname + "\nPassword: " + password + "\n";
    }

}
