package org.firstinspires.ftc.teamcode;

public class Constants {
    public static final double ROTATION_CONSTANT = 0.29;
    public static final int MECANUM_MOTOR_NUMBER = 4;
    public static final int MECANUM_FRONT_LEFT_MOTOR = 0;
    public static final int MECANUM_FRONT_RIGHT_MOTOR = 1;
    public static final int MECANUM_BACK_LEFT_MOTOR = 2;
    public static final int MECANUM_BACK_RIGHT_MOTOR = 3;
    public static final double DRIVE_POWER_MODIFIER = 1;
    public static final double MOTOR_SLIDE_POWER = 0.25;
    public static final int GROUND_POSITION = 0;
    public static final int LOW_POSITION = 5;
    public static final int MEDIUM_POSITION = 6;
    public static final int HIGH_POSITION = 7;
    public static final int CONE_ONE = 1;
    public static final int CONE_TWO = 2;
    public static final int CONE_THREE = 3;
    public static final int CONE_FOUR = 4;
    public static final double SLIDE_SERVO_ZERO_POSITION = 0;
    public static final double SLIDE_SERVO_ROTATED_POSITION = 1;
    public static final int LINEAR_SLIDE_MINIMUM = 0;
    public static final int LINEAR_SLIDE_MAXIMUM = 3;
    public static final double CLAW_MIN = 0;
    public static final double CLAW_MAX = 1;
    public static final int RED_ZONE = 10;


    public static double[] mapJoystick(double x, double y){
        double m_x = x * Math.sqrt(1 - y * y / 2);
        double m_y = y * Math.sqrt(1 - x * x / 2);
        double[] converted = {m_x, m_y};
        return converted;
    }
    public static double inputMagnitude(double x, double y){
        double[] cords = mapJoystick(x, y);
        return (Math.hypot(cords[0], cords[1]));
    }

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

    public static double[] returnMecanumValues(double rotation, double strafe, double forward, double heading, double scalePower){
        double angle = inputAngle(strafe, forward) + 45 + heading;
        double power = inputMagnitude(strafe, forward);
        double xPower = power * Math.cos(Math.toRadians(angle));
        double yPower = power * Math.sin(Math.toRadians(angle));
        double frontLeft = xPower;
        double frontRight = yPower;
        double backLeft = yPower;
        double backRight = xPower;
        if (frontLeft > frontRight){
            frontRight -= (rotation * ROTATION_CONSTANT);
            backLeft += (rotation * ROTATION_CONSTANT);
        } else {
            frontLeft += (rotation * ROTATION_CONSTANT);
            backRight -= (rotation * ROTATION_CONSTANT);
        }
        frontLeft *= scalePower;
        frontRight *= scalePower;
        backLeft *= scalePower;
        backRight *= scalePower;
        double[] values = {frontLeft, frontRight, backLeft, backRight};
        return values;
    }
}
