package org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.op;

public class MecMath {
    double frontLeft;
    double frontRight;
    double backLeft;
    double backRight;
    public void motorValues(double rotation, double speed, double deg){


        deg += 45; //due to mecanum having rollers 45 degrees offset, we change the deg to reflect this and this shifts the cordinate plane


        //Y axis is frontRight and backLeft
        //X axis is frontLeft and backRight
        //The following takes angle, makes vector with magnitude speed, calculates height and base of triangle formed
        double yPower = speed * Math.sin(Math.toRadians(deg));
        double xPower = speed * Math.cos(Math.toRadians(deg));
        frontLeft = xPower;
        backRight = xPower;
        frontRight = yPower;
        backLeft = yPower;

        //rotation comes into effect
        if (Math.abs(yPower) < Math.abs(xPower)){
            frontRight += rotation * 0.29;
            backLeft -= rotation * 0.29;
        } else {
            frontLeft += rotation * 0.29;
            backRight -= rotation * 0.29;
        }

    }
    public static double inputMagnitude(double x, double y){
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return (Math.sqrt(x * x + y * y));
    }
    public static double inputDirection(double x, double y){
        return (Math.toDegrees(Math.atan(y / x)));
    }
}
