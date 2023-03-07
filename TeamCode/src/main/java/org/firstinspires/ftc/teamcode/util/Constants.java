package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {
    //Drive constants
    public static double ROTATION_CONSTANT = 0.6;//how fast will the robot turn (as a percentage)
    public static final int MECANUM_MOTOR_NUMBER = 4; //unused, was intended for ftclib
    public static final int MECANUM_FRONT_LEFT_MOTOR = 0; //the following four variables are for arrays
    public static final int MECANUM_FRONT_RIGHT_MOTOR = 1;
    public static final int MECANUM_BACK_LEFT_MOTOR = 2;
    public static final int MECANUM_BACK_RIGHT_MOTOR = 3;
    public static double DRIVE_POWER_MODIFIER = 0.8; //how fast will the robot drive (as a percentage)
    public static double SLOW_DRIVE_MODIFIER = 0.3; //a requested slower speed
    //Slide constants
    public static double MOTOR_SLIDE_POWER = 0.95; //how fast will the slide move (as a percentage)
    public static final double MOTOR_SLIDE_RESET_POWER = -0.3; //how fast slide moves when resetting (as a percentage) KEEP THIS VALUE NEGATIVE
    public static final int GROUND_POSITION = 61;//folowing variables are encoder tick values
    public static final int LOW_POSITION = 1900; //low junction
    public static final int MEDIUM_POSITION = 3000; //medium junction
    public static final int HIGH_POSITION = 4300; //high junction
    public static final int CONE_ONE = 225; //conestack 1 225
    public static final int CONE_TWO = 348; //coestack 2 348
    public static final int CONE_THREE = 492; //conestack 3 492
    public static final int CONE_FOUR = 653; //conestack 4 653
    public static final int RED_ZONE = 1600; //the height at which it is safe to rotate the claw, this was initially 10
    public static final int LINEAR_SLIDE_MINIMUM = 60; //lowest point for linear slide
    public static final int LINEAR_SLIDE_MAXIMUM = 4320; //highest point for linear slide
    public static final int LINEAR_SLIDE_MARGIN_ERROR = 10; //a margin of error to account for PID
    //Rotation servo
    public static final double SLIDE_SERVO_ZERO_POSITION = 0.73;
    public static final double SLIDE_SERVO_ROTATED_POSITION = 0.04; //should be 0.04 for testing its been changed
    public static final double SERVO_ROTATE_TIME = 1;
    //Claw constants
    public static final double CLAW_MIN = 0.31; //grab
    public static final double CLAW_MAX = 0.23; //release
    //Auto Constants
    public static class Auto{
        public static final double ONE_TILE_STRAFE = 1.1;
        public static final double ONE_TILE_FORWARD = 1.1;
        public static final double QUARTER_ROTATION = 1.05;
        public static final double STAY_STILL_AND_RELEASE = 2;
        public static final double ONE_SECOND = 1;
    }

}
