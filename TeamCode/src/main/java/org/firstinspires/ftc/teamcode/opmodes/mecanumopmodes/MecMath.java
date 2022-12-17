/*
* This class contains all computations required for a mecanum drive
* The motorValues() method modifies the class members to their correct values based on mecanum drive condition
* the inputMagnitude() method takes the distance of a joystick
* the inputAngle() method gets the angle of a joystick at a certain time
 */
package org.firstinspires.ftc.teamcode.opmodes.mecanumopmodes;

public class MecMath {
    double frontLeft;
    double frontRight;
    double backLeft;
    double backRight;
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
    public static double inputMagnitude(double x, double y){
        //convert cartesian to unit circle cords
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return (Math.sqrt(x * x + y * y));
    }
    public static double inputAngle(double x, double y){
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return (Math.toDegrees(Math.atan(y / x)));
    }
}
