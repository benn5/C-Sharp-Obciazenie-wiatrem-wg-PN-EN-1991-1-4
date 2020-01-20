package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Toolbar extends JToolBar implements ActionListener {

    private JButton saveBtn;
    private JButton openBtn;
    private ToolbarListener toolbarListener;
    private JFileChooser fileChooser;


    public Toolbar() {


        //tolbar nie bedzie mozna przesuwac
        setFloatable(false);

        //przycisk do zapisu wyników do pliku
        saveBtn = new JButton();
        saveBtn.setIcon(createIcon("/images/saveIcon.gif"));


        saveBtn.setToolTipText("Zapisz do pliku na pulpicie");

        openBtn = new JButton();
        openBtn.setIcon(createIcon("/images/openIcon.gif"));
        openBtn.setToolTipText("Otwórz raport obliczeń wewnątrz programu");

        fileChooser = new JFileChooser();

        add(saveBtn);
        add(openBtn);

        saveBtn.addActionListener(this::actionPerformed);
        openBtn.addActionListener(this::actionPerformed);

    }


    public void setToolbarListener(ToolbarListener toolbarListener){
        this.toolbarListener = toolbarListener;
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton)e.getSource();

        if(clicked == saveBtn){
            if(toolbarListener != null){
                toolbarListener.saveEventOccurred();
                //System.out.println("Kliknieto zapisz - toolbar");
            }
        }

        if(clicked == openBtn){
            if(toolbarListener != null){
                toolbarListener.openEventOccurred();
                //System.out.println("kliknieto otworz - toolbar");
            }
        }
    }



    public ImageIcon createIcon(String path){

        URL url = this.getClass().getResource(path);
        //System.out.println("URL= " + url);

       if(url == null){
            System.err.println("Unable to load image: " /*+ path*/);
        }

        ImageIcon icon = new ImageIcon(url);
        return icon;
    }



}
