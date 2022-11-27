/*
*The "test" package is as the name suggests, a place to test code.
* This is also the place where experimental with learning new code is conducted in an effort to separate this
* from the official opmode code intended to be run by the robot.
 */


package org.firstinspires.ftc.teamcode.test;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

public class RobotLocation {
    double angle;
    double X;
    double Y;
    public RobotLocation(double angle, double X, double Y){
        this.angle = angle;
        this.X = X;
        this.Y = Y;
    }

    public double getHeadingDeg(){
        double angle = this.angle;
        while (angle > Math.PI){
            angle -= 2 * Math.PI;
        }
        while (angle < -Math.PI){
            angle += 2 * Math.PI;
        }
        return Math.toDegrees(angle);
    }

    public void changeHeadingDeg(double theta){
        this.angle += Math.toRadians(theta);
    }

    public void setHeadingDeg(double heading){
        this.angle = Math.toRadians(heading);
    }

    public void getAngle(){
        telemetry.addData("Angle (degrees)", Math.toDegrees(this.angle));
    }
    public double getX(){
        return X;
    }
    public void changeX(double delta){
        this.X += delta;
    }
    public void setX(double X){
        this.X = X;
    }
    public double getY(){
        return Y;
    }
    public void changeY(double delta){
        this.Y += delta;
    }
    public void setY(double Y){
        this.Y = Y;
    }
}
