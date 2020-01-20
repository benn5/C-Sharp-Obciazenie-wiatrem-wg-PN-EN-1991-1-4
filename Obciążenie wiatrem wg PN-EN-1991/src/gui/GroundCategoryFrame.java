package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroundCategoryFrame extends JFrame {



    private JLabel mapLabel;
    private ImageIcon groundCatIcon;
    private JPanel radioBtnPanel;

    private JRadioButton zeroGroundCatRadio;
    private JRadioButton firstGroundCatRadio;
    private JRadioButton secondGroundCatRadio;
    private JRadioButton thirdGroundCatRadio;
    private JRadioButton fourthGroundCatRadio;

    private ButtonGroup groundCatGroup;

    private JButton okBtn;
    private InputPanelListener inputPanelListener;

    private static final String groundCategory_0 = "0 kategoria";
    private static final String groundCategory_1 = "I kategoria";
    private static final String groundCategory_2 = "II kategoria";
    private static final String groundCategory_3 = "III kategoria";
    private static final String groundCategory_4 = "IV kategoria";


    public GroundCategoryFrame(){
        super("Kategorie terenu");

        //ustalenie typu layoutu
        setLayout(new BorderLayout());



        groundCatIcon = new ImageIcon();
        mapLabel = new JLabel();
        radioBtnPanel = new JPanel();



        //ustawienie tekstu przy radio buttonach i ich grupowanie
        zeroGroundCatRadio = new JRadioButton(groundCategory_0);
        firstGroundCatRadio = new JRadioButton(groundCategory_1);
        secondGroundCatRadio = new JRadioButton(groundCategory_2);
        thirdGroundCatRadio = new JRadioButton(groundCategory_3);
        fourthGroundCatRadio = new JRadioButton(groundCategory_4);


        okBtn = new JButton("OK");
        groundCatGroup = new ButtonGroup();


        radioBtnPanel.add(zeroGroundCatRadio);
        radioBtnPanel.add(firstGroundCatRadio);
        radioBtnPanel.add(secondGroundCatRadio);
        radioBtnPanel.add(thirdGroundCatRadio);
        radioBtnPanel.add(fourthGroundCatRadio);

        radioBtnPanel.add(okBtn);

        groundCatGroup.add(zeroGroundCatRadio);
        groundCatGroup.add(firstGroundCatRadio);
        groundCatGroup.add(secondGroundCatRadio);
        groundCatGroup.add(thirdGroundCatRadio);
        groundCatGroup.add(fourthGroundCatRadio);

        //przypisanie wartosci do przyciskow
        zeroGroundCatRadio.setActionCommand("0");
        firstGroundCatRadio.setActionCommand("1");
        secondGroundCatRadio.setActionCommand("2");
        thirdGroundCatRadio.setActionCommand("3");
        fourthGroundCatRadio.setActionCommand("4");

        //ustawienie domyslego wyboru
        zeroGroundCatRadio.setSelected(true);

        //przeskalowanie obrazu
        mapLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().
                getResource("/images/groundGategory.png")).getImage().
                getScaledInstance(700, 350, Image.SCALE_SMOOTH)));

        //dodawanie komponentów okna mapy polski
        add(mapLabel, BorderLayout.WEST);
        add(radioBtnPanel, BorderLayout.EAST);


        pack();
        setSize(new Dimension(900,400));
        setMinimumSize(new Dimension(900,400));
        setVisible(true);




        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int groundCategory = Integer.parseInt(groundCatGroup.getSelection().getActionCommand());
                //System.out.println("strefa z mpay polski: " + zone);

                InputEvent event = new InputEvent(this, groundCategory, true);

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



    //DODAWANIE DO OKIENKA PRZYCISKOW
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


        //PIERWSZY RZAD - 0 KATEGORIA            ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = inset;
        panel.add(zeroGroundCatRadio, gc);


        //NASTEPNY RZAD - 1 KATEGORIA            ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = inset;
        panel.add(firstGroundCatRadio, gc);


        //NASTEPNY RZAD - 2 KATEGORIA          ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;


        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = inset;
        panel.add(secondGroundCatRadio, gc);


        //NASTEPNY RZAD - 3 KATEGORIA          ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;


        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = inset;
        panel.add(thirdGroundCatRadio, gc);


        //NASTEPNY RZAD - 4 KATEGORIA          ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;


        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = inset;
        panel.add(fourthGroundCatRadio, gc);


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
