package gui;

import calculations.EurocodeTableVb0;
import calculations.Report;
import calculations.Turbulence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame{

    private InputPanel inputPanel;
    private Toolbar toolbar;
    private TextPanel textPanel;
    private JTextArea textArea;
    private JSplitPane splitPane;
    private PreferencesDialog preferencesDialog;
    private JFileChooser fileChooser;
    private Preferences prefs;


    ///////////////////////
    private Report report = new Report();
    private EurocodeTableVb0 eurocodeTableVb0 = new EurocodeTableVb0();
    private Turbulence turbulence = new Turbulence();
    ///////////////////////


    public MainFrame(){
        //UKŁAD GŁÓWNEGO OKNA APLIKACJI

        //główne okno - extend JFrame - nie potrzeba frame inicjowac
        super("Obliczenia obciążenia wiatru na połać dachową");

        //STYL WYŚWIETLANIA
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            System.out.println("L&F not available");
        }




        //ustalenie polozenia okna na starcie oraz typu layoutu
        setLayout(new BorderLayout());
        setLocation(500,200);

        //zapissanie preferencji aplikacji
        prefs = Preferences.userRoot().node("user");

        //powolanie paska narzedzi MenuBar
        setJMenuBar(createMenuBar());


        //powolanie paska narzedi  na gorze okna
        toolbar = new Toolbar();

        //powolanie lewego panelu do wprowadzenia danych
        inputPanel = new InputPanel();

        //powolanie panelu tekstu
        textPanel = new TextPanel();
        textArea = new JTextArea();

        //powolanie podzieleniea okna na lewo inputpanel
        //na prawo textarea
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, textPanel);


        //ustawieniue preferencji w okienku preferencji
        preferencesDialog = new PreferencesDialog(this);

        preferencesDialog.setPrefsListener(new PreferencesListener() {
            @Override
            public void preferencesSet(double A, double z, int zone, int groundCategory) {
                //System.out.println(A+" "+z+" "+zone+" "+groundCategory + "z MAINFRAME od okienka PREFERENCJI");
                prefs.putDouble("A", A);
                prefs.putDouble("z", z);
                prefs.putInt("zone", zone);
                prefs.putInt("groundCategory", groundCategory);

                //USTAWIENIE WARTOSCI DOMYSLNYCH JAKO POCZATKOWE
                setDefaultDataToInputBoxes(A, z, zone, groundCategory);
            }
        });


        Double A = prefs.getDouble("A", 150.0);
        Double z = prefs.getDouble("z", 8.0);
        Integer zone = prefs.getInt("zone", 1);
        Integer groundCategory = prefs.getInt("groundCategory", 0);

        preferencesDialog.setDefaults(A, z, zone, groundCategory);

       //USTAWIENIE WARTOSCI DOMYSLNYCH W OKIENKACH
        setDefaultDataToInputBoxes(A, z, zone, groundCategory);


        //ustawienie okna wyboru pliku/ sciezki
        fileChooser =new JFileChooser();


        //dodawanie komponentow do okna
        add(toolbar, BorderLayout.PAGE_START);
        add(inputPanel, BorderLayout.WEST);

        add(textPanel, BorderLayout.CENTER);

        //DODANIE PASKA PRZEWIJANIA
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textArea);
        textPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);



        //glowne okno aplikacji i jego parametry
        setSize(950,600);
        setMinimumSize(new Dimension(950,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);





        //WYKONANIE OBLICZEN I WYGENEROWANI RAPORTU
        inputPanel.setInputPanelListener(new InputPanelListener() {
            @Override
            public void inputEventOccurred(InputEvent e) {
                report.appendRaportToTextArea(e, textArea);
            }
        });


        //ZAPISANIE PLIKU Z POZIOMU TOOLBARU
        toolbar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOccurred() {
                String homeDir = String.valueOf(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());

                fileChooser.setCurrentDirectory(new File(homeDir));
                fileChooser.setSelectedFile(new File(homeDir + Report.fileName));

                try {
                    try {
                        File file = fileChooser.getSelectedFile();
                        //System.out.println(file.exists() + " " + file);

                        if(file.exists()){
                            int result = JOptionPane.showConfirmDialog(MainFrame.this, "Plik istnieje, nadpisać?",
                                    "Plik istniejący", JOptionPane.YES_NO_CANCEL_OPTION);

                            System.out.println("wybrano zapis");

                            if(result == JOptionPane.YES_OPTION){
                                report.saveReport(textArea);

                            }
                        }
                        else{
                            report.saveReport(textArea);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }



            //OTWARCIE PLIKU WEWNATRZ POLA TEKSTOWEGO PROGRAMU
            @Override
            public void openEventOccurred() {


                //sciezka domyslna na pulpit
                String homeDir = String.valueOf(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());

                fileChooser.setCurrentDirectory(new File(homeDir));
                fileChooser.setSelectedFile(new File("*.txt"));

                int retrival = fileChooser.showOpenDialog(MainFrame.this);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();

                        if (file.exists()) {

                            try {
                                //jezeli plik ma rozszerzenie .txt
                                String fileName = file.getName();
                                String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
                                String txt = "txt";
                                if(!txt.equals(extension)){
                                    JOptionPane.showMessageDialog(MainFrame.this,
                                            "Wybierz plik o rozszerzeniu .txt");
                                }
                                else {
                                    //wyczyszczenie zawartosci textArerwa
                                    textArea.selectAll();
                                    textArea.replaceSelection("");

                                    BufferedReader br = new BufferedReader(new FileReader(file));

                                    String st;
                                    while ((st = br.readLine()) != null) {
                                        //System.out.println(st);
                                        textArea.append(st + "\n");
                                    }
                                }
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //System.out.println("window closing");

                dispose();

                //garbage collector
                System.gc();
            }
        });
    }


    private void setDefaultDataToInputBoxes(double A, double z, int zone, int groundCategory){
        //USTAWIENIE WARTOSCI DOMYSLNYCH AJAKO POCZATKOWE
        inputPanel.setATextField(Double.toString(A));
        inputPanel.setzTextFiled(Double.toString(z));
        inputPanel.setZoneComboBox(zone);
        inputPanel.setGroundCategoryComboBox(groundCategory);

    }

    //UTWORZENIE PASKA NARZEDZI
    public JMenuBar createMenuBar(){

        JMenuBar menuBar;
        JMenu fileMenu;
        JMenu prefsMenu;

        JMenuItem saveItem;
        JMenuItem saveAsItem;
        JMenuItem openItem;
        JMenuItem aboutItem;
        JMenuItem exitItem;
        JMenuItem prefsItem;
        /////////////////////////////

        menuBar = new JMenuBar();

        //opisy przyciskow paska narzedzi
        fileMenu = new JMenu("Plik");
        prefsMenu = new JMenu("Preferencje");


        saveItem = new JMenuItem("Zapisz na pulpicie");
        saveAsItem = new JMenuItem("Zapisz jako...");
        openItem = new JMenuItem("Otwórz plik raportu obliczeń...");
        aboutItem = new JMenuItem("O programie");
        exitItem = new JMenuItem("Wyjście");
        prefsItem = new JMenuItem("Preferencje użytkownika");



        //dodanie elementow do poszczegolnych zakladek paska narzedzi
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(openItem);
        fileMenu.add(aboutItem);
        fileMenu.add(exitItem);

        prefsMenu.add(prefsItem);

        //dodanie elementów głownych paska do paska do narzedzi
        menuBar.add(fileMenu);
        menuBar.add(prefsMenu);


        //mnemonika
        fileMenu.setMnemonic(KeyEvent.VK_P);
        aboutItem.setMnemonic(KeyEvent.VK_O);
        exitItem.setMnemonic(KeyEvent.VK_W);


        //akceleratory
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.CTRL_MASK+ java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));


        //zapisanie pliku - domyślnie na pulpicie
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("zapisano na pulpicie - menu");
                String homeDir = String.valueOf(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());

                fileChooser.setCurrentDirectory(new File(homeDir));
                fileChooser.setSelectedFile(new File(homeDir + Report.fileName));

                try {
                    try {
                        File file = fileChooser.getSelectedFile();
                        //System.out.println(file.exists() + " " + file);

                        if(file.exists()){
                            int result = JOptionPane.showConfirmDialog(MainFrame.this,
                                    "Plik istnieje, nadpisać?",
                                    "Plik istniejący", JOptionPane.YES_NO_CANCEL_OPTION);

                            System.out.println("wybrano zapis");

                            if(result == JOptionPane.YES_OPTION){
                                report.saveReport(textArea);

                            }
                        }
                        else{
                            report.saveReport(textArea);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });


        //zapisanie jako pliku
        saveAsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Zapisano jako - menu");

                String reportText = textArea.getText();
                //sciezka domyslna na pulpit
                String homeDir = String.valueOf(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());

                fileChooser.setCurrentDirectory(new File(homeDir));
                fileChooser.setSelectedFile(new File("*.txt"));

                int retrival = fileChooser.showSaveDialog(MainFrame.this);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {

                        File file = fileChooser.getSelectedFile();

                        if(file.exists()){
                            int result = JOptionPane.showConfirmDialog(MainFrame.this,
                                    "Plik istnieje, nadpisać?",
                                    "Plik istniejący", JOptionPane.YES_NO_CANCEL_OPTION);

                            if(result == JOptionPane.YES_OPTION){

                                FileWriter fw = new FileWriter(fileChooser.getSelectedFile());
                                fw.write(reportText);
                                fw.close();
                            }
                        }
                        else {
                            FileWriter fw = new FileWriter(fileChooser.getSelectedFile());
                            fw.write(reportText);
                            fw.close();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        //otwieranie pliku
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //sciezka domyslna na pulpit
                String homeDir = String.valueOf(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory());

                fileChooser.setCurrentDirectory(new File(homeDir));
                fileChooser.setSelectedFile(new File("*.txt"));

                int retrival = fileChooser.showOpenDialog(MainFrame.this);
                if (retrival == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fileChooser.getSelectedFile();

                        if (file.exists()) {

                            try {
                                //jezeli plik ma rozszerzenie .txt
                                String fileName = file.getName();
                                String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
                                String txt = "txt";
                                if(!txt.equals(extension)){
                                    JOptionPane.showMessageDialog(MainFrame.this,
                                            "Wybierz plik o rozszerzeniu .txt");
                                }
                                else {
                                    report.openReport();
                                }
                            }
                            catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });



        //otwieranie nowego okna "O programie"
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("kliknieto \"O programie\" ");
                AboutFrame aboutFrame = new AboutFrame();
            }
        });

        //zamykanie aplikacji
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("klinieto exit");

                Container parent = menuBar.getParent();

                int action = JOptionPane.showConfirmDialog(parent, "Na pewno zamknąć program?",
                        "Wyjście", JOptionPane.OK_CANCEL_OPTION);

                if(action == JOptionPane.OK_OPTION) {


                    //zamykanie wszystkich okien z poziomu glownego okna
                    Window[] windows = Window.getWindows();
                    for(Window window: windows){
                        //System.out.println("window: " + window);
                        window.dispose();
                    }
                }
            }
        });

        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preferencesDialog.setVisible(true);
            }
        });



        return menuBar;
    }




}

