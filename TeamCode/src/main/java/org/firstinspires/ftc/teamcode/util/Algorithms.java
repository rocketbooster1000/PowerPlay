package org.firstinspires.ftc.teamcode.util;

import static org.firstinspires.ftc.teamcode.util.Constants.ROTATION_CONSTANT;

public class Algorithms {
    //remaps joystick from grid to circle
    static double[] mapJoystick(double x, double y){
        double m_x = x * Math.sqrt(1 - y * y / 2);
        double m_y = y * Math.sqrt(1 - x * x / 2);
        double[] converted = {m_x, m_y};
        return converted;
    }
    //finds r (for r, theta aqquired from the joystick)
    static double inputMagnitude(double x, double y){
        double[] cords = mapJoystick(x, y);
        return (Math.hypot(cords[0], cords[1]));
    }

    //finds theta (for r, theta aquirred from the joystick)
    //essentailly a custom Math.atan2 implementation
    static double inputAngle(double x, double y){
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

    //calculates power for motors (teleop)
    public static double[] returnMecanumValues(double rotation, double strafe, double forward, double heading, double scalePower){
        double angle = inputAngle(strafe, forward) + 45 - heading;
        double power = inputMagnitude(strafe, forward);
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));
        double maxTrig = Math.max(Math.abs(sin), Math.abs(Math.cos(angle)));
        double xPower = power * cos;
        double yPower = power * sin;

        if (maxTrig != 0){
            xPower /= maxTrig;
            yPower /= maxTrig;
        }

        double frontLeft = xPower;
        double frontRight = yPower;
        double backLeft = yPower;
        double backRight = xPower;
        frontRight -= (rotation * ROTATION_CONSTANT);
        backLeft += (rotation * ROTATION_CONSTANT);
        frontLeft += (rotation * ROTATION_CONSTANT);
        backRight -= (rotation * ROTATION_CONSTANT);
        double maxPower = Math.max(Math.max(Math.abs(frontLeft), Math.abs(frontRight)), Math.max(Math.abs(backLeft), Math.abs(backRight)));

        if (maxPower > 1){
            frontLeft /= maxPower;
            frontRight /= maxPower;
            backLeft /= maxPower;
            backRight /= maxPower;
        }

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
