/*
* This class contains all computations required for a mecanum drive
* The returnMotorValues() method returns the power doubles required for mecanum in an array
* The inputMagnitude() method returns remapped magnitude of x,y on gamepad
* The inputAngle() method returns remapped angle of x,y on gamepad
* the motorValues() method and class members of MecMath class are commented out, they are from previous iterations of this ccode
 */
package org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.mech;

public class MecMath {
    //double frontLeft;
    //double frontRight;
    //double backLeft;
    //double backRight;
    /*
    public void motorValues(double rotation, double speed, double deg){
        //note about deg
        //0 is straight forward, 90 is to the left, 180 is backwards, 270 is to the right

        deg += 45; //mecanum 45 degree roller offset


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
     */

    public static double[] returnMotorValues(double rotation, double power, double angle){
        angle += 45;
        double yPower = power * Math.sin(Math.toRadians(angle));
        double xPower = power * Math.sin(Math.toRadians(angle));
        double frontLeftPower = xPower;
        double backRightPower = xPower;
        double frontRightPower = yPower;
        double backLeftPower = yPower;
        if (Math.abs(yPower) < Math.abs(xPower)){
            frontRightPower += rotation * 0.29;
            backLeftPower -= rotation * 0.29;
        } else {
            frontLeftPower += rotation * 0.29;
            backRightPower -= rotation * 0.29;
        }
        double[] motorValues = {frontLeftPower, frontRightPower, backLeftPower, backRightPower};
        return motorValues;
    }

    public static double inputMagnitude(double x, double y){
        //convert cartesian to unit circle cords
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return Math.hypot(x, y);
    }
    public static double inputAngle(double x, double y){
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return (Math.toDegrees(Math.atan(y / x)));
    }
}
