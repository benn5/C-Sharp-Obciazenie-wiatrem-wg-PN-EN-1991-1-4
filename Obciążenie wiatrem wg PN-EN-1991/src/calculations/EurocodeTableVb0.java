package calculations;

import gui.InputEvent;

public class EurocodeTableVb0 {


    public double countVb0(InputEvent e){

        double A = e.getA();
        int zone = e.getZone();

        double vb0 = 0;

        if(A <= 300 && A >= 0){
            switch(zone){
                case 1:
                case 3:
                    vb0 = 22;
                    break;
                case 2:
                    vb0 = 26;
                    break;
                default:
                    vb0 = 0.00;
                    break;
            }
        } else if(A > 300){
            switch(zone){
                case 1:
                case 3:
                    vb0 = 22*(1 + 0.0006*(A - 300));
                    break;
                case 2:
                    vb0 = 26;
                    break;
                default:
                    vb0 = 0;
                    break;
            }
        }
             return vb0;
    }



    //obliczenie wartości Vb
    public double vb(InputEvent e){
        return Constants.Cdir * Constants.Cseason * countVb0(e);
    }



    //obliczenie wartości Vm(z) - srednia predkosc wiatru
    //DANE POBRANE Z BAZY DANYCH
    public double vmz (InputEvent e) {


        double z = e.getZ();
        int groundCategory = e.getGroundCategory();

        double z0 = 1;
        double zmin = 1;

        //wybór wartości z0 i zmin w zależności od kategorii terenu
        switch (groundCategory) {
            case 0:
                z0 = Constants.z0_zminTable[0][0];
                zmin = Constants.z0_zminTable[0][1];
                break;
            case 1:
                z0 = Constants.z0_zminTable[1][0];
                zmin = Constants.z0_zminTable[1][1];
                break;
            case 2:
                z0 = Constants.z0_zminTable[2][0];
                zmin = Constants.z0_zminTable[2][1];
                break;
            case 3:
                z0 = Constants.z0_zminTable[3][0];
                zmin = Constants.z0_zminTable[3][1];
                break;
            case 4:
                z0 = Constants.z0_zminTable[4][0];
                zmin = Constants.z0_zminTable[4][1];
                break;
            default:
                z0 = 1;
                zmin = 1;
                break;
        }

        if(z < zmin){
            z = zmin;
        }

        double krz = 0.19 * Math.pow((z0 / Constants.z0II), 0.07);

        //wartosc sredniej predkosci wiatru
        double vmz = krz * Math.log(z / z0) * Constants.Co * vb(e);
        return  vmz;
    }


}
