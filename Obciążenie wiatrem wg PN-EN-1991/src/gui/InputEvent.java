package gui;

import java.util.EventObject;

public class InputEvent extends EventObject {

    private double A;
    private double z;
    private int zone;
    private int groundCategory;


    InputEvent(Object source, double A, double z, int zone, int groundCategory){

        super(source);
        this.A=A;
        this.z=z;
        this.zone=zone;
        this.groundCategory=groundCategory;
    }

    InputEvent(Object source, int zone){
        super(source);
        this.zone = zone;
    }

    InputEvent(Object source, int groundCategory, boolean isGroundCategory){
        super(source);
        this.groundCategory = groundCategory;
    }



    public double getA() {
        return A;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getZone() {
        return zone;
    }

    public int getGroundCategory() {
        return groundCategory;
    }

}
