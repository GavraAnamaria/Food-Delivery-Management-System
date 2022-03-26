package business;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.opencsv.bean.CsvToBeanBuilder;
import dataLayer.FWriter;
import dataLayer.Serializator;
import java.io.FileReader;
import java.util.*;


/**
 * @invariant isWellFormed()
 */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing {

    private int idPers;
    private Map<Order, List<MenuItem>> orders;
    private List<ClientB> clients;
    private List<MenuItem> menu = new ArrayList<>();
    private Serializator ser = new Serializator();

    public DeliveryService(){
             this.clients = ser.readClient();
             ClientB.setId(clients.size());
             this.orders = ser.readOrder();
             Order.setId(orders.size());
        idPers = 0;
        importProducts(); }

    public void setIdPers ( int id){
        this.idPers = id;
    }

    public List<MenuItem> getMenu () {
        return this.menu;
    }



//________________________________________________________________________________________________________________________________
//============================================================================================================[  MANAGER  ]=======
//--------------------------------------------------------------------------------------------------------------------------------



    //---------------------------------------------------------[ IMPORT PRODUCTS ]----------------------------------------------------------
    /**
     * @pre true
     * @post menu!=null
     */
    public void importProducts()  {
        assert isWellFormed();
        List<BaseProduct> baseProducts = null;
        try {
            baseProducts = new CsvToBeanBuilder(new FileReader("products.csv"))
                    .withType(BaseProduct.class).build().parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        menu = baseProducts.stream().filter(distinctByKey(BaseProduct::getTitle))
                .collect(Collectors.toList());
        menu.forEach(x -> System.out.println(x.toString() + "\n ... \n"));
        assert menu!=null;
    }



    //---------------------------------------------------------[ MANAGE PRODUCTS ]----------------------------------------------------------
    /**
     * @pre title!=null
     * @post menu.getSize() == menu.getSize()@pre + 1
     */
    public void addProd(String title, float rating, int calories, int protein, int fat, int sodium, int price) {
        assert isWellFormed();
        assert title!=null;
        int s = menu.size();
        menu.add(new BaseProduct(title, rating, calories, protein, fat, sodium, price));
        System.out.println("\nProdusul '" + title + "' a fost adaugat.\n");
        assert menu.size() == s+1;
    }
    public void updateProd(String title, String rating, String calories, String protein, String fat, String sodium, String price) {
        BaseProduct b = null;
        for (MenuItem m : menu) {
            if (m.getTitle().equals(title) && (m instanceof BaseProduct)) {
                b = (BaseProduct) m;
                break; } }
        if (b != null) {
            if (rating != null)
                b.setRating(Integer.parseInt(rating));
            if (calories != null)
                b.setCal(Integer.parseInt(calories));
            if (protein != null)
                b.setProt(Integer.parseInt(protein));
            if (fat != null)
                b.setFat(Integer.parseInt(fat));
            if (sodium != null)
                b.setSodium(Integer.parseInt(sodium));
            if (price != null)
                b.setPrice(Integer.parseInt(price));
            System.out.println("\nProdusul a fost modificat.\n");
        }
    }

    /**
     * @pre str!=null
     * @post menu.getSize() <= menu.getSize()@pre
     */
    public void delProd(String str) {
        assert isWellFormed();
        assert str!=null;
        int si = menu.size();
        List<MenuItem> b = menu.stream().filter(s -> s.getTitle().equals(str)).collect(Collectors.toList());
        for (MenuItem m : b) {
            menu.remove(m);
            System.out.println("\nProdusul '" + m.getTitle() + "' a fost sters.\n");
        }
        assert si >= menu.size();
    }
    public void addCompProd(CompositeProduct compProd) {
        menu.add(compProd);
    }





    //---------------------------------------------------------[ GENERATE REPORTS ]----------------------------------------------------------
    /**
     * @pre h1!=null h2!=null
     * @post @result.getSize()<orders.getSize()
     */
    public List<Order> generateRapH(String h1, String h2) {           // Produsele comandate intre orele specificate
        assert isWellFormed();
        assert h1 != null;
        assert h2 != null;
        List<Order> o = new ArrayList<>();
        for (Order or : orders.keySet()) {
            String s = Character.toString(or.getDate().charAt(14)) + Character.toString(or.getDate().charAt(15));
            if ((Integer.parseInt(s) >= Integer.parseInt(h1)) && (Integer.parseInt(s) <= Integer.parseInt(h2))) {
                o.add(or);
            }
        }
        assert o.size() < orders.size();
        return o;
    }
    public String generateRapVal(String nr, String val) {      // clientii care au comandat de mai mult de un numar specificat de ori produse cu o valoare mai mare decat cea specificata
        String s = "";
        for (ClientB c : clients) {
            long n = (orders.keySet().stream().filter(x-> x.getPrice()>Integer.parseInt(val) && x.getClientId()==c.getId()).count());
            if (orders.keySet().stream().filter(x-> x.getPrice()>Integer.parseInt(val) && x.getClientId()==c.getId()).count() > Integer.parseInt(nr)) {
                s += "ID CLIENT: " + c.getId() + "\nNUMAR COMENZI: " + n + "\n\n";
            }
        }
        return s;
    }


    /**
     * @pre nr!=null
     * @post true
     */
    public String generateRapNr(String nr) {           //Produse comandate de mai mult de un numar specificat de ori
        assert nr!=null;
        String s ="";
        assert isWellFormed();
        for (MenuItem mi : menu) {
            for(List<MenuItem> mItems : orders.values()){
                int n = (int) mItems.stream().filter(x->x.getTitle()==mi.getTitle()).count();
                if (n > Integer.parseInt(nr)) {
                    s+= mi.toString() + "\nNumar comenzi: " + n + "\n\n";
                }
            }
        }
        return s;
    }
    public List<Order> generateRapDay(String day) {        // comenzile plasate intr o anumita zi
        List<Order> o = new ArrayList<>();
        for (Order or : orders.keySet()) {
            String s = Character.toString(or.getDate().charAt(8)) + Character.toString(or.getDate().charAt(9));
            if (Integer.parseInt(s) == Integer.parseInt(day)) {
                o.add(or);
            }
        }
        return o;
    }


//________________________________________________________________________________________________________________________________
//============================================================================================================[ CLIENT ]=========
//--------------------------------------------------------------------------------------------------------------------------------


    //---------------------------------------------------------[ REGISTER ]-----------------------------------------------------------
    /**
     * @pre client!=null
     * @post clients.getSize() == clients.getSize()@pre + 1
     */
    public void register(ClientB client){
        assert isWellFormed();
        assert client != null;
        int siz = clients.size();
        clients.add(client);
        for(ClientB c: clients)
            System.out.println(c.toString());
        ser.writeClient(clients);
        assert clients.size() > siz + 1;
    }
    public ClientB login(String uname, String password){
        for(ClientB c: clients)
            if(c.equals(new ClientB(uname, password)))
                return c;
            return null;
    }





    //---------------------------------------------------------[ SEARCH ]-----------------------------------------------------------
    /**
     * @pre t!=null
     * @post @result.get(0).getTitle().contains(t)
     */
    public List<MenuItem> searchTitle(String t) {
        assert isWellFormed();
        assert !t.isEmpty();
        List<MenuItem> m = menu.stream()
                .filter(x -> x.getTitle().contains(t))
                .collect(Collectors.toList());
        assert m.get(0).getTitle().contains(t);
        return m;
    }
    public List<MenuItem> searchPrice(String t) {
        return menu.stream()
                .filter(x -> x.computePrice() == Integer.parseInt(t))
                .collect(Collectors.toList());
    }


    /**
     * @pre t!=null
     * @post @result.get(0).getCalories()==t
     */
    public List<MenuItem> searchRating(String t) {
        assert isWellFormed();
        assert  t.length() == 0;
        List<MenuItem> m = menu.stream()
                .filter(x -> {
                    if (x instanceof BaseProduct)
                        return ((BaseProduct) x).getRating() == Float.parseFloat(t);
                    return false;
                })
                .collect(Collectors.toList());
        assert ((BaseProduct)m.get(0)).getCalories() == Integer.parseInt(t);
        return m;
    }
    public List<MenuItem> searchCalories(String t) {
        return menu.stream()
                .filter(x -> (x instanceof BaseProduct) && (((BaseProduct) x).getCalories() == Integer.parseInt(t)))
                .collect(Collectors.toList());
    }




    //---------------------------------------------------------[ CREATE ORDER ]-----------------------------------------------------------
    /**
     * @pre true
     * @post orders.getSize() == orders.getSize()@pre + 1
     */
    public void createOrder(List<MenuItem> items) {
        assert isWellFormed();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'la' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        int siz = orders.size();
        int price = 0;
        for (MenuItem m : items)
            price += m.computePrice();
        Order o = new Order(idPers, formatter.format(date), price);
        orders.put(o, items);
        notifyUpdate(o);
        FWriter fileWriter = new FWriter();

        for(Order ord:orders.keySet())
            System.out.println(ord);
        ser.writeOrder(orders);

        for (Order or : orders.keySet()) {
            String s = or.toString();
            for (MenuItem m : orders.get(or))
                s = s + "|       -" + m.getTitle() + ";    PRET: " + m.computePrice() + "\n";
            s += "|\n| TOTAL: " + or.getPrice() + "\n|_______________________________________________________________________________\n\n\n\n";
            fileWriter.writeText(s);
        }
        fileWriter.closeFW();
        assert orders.size() > siz;
    }


    //---------------------------------------------------------[ WELL FORMED ]-----------------------------------------------------------
    protected boolean isWellFormed() {
        if(clients == null || orders == null || menu == null)
            return false;
    return true;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, String> seen = new ConcurrentHashMap<>();
        return t -> seen.put(keyExtractor.apply(t), "") == null;
    }
}