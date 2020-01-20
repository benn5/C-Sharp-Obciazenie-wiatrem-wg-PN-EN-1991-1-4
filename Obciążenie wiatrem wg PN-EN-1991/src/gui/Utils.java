package gui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

public class Utils {



    public static void numbersOnly(JTextField textField){


        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String value = textField.getText();

                //zabronione jest wprowadzenie wielu przecinkow (kod ASCII dla  '.' wynosi 46)
                if (e.getKeyChar() == 46 && (textField.getText().indexOf('.') != -1) ){
                    //System.out.println("zla ilosc kropek");
                    textField.setEditable(false);
                    return;
                }

                //dla zaków backspace i delete nic sie nie dzieje
                if(e.getKeyChar() == 8 || e.getKeyChar() == 127){
                    textField.setEditable(true);
                    return;
                }

                //jak rowniz dla strzlek
                if(e.getKeyChar() == 24 || e.getKeyChar() == 25 ||
                        e.getKeyChar() == 26 || e.getKeyChar() == 27){
                    textField.setEditable(true);
                    return;

                }

                if( (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == 46){
                    textField.setEditable(true);
                }

                else{
                    textField.setEditable(false);
                }
            }
        });
    }



    //scrollowanie w comboboxas
    public static void comboBoxScroller(MouseWheelEvent e, JComboBox comboBox) {

        int notches = e.getWheelRotation();
        int selectedIndex = (comboBox.getSelectedIndex());

        //scroll  wheel UP
        if (notches < 0) {
            //System.out.println("notch = " + notches);
            selectedIndex -= 1;

            if (selectedIndex < 0) {
                //System.out.println("wykroczono poza doł ustawiam na 0 index");
                selectedIndex = 0;
            }
            comboBox.setSelectedIndex(selectedIndex);
        }

        //scroll wheel DOWN
        if (notches > 0) {
            //System.out.println("notch = " + notches);
            selectedIndex += 1;

            if (selectedIndex > comboBox.getItemCount() - 1) {
                //System.out.println("wykroczono poza gore ustawiam na max index");
                selectedIndex = comboBox.getItemCount() - 1;
            }

            comboBox.setSelectedIndex(selectedIndex);
        }
    }





}
