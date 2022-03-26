import Presentation.Administrator;
import Presentation.Client;
import Presentation.Employee;
import business.DeliveryService;

public class MainClass {
    public static void main(String[] args) {
        DeliveryService d = new DeliveryService();
        Employee observer = new Employee(d);
        Client c = new Client(d);
        Administrator a = new Administrator(d);
    }
}
