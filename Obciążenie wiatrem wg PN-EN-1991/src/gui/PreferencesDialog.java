package gui;

import javax.swing.*;
import java.awt.*;

public class PreferencesDialog extends JDialog {

    private JLabel ALabel;
    private JLabel zLabel;
    private JLabel AUnits;
    private JLabel zUnits;
    private JLabel zoneLabel;
    private JLabel groundCategoryLabel;

    private JTextField ATextField;
    private JTextField zTextField;

    private JComboBox zoneComboBox;
    private JComboBox groundCategoryComboBox;

    private JButton savePreferencesBtn;
    private PreferencesListener preferencesListener;


    private static final String ALabelText = "Wys. nad poz. morza  (> 0): ";
    private static final String zLabelText = "Wys. dachu nad poz. terenu: ";
    private static final String zoneLabelText = "Strefa wiatrowa: ";
    private static final String groundCategoryLabelText = "Kategoria terenu: ";

    private static String AUnitsText = "[m n.p.m.]";
    private static final String zUnitsText = "[m]";



    public PreferencesDialog(JFrame frame){

        super(frame, "Ustawienia użytkownika", false);

        ALabel = new JLabel(ALabelText);
        zLabel = new JLabel(zLabelText);
        AUnits = new JLabel(AUnitsText);
        zUnits = new JLabel(zUnitsText);

        zoneLabel = new JLabel(zoneLabelText);
        groundCategoryLabel = new JLabel(groundCategoryLabelText);

        ATextField = new JTextField(10);
        zTextField = new JTextField(10);

        zoneComboBox = new JComboBox();
        groundCategoryComboBox = new JComboBox();

        savePreferencesBtn = new JButton("Zapisz jako ustawienia domyślne");


        //okno preferencji i jego parametry
        setSize(500,200);
        setMinimumSize(new Dimension(500,200));


        Dimension dim = getPreferredSize();
        dim.width = 250;
        setMinimumSize(dim);


        //zapobieganie wprowadzenia liter do cyfrowych okinek A i z
        Utils.numbersOnly(ATextField);
        Utils.numbersOnly(zTextField);


        //ustawienie wartości w comboboxach
        DefaultComboBoxModel zoneModel = new DefaultComboBoxModel();
        zoneModel.addElement("I");
        zoneModel.addElement("II");
        zoneModel.addElement("III");

        zoneComboBox.setModel(zoneModel);
        zoneComboBox.setSelectedIndex(0);


        DefaultComboBoxModel groundCategoryModel = new DefaultComboBoxModel();
        groundCategoryModel.addElement("0");
        groundCategoryModel.addElement("I");
        groundCategoryModel.addElement("II");
        groundCategoryModel.addElement("III");
        groundCategoryModel.addElement("IV");

        groundCategoryComboBox.setModel(groundCategoryModel);
        groundCategoryComboBox.setSelectedIndex(0);


        //zapobieganie wprowadzenia liter do cyfrowych okinek A i z
        Utils.numbersOnly(ATextField);
        Utils.numbersOnly(zTextField);


        //AKCJE
        zoneComboBox.addActionListener(e -> {
            //System.out.println("strefa combo " + (zoneComboBox.getSelectedIndex() + 1));
        });

        zoneComboBox.addMouseWheelListener(e -> Utils.comboBoxScroller(e, zoneComboBox));



        groundCategoryComboBox.addActionListener(e -> {
            //System.out.println("kat terenu combo " + groundCategoryComboBox.getSelectedIndex());
        });

        groundCategoryComboBox.addMouseWheelListener(e -> Utils.comboBoxScroller(e, groundCategoryComboBox));


        savePreferencesBtn.addActionListener(e -> {

            //nie moze zostać pusty textField
            if(ATextField.getText().length() == 0){
                JOptionPane.showMessageDialog(PreferencesDialog.this,
                        "Proszę wpisać wysokość nad poziomem morza");
                return;
            }
            if(zTextField.getText().length() == 0){
                JOptionPane.showMessageDialog(PreferencesDialog.this,
                        "Proszę wpisać wysokość dachu budynku");
                return;
            }

            double A = Double.parseDouble(ATextField.getText());
            double z = Double.parseDouble(zTextField.getText());
            int zone = zoneComboBox.getSelectedIndex() + 1;
            int groundCategory = groundCategoryComboBox.getSelectedIndex();
            //System.out.println(A+" "+z+" "+zone+" "+groundCategory + "z okienka dialogowego preferencji");

            if(preferencesListener != null){
                preferencesListener.preferencesSet(A, z, zone, groundCategory);
            }

            setVisible(false);
        });



        //ustawienie wyglądu okienka
        layoutComponents();

        //lokalizacja reletywnie wzgledem parenta
        setLocationRelativeTo(getParent());
    }



    //ZAPAMIETANIE WARTSCI WLASCIOWSCI DOMYSLNYCH
    public void setDefaults(double A, double z, int zone, int groundCategory){
        ATextField.setText(Double.toString(A));
        zTextField.setText(Double.toString(z));
        zoneComboBox.setSelectedIndex(zone - 1);
        groundCategoryComboBox.setSelectedIndex(groundCategory);
    }



    public void setPrefsListener(PreferencesListener preferencesListener){
        this.preferencesListener = preferencesListener;
    }



    //ustawienie wygladu okna z preferencjami
    public void layoutComponents(){

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
        add(zTextField, gc);

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


        //NASTEPNY RZAD - przycisk zapisu///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.3;

        gc.anchor = GridBagConstraints.SOUTH;
        gc.insets = new Insets(0,10,0,10);
        add(savePreferencesBtn, gc);



        //NASTEPNY RZAD - pusty element   ///////////////////////////
        gc.gridy++;
        gc.gridx = 0;

        gc.weightx = 1;
        gc.weighty = 0.1;


        JPanel panelEmpty = new JPanel();
        add(panelEmpty, gc);
    }



}
