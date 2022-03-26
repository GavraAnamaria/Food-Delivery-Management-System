package business;

import java.io.IOException;
import java.util.List;
/**
 * @invariant isWellFormed()
 */
public interface IDeliveryServiceProcessing {

/**
 * @pre true
 * @post menu!=null
 */
    void importProducts() throws IOException, ClassNotFoundException;

    /**
     * @pre title!=null
     * @post menu.getSize() == menu.getSize()@pre + 1
     */
    void addProd(String title, float rating, int calories, int protein, int fat, int sodium, int price);

    /**
     * @pre str!=null
     * @post menu.getSize() == menu.getSize()@pre + 1
     */
    void delProd(String str);

    /**
     * @pre compProd!=null
     * @post menu.getSize() == menu.getSize()@pre + 1
     */
    //void addCompProd(CompositeProduct compProd);

    /**
     * @pre true
     * @post orders.getSize() == orders.getSize()@pre + 1
     */
    void createOrder(List<MenuItem> items);

    /**
     * @pre h1!=null h2!=null
     * @post @result.getSize()<orders.getSize()
     */
    List<Order> generateRapH(String h1, String h2);

    /**
     * @pre nr!=null
     * @post @result.getSize()<orders.getSize()
     */
    String generateRapNr(String nr);

    /**
     * @pre client!=null
     * @post clients.getSize() == clients.getSize()@pre + 1
     */
    void register(ClientB client);

    /**
     * @pre t!=null
     * @post @result.get(0).getTitle().contains(t)
     */
    List<MenuItem> searchTitle(String t);

    /**
     * @pre t!=null
     * @post @result.get(0).getCalories()==t
     */
    List<MenuItem> searchRating(String t);


    }
