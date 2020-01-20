package calculations;

public final class Constants {

    //wspolczynnik kierunku wiatru (zalecane 1.0)
    public static final double Cdir = 1.0;

    //wpolczynnik sezonu (zalecane 1.0)
    public static final double Cseason = 1.0;

    //wspolczynnik chrpowatosci (zalecane 1.0)
    public static final double Co = 1.0;

    //wsp wysokosci jak dla terenu kat. II 0,05 m
    public static final double z0II = 0.05;

    //wysokosc minimalna i maxymalna na poziomem trenu
    public static final double zmin = 1.0;
    public static final double zmax = 200.0;

    //tablica opisująca parametr Z w zaleznosci od kategorii terenu
    //pierwszy rząd tablicy - teren koat 0, drugi rząd - kat. 1...
    //pierwsza kolumna odpowiada za Z0 druga Zmin
    public static final double[][] z0_zminTable = {{0.003,1}, //kat 0
                                                {0.01, 1},  //kat 1
                                                {0.05, 2},  //kat 2
                                                {0.3, 5},   //kat 3
                                                {1.0, 10},  //kat 4
    };

    //wps turbulencji (zalecane 1.0)
    public static final double kl = 1.0;

    //gęstość powietrza [kg/m3]
    public static final double airDensity = 1.25;

    //minimalna dopuszczalna w obliczniach wys pow. poz. morza
    public static final double Amin = 0;

}
