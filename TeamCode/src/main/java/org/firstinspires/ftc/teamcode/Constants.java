package org.firstinspires.ftc.teamcode;

public class Constants {
    public static final double ROTATION_CONSTANT = 0.29;
    public static final int MECANUM_MOTOR_NUMBER = 4;
    public static final int MECANUM_FRONT_LEFT_MOTOR = 0;
    public static final int MECANUM_FRONT_RIGHT_MOTOR = 1;
    public static final int MECANUM_BACK_LEFT_MOTOR = 2;
    public static final int MECANUM_BACK_RIGHT_MOTOR = 3;
    public static final double DRIVE_POWER_MODIFIER = 1;
    public Constants(){

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

    public static double[] returnMotorValues(double rotation, double strafe, double forwardPower, double heading, double scalar){
        double angle = inputAngle(strafe, forwardPower);
        angle += heading;
        angle += 45;

        double power = inputMagnitude(strafe, forwardPower);

        double yPower = power * Math.sin(Math.toRadians(angle));
        double xPower = power * Math.sin(Math.toRadians(angle));
        double frontLeftPower = xPower;
        double backRightPower = xPower;
        double frontRightPower = yPower;
        double backLeftPower = yPower;
        if (Math.abs(yPower) < Math.abs(xPower)){
            frontRightPower += (rotation * ROTATION_CONSTANT);
            backLeftPower -= (rotation * ROTATION_CONSTANT);
        } else {
            frontLeftPower += (rotation * ROTATION_CONSTANT);
            backRightPower -= (rotation * ROTATION_CONSTANT);
        }
        frontLeftPower *= scalar;
        frontRightPower *= scalar;
        backLeftPower *= scalar;
        backRightPower *= scalar;
        double[] motorValues = {frontLeftPower, frontRightPower, backLeftPower, backRightPower};
        return motorValues;
    }


}
