package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AboutFrame extends JFrame {


    public AboutFrame(){

        JFrame frame = new JFrame();

        frame.setTitle("O programie");

        setSize(500,200);
        setMinimumSize(new Dimension(500,200));


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("nacisnieto");
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    setVisible(false);
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        setVisible(true);
        layoutControl();
    }



    private void layoutControl(){

        JPanel panel = new JPanel();

        //tworzenie obwolutki i tytulu wewn. panelu
        int space = 15;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createTitledBorder("Szczegółowe inforormacje o aplikacji");
        panel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

        //layout okienka informacyjnego
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //wciecia
        Insets rightPadding = new Insets(0,0,0,20);
        Insets noPadding = new Insets(0,0,0,0);


        //PIERWSZY RZAD - wersja             ///////////////////////////
        gc.gridy = 0;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.fill = GridBagConstraints.NONE;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        panel.add(new JLabel("Wersja programu: "), gc);


        gc.gridx = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        panel.add(new JLabel("v.0.01"), gc);


        //NASTEPNY RZAD - norma projektowa            ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        panel.add(new JLabel("Wykorzystana norma projektowa: "), gc);


        gc.gridx = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        panel.add(new JLabel("PN-EN-1991-1-4 2008 r."), gc);


        //NASTEPNY RZAD - autor             ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        panel.add(new JLabel("Autor: "), gc);


        gc.gridx = 1;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        panel.add(new JLabel("Benedykt Karwiński"), gc);


        //dodanie labeli do panelu glownego ramki
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);


        //lokalizacja reletywnie wzgledem peranta
        setLocationRelativeTo(getParent());

    }

}
