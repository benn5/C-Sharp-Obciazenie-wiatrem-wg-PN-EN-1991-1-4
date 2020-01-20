package gui;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private JTextArea textArea;


    public TextPanel(){
        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createEtchedBorder());


        //textarea zajmuje całą powierzchnie
        setLayout(new BorderLayout());

        //dodanie paska scrolowania - wrazie wypadku
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }




}
