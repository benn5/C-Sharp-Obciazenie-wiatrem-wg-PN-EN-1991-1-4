package calculations;

import gui.InputEvent;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Formatter;

public class Report {

    private Formatter f;
    private EurocodeTableVb0 eurocode = new EurocodeTableVb0();
    private Turbulence turbulence = new Turbulence();
    private ProcessBuilder pb;
    public static String fileName = "\\Raport.txt";



    //OBLICZENIA
    public void appendRaportToTextArea(InputEvent e, JTextArea textArea){

        //wyswietlanie wynikow z dokladnoscia do dwoch miejsc po przecinku
        DecimalFormat df = new DecimalFormat("#.##");


        //po ponownym przeliczeniu nie będzie duplikatów
        textArea.selectAll();
        textArea.replaceSelection("");


        String first = "Wysokość na poziomem morza wynosi: " + e.getA() + " [m n.p.m.]\n" +
                "Wysokość dachu nad poziom terenu: " + e.getZ() + " [m]\n" +
                "Strefa wiatrowa: " + e.getZone() + "\n" +
                "Kategoria terenu: " + e.getGroundCategory() +"\n\n\n";

        String second ="Wartość podstawowa bazowej prędkości wiatru: " + eurocode.countVb0(e) + " [m/s]\n\n\n";

        String Vb = String.valueOf(df.format(eurocode.countVb0(e)));
        String Vmz = df.format(eurocode.vmz(e));


        String third = "Cdir = " + Constants.Cdir + " [-]\n" +
                "Cseason = " + Constants.Cseason + " [-]\n" +
                "Prędkość bazowa Vb = " + Constants.Cdir + " * " +  Constants.Cseason +  " * " + Vb + " = " + Vb + " [m/s]\n" +
                "Srednia prędkość wiatru Vm(z) = " + Vmz + " [m/s]\n\n\n";


        String Ivz = df.format(turbulence.Ivz(e));
        String fourth = "Współczynnik chropowatości terenu = " + turbulence.chooseZ0(e) + " [m]\n" +
                "Intensywność turbulencji = " + Ivz + " [-]\n";

        String qb = df.format(turbulence.qb(e));
        String qpz = df.format(turbulence.qpz(e));

        String fifth = "Wartość szczytowego ciśnienia wiatru: " + qb + " [kPa]\n" +
                "Wartość szczytowego ciścnienia prędkości wiatru wynosi: " + qpz + " [kPa]\n";

        //System.out.println(first + second + third + fourth + fifth);
        textArea.append(first + second + third + fourth + fifth);

    }



    public void saveReport(JTextArea textArea) throws IOException {

        //text z textarey z wynikamui
        String text = textArea.getText();

        //sciezka na pulpit
        String homeDir = String.valueOf(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());


        BufferedWriter writer = new BufferedWriter(new FileWriter(homeDir + fileName));
        writer.write(text);
        writer.close();

        //System.out.println("report TEXT:\n" + text);
        //System.out.println(homeDir + fileName);
    }

    public void openReport() throws IOException{

        //sciezka na pulpit
        String homeDir = String.valueOf(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());

        pb = new ProcessBuilder("Notepad.exe", homeDir + fileName);
        pb.start();
    }



}
