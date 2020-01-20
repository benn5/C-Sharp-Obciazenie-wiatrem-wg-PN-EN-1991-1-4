package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PolandMapFrame extends JFrame {

    private JLabel mapLabel;
    private ImageIcon mapIcon;
    private JPanel radioBtnPanel;

    private JRadioButton firstZoneRadio;
    private JRadioButton secondZoneRadio;
    private JRadioButton thirdZoneRadio;

    private ButtonGroup zoneGroup;

    private JButton okBtn;
    private InputPanelListener inputPanelListener;


    public PolandMapFrame(){
        super("Mapa Polski - strefy wiatrowe");

        //ustalenie typu layoutu
        setLayout(new BorderLayout());



        mapIcon = new ImageIcon();
        mapLabel = new JLabel();
        radioBtnPanel = new JPanel();



        //ustawienie tekstu przy radio buttonach i ich grupowanie
        firstZoneRadio = new JRadioButton("1 strefa");
        secondZoneRadio = new JRadioButton("2 strefa");
        thirdZoneRadio = new JRadioButton("3 strefa");
        okBtn = new JButton("OK");
        zoneGroup = new ButtonGroup();


        radioBtnPanel.add(firstZoneRadio);
        radioBtnPanel.add(secondZoneRadio);
        radioBtnPanel.add(thirdZoneRadio);
        radioBtnPanel.add(okBtn);

        zoneGroup.add(firstZoneRadio);
        zoneGroup.add(secondZoneRadio);
        zoneGroup.add(thirdZoneRadio);

        //przypisanie wartosci do przyciskow
        firstZoneRadio.setActionCommand("1");
        secondZoneRadio.setActionCommand("2");
        thirdZoneRadio.setActionCommand("3");

        //ustawienie domyslego wyboru
        firstZoneRadio.setSelected(true);

        //przeskalowanie obrazu
        mapLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().
                getResource("/images/polandMap.png")).getImage().
                getScaledInstance(500, 500, Image.SCALE_SMOOTH)));

        //dodawanie komponentów okna mapy polski
        add(mapLabel, BorderLayout.CENTER);
        add(radioBtnPanel, BorderLayout.EAST);


        pack();
        setSize(new Dimension(650,550));
        setMinimumSize(new Dimension(650,550));
        setVisible(true);




        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int zone = Integer.parseInt(zoneGroup.getSelection().getActionCommand());
                //System.out.println("strefa z mpay polski: " + zone);

                InputEvent event = new InputEvent(this, zone);

                if(inputPanelListener != null){
                    inputPanelListener.inputEventOccurred(event);
                    //System.out.println("zoneRes = " + event.getZone());
                }
                dispose();
            }
        });


        layoutComponents(radioBtnPanel);

        //ustalenie polozenia okna
        setLocationRelativeTo(getParent());
    }



    public void setInputPanelListener(InputPanelListener inputPanelListener){
        this.inputPanelListener = inputPanelListener;
    }


    private void layoutComponents(JPanel panel){

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc  = new GridBagConstraints();

        Insets inset = new Insets(0,0,0,35);

        //PUSTY ELEMENT ///////////////////////////
        gc.gridy = 0;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 3;

        JPanel panelEmpty = new JPanel();

        gc.insets = new Insets(0,0,0,0);
        panel.add(panelEmpty, gc);


        //PIERWSZY RZAD - 1 STREFA            ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = inset;
        panel.add(firstZoneRadio, gc);


        //NASTEPNY RZAD - 2 STREFA            ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = inset;
        panel.add(secondZoneRadio, gc);


        //NASTEPNY RZAD - 3 STREFA          ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;


        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = inset;
        panel.add(thirdZoneRadio, gc);


        //NASTEPNY RZAD - OK BUTTON         ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;


        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = inset;
        panel.add(okBtn, gc);



        //PUSTY ELEMENT  ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 3;

        JPanel panelEmpty2 = new JPanel();
        panel.add(panelEmpty2, gc);

    }



}
