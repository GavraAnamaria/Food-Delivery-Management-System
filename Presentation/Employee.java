package Presentation;

import business.DeliveryService;
import business.MenuItem;
import business.Order;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Employee extends JFrame implements Observer  {
        private final List<JButton> button = new ArrayList<>();
        private final List<JPanel> panel = new ArrayList<>();
        private final JPanel p = new JPanel();
        private final JScrollPane scrollPane = new JScrollPane(p);
        private DeliveryService ds;

        public Employee(DeliveryService d) {
                this.ds = d;
                scrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setPreferredSize(new Dimension(500, 400));


                p.setLayout(new GridLayout(0, 1));
                scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

                List<MenuItem> l = new ArrayList<>();
                Order o = new Order(3, "11/12/1212",  123);
                Order o1 = new Order(3, "11/12/1212",  134);
                Order o2 = new Order(3, "11/12/1212",  111);
                Order o3 = new Order(3, "11/12/1212",  109);
                this.update(o);
                this.update(o);
                this.update(o1);
                this.update(o1);
                this.update(o1);
                this.update(o2);
                this.update(o2);
                this.update(o2);
                this.update(o3);
                this.update(o3);
                this.update(o3);
                this.update(o3);
                this.update(o3);

                JPanel p1 = new JPanel();
                JButton b = new JButton("Vizualizare comenzi");
                b.addActionListener(new vizListener(this, this));
                p1.setBorder(BorderFactory.createEmptyBorder(150,0,0,0));
                p1.add(b);
                this.setContentPane(p1);
                this.pack();
                this.setTitle("ANGAJAT");
                this.setVisible(true);
                this.setBounds(100,100,514,436);
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        @Override
        public void update(Order or){
                JPanel order = new JPanel();
                JButton b = new JButton("FINALIZATA");
                b.addActionListener(new fListener(b));
                button.add(b);
                order.add(new JLabel(or.toString1()+ "    " +button.indexOf(b)));
                order.add(b);
                p.add(order);
                panel.add(order);
                this.revalidate();
                this.pack();
        }

        public void update1(JButton b){
                int i = button.indexOf(b);
                p.remove(panel.get(i));
                panel.remove(i);
                button.remove(i);
                this.revalidate();
                this.pack();
        }


        public class fListener implements ActionListener {
                JButton b;

                public fListener(JButton b) {
                        this.b = b;
                }

                public void actionPerformed(ActionEvent e) {
                        update1(b);
                        }
        }

        public class vizListener implements ActionListener {
                JFrame f;
                Observer o;
                public vizListener(JFrame f, Observer o) {
                        this.f = f;
                        this.o = o;
                }
                public void actionPerformed(ActionEvent e) {
                        ds.addObserver(o);
                        f.setContentPane(scrollPane);
                        f.revalidate();
                }
        }

}

