package Presentation;

import business.BaseProduct;
import business.ClientB;
import business.DeliveryService;
import business.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Client extends JFrame  {
    private final JTextField uName = new JTextField(16);
    private final JTextField pass = new JTextField(16);
    private final JPanel p1 = new JPanel();
    private final JPanel panel1 = new JPanel();
    private final JPanel panel3 = new JPanel();
    private final JPanel searchPanel = new JPanel();
    private final JTextArea textArea = new JTextArea();
    private final JPanel panelOrder = new JPanel();
    private final JComboBox menuItems = new JComboBox();
    private List<MenuItem> items;
    private List<MenuItem> sItems = new ArrayList<>();
    private DeliveryService ds;


    public Client(DeliveryService d) {
         ds = d;
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(0, 2, -35,10));
        p.setBorder(BorderFactory.createEmptyBorder(100, 50, 10, 60));
        p.add(new JLabel("NUME UTILIZATOR"));
        p.add(uName);
            p.add(new JLabel("PAROLA"));
        p.add(pass);
        JButton b = new JButton("AUTENTIFICARE");
        JButton b1 = new JButton("INREGISTRARE");
        b.addActionListener(new logListener(this));
        b1.addActionListener(new registerListener());
        p1.add(p);
        p1.add(b);
        p1.add(b1);


        JPanel panel = new JPanel();
         panel.setLayout(new GridLayout(0, 1,10, 10));
         panel.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));
         JButton button1 = new JButton("VIZUALIZARE PRODUSE");
         JButton button2 = new JButton("CAUTARE PRODUS");
         JButton button3 = new JButton("PLASARE COMANDA");
         JButton back = new JButton("<=");
         back.addActionListener( new bListener(this));
         button1.addActionListener( new vizListener(this));
         button2.addActionListener( new searchListener(this));
         button3.addActionListener( new order1Listener(this));
         panel.add(button1);
         panel.add(button2);
         panel.add(button3);
         panel.add(back);
         panel1.add(panel);



//--------------------------------------------------------------[ CAUTARE PRODUSE ]--------------------------------

        JPanel sPanel = new JPanel();
        JPanel sPanel1 = new JPanel();
        JPanel sPanel2 = new JPanel();
        JPanel sPanel3 = new JPanel();
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        JButton button4 = new JButton("CAUTARE");
        JButton button5 = new JButton("CAUTARE");
        JButton button6 = new JButton("CAUTARE");
        JButton button7 = new JButton("CAUTARE");
        JButton back3 = new JButton("<=");

        JTextArea textArea1 = new JTextArea();
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setPreferredSize(new Dimension(450, 190));
        scrollPane1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        JTextField title = new JTextField(16);
        JTextField cal = new JTextField(16);
        JTextField rating = new JTextField(16);
        JTextField price = new JTextField(16);
        sPanel.add(new JLabel("TITLU: "));
        sPanel.add(title);
        sPanel.add(button4);
        sPanel1.add(new JLabel("RATING: "));
        sPanel1.add(rating);
        sPanel1.add(button5);
        sPanel2.add(new JLabel("CALORII: "));
        sPanel2.add(cal);
        sPanel2.add(button6);
        sPanel3.add(new JLabel("PRET: "));
        sPanel3.add(price);
        sPanel3.add(button7);
        back3.addActionListener( new b1Listener(this));
        button4.addActionListener( new sTitleListener(title, textArea1, this));
        button5.addActionListener( new sRatingListener(rating, textArea1, this));
        button6.addActionListener( new sCalListener(cal, textArea1, this));
        button7.addActionListener( new sPriceListener(price, textArea1, this));
        searchPanel.add(sPanel);
        searchPanel.add(sPanel1);
        searchPanel.add(sPanel2);
        searchPanel.add(sPanel3);
        searchPanel.add(scrollPane1);
        searchPanel.add(back3);


//--------------------------------------------------------------[ VIZUALIZARE PRODUSE ]--------------------------------
         JScrollPane scrollPane = new JScrollPane(textArea);
         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         scrollPane.setPreferredSize(new Dimension(500, 350));
         scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
         JButton back1 = new JButton("<=");
         back1.addActionListener( new b1Listener(this));
         panel3.add(scrollPane);
         panel3.add(back1);


