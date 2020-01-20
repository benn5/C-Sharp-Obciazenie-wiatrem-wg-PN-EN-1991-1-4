package calculations;

import gui.InputEvent;

public class Turbulence {

    private EurocodeTableVb0 eurocode = new EurocodeTableVb0();

    //wybor z0 na podst kat terenu
    public double chooseZ0 (InputEvent e) {
        double z0 = 1;
        int groundCategory = e.getGroundCategory();


        switch (groundCategory) {
            case 0:
                z0 = Constants.z0_zminTable[0][0];
                break;
            case 1:
                z0 = Constants.z0_zminTable[1][0];
                break;
            case 2:
                z0 = Constants.z0_zminTable[2][0];
                break;
            case 3:
                z0 = Constants.z0_zminTable[3][0];
                break;
            case 4:
                z0 = Constants.z0_zminTable[4][0];
                break;
            default:
                z0 = 1;
                break;
        }
        return  z0;
    }

    public double Ivz(InputEvent e){
        double z = e.getZ();
        return Constants.kl / (Constants.Co * Math.log(z / chooseZ0(e)));
    }



    //wartosc szczytowego cisnienia predkosci wiatru
    public double qpz(InputEvent e){
        return (1 + 7*Ivz(e)) * 0.5 * Constants.airDensity * eurocode.vmz(e) * eurocode.vmz(e) * 0.001;
    }


    //wartosc sczytowego cisnienia wiatru
    public double qb(InputEvent e){
        return 0.5 * Constants.airDensity * eurocode.vb(e) * eurocode.vb(e) * 0.001;
    }

}
