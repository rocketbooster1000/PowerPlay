package org.firstinspires.ftc.teamcode;

public class Constants {
    //Drive constants
    public static final double ROTATION_CONSTANT = 0.6;//how fast will the robot turn (as a percentage)
    public static final int MECANUM_MOTOR_NUMBER = 4; //unused, was intended for ftclib
    public static final int MECANUM_FRONT_LEFT_MOTOR = 0; //the following four variables are for arrays
    public static final int MECANUM_FRONT_RIGHT_MOTOR = 1;
    public static final int MECANUM_BACK_LEFT_MOTOR = 2;
    public static final int MECANUM_BACK_RIGHT_MOTOR = 3;
    public static final double DRIVE_POWER_MODIFIER = 0.8; //how fast will the robot drive (as a percentage)
    public static final double SLOW_DRIVE_MODIFIER = 0.3; //a requested slower speed
    //Slide constants
    public static final double MOTOR_SLIDE_POWER = 0.2; //how fast will the slide move (as a percentage)
    public static final int GROUND_POSITION = 0;//folowing variables are encoder tick values
    public static final int LOW_POSITION = 1900; //low junction
    public static final int MEDIUM_POSITION = 3000; //medium junction
    public static final int HIGH_POSITION = 4250; //high junction
    public static final int CONE_ONE = 100; //conestack 1
    public static final int CONE_TWO = 200; //coestack 2
    public static final int CONE_THREE = 300; //conestack 3
    public static final int CONE_FOUR = 400; //conestack 4
    public static final int RED_ZONE = 1600; //the height at which it is safe to rotate the claw, this was initially 10
    public static final int LINEAR_SLIDE_MINIMUM = 0; //lowest point for linear slide
    public static final int LINEAR_SLIDE_MAXIMUM = 4650; //highest point for linear slide
    public static final int LINEAR_SLIDE_MARGIN_ERROR = 10; //a margin of error to account for PID
    //Rotation servo
    public static final double SLIDE_SERVO_ZERO_POSITION = 0.71;
    public static final double SLIDE_SERVO_ROTATED_POSITION = 0.04; //should be 0.04 for testing its been changed
    //Claw constants
    public static final double CLAW_MIN = 0.01; //release
    public static final double CLAW_MAX = 0.3; //grab
    //Auto Constants
    public static class Auto{
        public static final double ONE_TILE_STRAFE = 3000;
        public static final double ONE_TILE_FORWARD = 3000;
        public static final double QUARTER_ROTATION = 1000;
    }


    /*
    Remaps the joystick
    It makes the input from the joystick go from being data on a square plane to being on a circular plane */
    static double[] mapJoystick(double x, double y){
        double m_x = x * Math.sqrt(1 - y * y / 2);
        double m_y = y * Math.sqrt(1 - x * x / 2);
        double[] converted = {m_x, m_y};
        return converted;
    }
    //This finds r which is a distance (so r and theta work together
    public static double inputMagnitude(double x, double y){
        double[] cords = mapJoystick(x, y);
        return (Math.hypot(cords[0], cords[1]));
    }
    /*
    This finds theta which is an angle, (theta and r work together to map points)
      */
    public static double inputAngle(double x, double y){
        double[] cords = mapJoystick(x, y);
        if (cords[0] == 0){
            if (cords[1] > 0){
                return 0;
            } else if (cords[1] < 0){
                return 180;
            } else {
                return 0;
            }
        }
        double deg = (Math.toDegrees(Math.atan(cords[1] / cords[0])) - 90);
        if (x < 0){
            return deg + 180;
        }
        return deg;
    }
    //This calculates the power of the motors
    public static double[] returnMecanumValues(double rotation, double strafe, double forward, double heading, double scalePower){
        double angle = inputAngle(strafe, forward) + 45 - heading;
        double power = inputMagnitude(strafe, forward);
        double xPower = power * Math.cos(Math.toRadians(angle));
        double yPower = power * Math.sin(Math.toRadians(angle));
        double frontLeft = xPower;
        double frontRight = yPower;
        double backLeft = yPower;
        double backRight = xPower;
        frontRight -= (rotation * ROTATION_CONSTANT);
        backLeft += (rotation * ROTATION_CONSTANT);
        frontLeft += (rotation * ROTATION_CONSTANT);
        backRight -= (rotation * ROTATION_CONSTANT);
        frontLeft *= scalePower;
        frontRight *= scalePower;
        backLeft *= scalePower;
        backRight *= scalePower;
        double[] values = {frontLeft, frontRight, backLeft, backRight};
        return values;
    }

    //used for time based auto
    //applies power based off of angle and speed instead of strafe and forward
    public static double[] returnMecanumValuesAuto(double magnitude, double angle, double rotation, double heading, double scalePower){
        double xPower = magnitude * Math.cos(Math.toRadians(angle + 45 - heading));
        double yPower = magnitude * Math.sin(Math.toRadians(angle + 45 - heading));
        double frontLeft = xPower;
        double frontRight = yPower;
        double backLeft = yPower;
        double backRight = xPower;
        frontRight -= (rotation * ROTATION_CONSTANT);
        backLeft += (rotation * ROTATION_CONSTANT);
        frontLeft += (rotation * ROTATION_CONSTANT);
        backRight -= (rotation * ROTATION_CONSTANT);
        frontLeft *= scalePower;
        frontRight *= scalePower;
        backLeft *= scalePower;
        backRight *= scalePower;
        double[] values = {frontLeft, frontRight, backLeft, backRight};
        return values;
    }
}