//------------------------------------------------------------------[ PLASARE COMANDA ]--------------------------------
        JPanel pComp = new JPanel();
        pComp.setLayout(new GridLayout(0, 2,-185, 0));
        pComp.setBorder(BorderFactory.createEmptyBorder(140, 0, 12, 0));
        menuItems.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -300));
        pComp.add(new JLabel("ADAUGA PRODUS:"));
        pComp.add(menuItems);
        panelOrder.add(pComp);
        JButton b3 = new JButton("ADAUGARE");
        JButton back2 = new JButton("<=");
        JButton b4 = new JButton("FINALIZARE");
        back2.addActionListener(new b1Listener(this));
        b3.addActionListener(new addProdListener());
        b4.addActionListener(new orderListener());
        panelOrder.add(b3);
        panelOrder.add(b4);
        panelOrder.add(back2);


        this.setContentPane(p1);
         this.pack();
         this.setTitle("CLIENT");
         this.setVisible(true);
         this.setBounds(100,100,514,435);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public class logListener implements ActionListener {
        JFrame f;
        public logListener(JFrame f) {
            this.f = f;
        }
        public void actionPerformed(ActionEvent e) {
            ClientB c = ds.login(uName.getText(), pass.getText());
            if(c!=null){
            ds.setIdPers( c.getId());
            f.setContentPane(panel1);
            f.revalidate();}
        }
    }

    public class registerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ds.register(new ClientB(uName.getText(), pass.getText()));
        }
    }




    public class vizListener implements ActionListener {
        JFrame f;
        public vizListener(JFrame f) {
            this.f = f;
        }
        public void actionPerformed(ActionEvent e) {
            String s = "";
            for(MenuItem m:ds.getMenu()){
                s = s+m.toString() + "\n\n" ;
            }
            textArea.setText(s);
            System.out.println("\n!GATA!\n");
            f.setContentPane(panel3);
            f.revalidate();
        }
    }


    public class order1Listener implements ActionListener {
        JFrame f;
        public order1Listener(JFrame f) {
            this.f = f;
        }
        public void actionPerformed(ActionEvent e) {
            items = new ArrayList<>();
            for(MenuItem m:ds.getMenu()) {
                menuItems.addItem(m.getTitle());
            }
            f.setContentPane(panelOrder);
            f.revalidate();
        }
    }

    public class bListener implements ActionListener {
        JFrame f;
        public bListener(JFrame f) {
            this.f = f;
        }
        public void actionPerformed(ActionEvent e) {
            f.setContentPane(p1);
            f.revalidate();
        }
    }
    public class searchListener implements ActionListener {
        JFrame f;
        public searchListener(JFrame f) {
            this.f = f;
        }
        public void actionPerformed(ActionEvent e) {
            f.setContentPane(searchPanel);
            f.revalidate();
        }
    }

    public class b1Listener implements ActionListener {
        JFrame f;
        public b1Listener(JFrame f) {
            this.f = f;
        }
        public void actionPerformed(ActionEvent e) {
            f.setContentPane(panel1);
            f.revalidate();
        }
    }


    public class addProdListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            items.add(ds.getMenu().get(menuItems.getSelectedIndex()));
        }
    }

    public class orderListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ds.createOrder(items);
            items = new ArrayList<>();
        }
    }

    public class sTitleListener implements ActionListener {
        private JTextField tf;
        private JTextArea ta;
        private JFrame f;
        public sTitleListener(JTextField text,JTextArea ta, JFrame f){
            this.tf = text;
            this.ta = ta;
            this.f = f; }
        public void actionPerformed(ActionEvent e) {
            sItems.clear();
            sItems = ds.searchTitle(tf.getText());
            String s = "";
            for(MenuItem m:sItems)
                s+= m.getTitle() + "\n";
            ta.setText(s);
            f.revalidate();
        }
    }

    public class sPriceListener implements ActionListener {
        private JTextField tf;
        private JTextArea ta;
        private JFrame f;
        public sPriceListener(JTextField text,JTextArea ta, JFrame f){
            this.tf = text;
            this.ta = ta;
            this.f = f; }
        public void actionPerformed(ActionEvent e) {
            sItems.clear();
            sItems = ds.searchPrice(tf.getText());
            String s = "";
            for(MenuItem m:sItems)
                s+= m.getTitle() + "    PRET: " + m.computePrice() + "\n";
            ta.setText(s);
            f.revalidate();
        }
    }

    public class sRatingListener implements ActionListener {
        private JTextField tf;
        private JTextArea ta;
        private JFrame f;
        public sRatingListener(JTextField text,JTextArea ta, JFrame f){
            this.tf = text;
            this.ta = ta;
            this.f = f; }
        public void actionPerformed(ActionEvent e) {
            sItems.clear();
            sItems = ds.searchRating(tf.getText());
            String s = "";
            for(MenuItem m:sItems)
                s+= m.getTitle() + "    RATING: " + ((BaseProduct)m).getRating() + "\n";
            ta.setText(s);
            f.revalidate();
        }
    }

    public class sCalListener implements ActionListener {
        private JTextField tf;
        private JTextArea ta;
        private JFrame f;
        public sCalListener(JTextField text,JTextArea ta, JFrame f){
            this.tf = text;
            this.ta = ta;
            this.f = f; }
        public void actionPerformed(ActionEvent e) {
            sItems.clear();
            sItems = ds.searchCalories(tf.getText());
            String s = "";
            for(MenuItem m:sItems)
                s+= m.getTitle() + "    CALORII: " + ((BaseProduct)m).getCalories() + "\n";
            ta.setText(s);
            f.revalidate();
        }
    }

}
