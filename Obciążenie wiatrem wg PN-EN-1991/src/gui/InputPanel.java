package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class InputPanel extends JPanel {

    private JLabel ALabel;
    private JLabel zLabel;
    private JLabel AUnits;
    private JLabel zUnits;
    private JLabel zoneLabel;
    private JLabel groundCategoryLabel;

    private JTextField ATextField;
    private JTextField zTextFiled;

    private JComboBox zoneComboBox;
    private JComboBox groundCategoryComboBox;

    private JButton chooseZoneBtn;
    private JButton chooseGroundCategoryBtn;
    private JButton calculateBtn;
    private InputPanelListener inputPanelListener;

    private PolandMapFrame polandMapFrame;
    private GroundCategoryFrame groundCategoryFrame;


    private static final String ALabelText = "Wys. nad poz. morza  (> 0): ";
    private static final String zLabelText = "Wys. dachu nad poz. terenu: ";
    private static final String zoneLabelText = "Strefa wiatrowa: ";
    private static final String groundCategoryLabelText = "Kategoria terenu: ";

    private static String AUnitsText = "[m n.p.m.]";
    private static final String zUnitsText = "[m]";

    private static final String chooseZoneBtnText = "Pomóż mi wybrać strefę wiatrową";
    private static final String chooseGroundCategoryBtnText = "Pomóż mi wybrać kategorię terenu";
    private static final String calculateBtnText = "Oblicz";


    //SETTERY ZEBY NIE TRZEBA BYLO ZMIANNEYCH ROBIC PUBLICZNYCH!!!
    public void setATextField(String text) {
        ATextField.setText(text);
    }

    public void setzTextFiled(String text) {
        zTextFiled.setText(text);
    }

    public void setZoneComboBox(int index) {
        zoneComboBox.setSelectedIndex(index - 1);
    }

    public void setGroundCategoryComboBox(int index) {
        groundCategoryComboBox.setSelectedIndex(index);
    }


    public InputPanel(){

        ALabel = new JLabel(ALabelText);
        zLabel = new JLabel(zLabelText);

        AUnits = new JLabel(AUnitsText);
        zUnits = new JLabel(zUnitsText);

        zoneLabel = new JLabel(zoneLabelText);
        groundCategoryLabel = new JLabel(groundCategoryLabelText);

        ATextField = new JTextField(10);
        zTextFiled = new JTextField(10);

        zoneComboBox = new JComboBox();
        groundCategoryComboBox = new JComboBox();

        chooseZoneBtn = new JButton(chooseZoneBtnText);
        chooseGroundCategoryBtn = new JButton(chooseGroundCategoryBtnText);
        calculateBtn = new JButton(calculateBtnText);


        //mnemonika dla przyciskow
        calculateBtn.setMnemonic(KeyEvent.VK_B);
        chooseZoneBtn.setMnemonic(KeyEvent.VK_W);
        chooseGroundCategoryBtn.setMnemonic(KeyEvent.VK_T);


        Dimension dim = getPreferredSize();
        dim.width = 250;
        setMinimumSize(dim);



        //zapobieganie wprowadzenia liter do cyfrowych okinek A i z
        Utils.numbersOnly(ATextField);
        Utils.numbersOnly(zTextFiled);


        //ustawienie wartości w comboboxach
        DefaultComboBoxModel zoneComboBoxModel = new DefaultComboBoxModel();
        zoneComboBoxModel.addElement("I");
        zoneComboBoxModel.addElement("II");
        zoneComboBoxModel.addElement("III");

        zoneComboBox.setModel(zoneComboBoxModel);
        zoneComboBox.setSelectedIndex(0);


        DefaultComboBoxModel groundCategoryComboBoxModel = new DefaultComboBoxModel();
        groundCategoryComboBoxModel.addElement("0");
        groundCategoryComboBoxModel.addElement("I");
        groundCategoryComboBoxModel.addElement("II");
        groundCategoryComboBoxModel.addElement("III");
        groundCategoryComboBoxModel.addElement("IV");

        groundCategoryComboBox.setModel(groundCategoryComboBoxModel);
        groundCategoryComboBox.setSelectedIndex(0);




        //SCROLLOWANIE NA COMBOBOXACH
        zoneComboBox.addMouseWheelListener(e -> Utils.comboBoxScroller(e, zoneComboBox));

        groundCategoryComboBox.addMouseWheelListener(e -> Utils.comboBoxScroller(e, groundCategoryComboBox));



        //OTWIERANIE NOWEGO OKNA POMOCNICZEGO Z UWAGI NA KLIKNIECIA PRZYCISKOW
        chooseZoneBtn.addActionListener(e -> {
            //System.out.println("kliknieto pomoc w wyborze strefy");
            polandMapFrame = new PolandMapFrame();

            polandMapFrame.setInputPanelListener(ev -> {
                //System.out.println(ev.getZone());
                zoneComboBox.setSelectedIndex(ev.getZone() - 1);
            });
        });



        chooseGroundCategoryBtn.addActionListener(e -> {
            //System.out.println("kliknieto pomoc w wyborze kat terenu");

            groundCategoryFrame = new GroundCategoryFrame();
            groundCategoryFrame.setInputPanelListener(ev -> groundCategoryComboBox.setSelectedIndex(ev.getGroundCategory()));
        });



        //PRZYCISK DO OBLICZEN
        calculateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("kliknieto oblicz");

                //nie moze zostać pusty textField
                if(ATextField.getText().length() == 0){
                    JOptionPane.showMessageDialog(InputPanel.this,
                            "Proszę wpisać wysokość nad poziomem morza");
                    return;
                }
                if(zTextFiled.getText().length() == 0){
                    JOptionPane.showMessageDialog(InputPanel.this,
                            "Proszę wpisać wysokość dachu budynku");
                    return;
                }

                double A = Double.parseDouble(ATextField.getText());
                double z = Double.parseDouble(zTextFiled.getText());

                //nalezy dodac 1 bo strefy zaczynaja sie od 1 nie od zera
                int zone = (zoneComboBox.getSelectedIndex() + 1);
                int groundCategory = groundCategoryComboBox.getSelectedIndex();
                //System.out.println(A +" "+ z +" "+ zone +" "+ groundCategory);


                //przeslanie danych przez interfejs to klasy ustalajacej
                //wartosci inputu
                InputEvent event = new InputEvent(this, A, z, zone, groundCategory);

                if(inputPanelListener != null){
                    inputPanelListener.inputEventOccurred(event);
                }
            }
        });



        //PO STAREMU
        //@Override new ActionListener -> potem Action performed!!

        zoneComboBox.addActionListener(e -> {
            //System.out.println("strefa combo " + (zoneComboBox.getSelectedIndex() + 1));
        });

        groundCategoryComboBox.addActionListener(e -> {
            //System.out.println("kat terenu combo " + groundCategoryComboBox.getSelectedIndex());
        });



        //ramka i tytuł wokól komponentów input
        Border innerBorder = BorderFactory.createTitledBorder("Dane do wpisania");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        //dodaj komponenty do layoutu
        layoutComponents();
    }



    public void setInputPanelListener(InputPanelListener inputPanelListener){
        this.inputPanelListener = inputPanelListener;
    }



    private void layoutComponents(){

        setLayout(new GridBagLayout());
        GridBagConstraints gc  = new GridBagConstraints();

        //PIERWSZY RZAD - A             ///////////////////////////
        gc.gridy = 0;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.fill = GridBagConstraints.NONE;

        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,10,0,10);
        add(ALabel, gc);


        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,10,0,10);
        add(ATextField, gc);

        gc.gridx = 2;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,10,0,10);
        add(AUnits, gc);

        //NASTEPNY RZAD - z             ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,10,0,10);
        add(zLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,10,0,10);
        add(zTextFiled, gc);

        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,10,0,10);
        add(zUnits, gc);


        //NASTEPNY RZAD - zone          ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,10,0,10);
        add(zoneLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,10,0,0);
        add(zoneComboBox, gc);

        //NASTEPNY RZAD - groundCategory///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,10,0,10);
        add(groundCategoryLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.insets = new Insets(0,10,0,0);
        add(groundCategoryComboBox, gc);


        //NASTEPNY RZAD - przycisk pomoc w strefie wiatrowej   ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,10,0,10);

        chooseZoneBtn.setPreferredSize(new Dimension(240,28));
        add(chooseZoneBtn, gc);



        //NASTEPNY RZAD - przycisk pomoc w kategorii terenu   ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,10,0,10);

        chooseGroundCategoryBtn.setPreferredSize(new Dimension(240,28));
        add(chooseGroundCategoryBtn, gc);



        //NASTEPNY RZAD - przycisk oblicz   ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,10,0,10);
        add(calculateBtn, gc);



        //NASTEPNY RZAD - pusty element   ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 3;


        JPanel panelEmpty = new JPanel();
        add(panelEmpty, gc);
    }



}
