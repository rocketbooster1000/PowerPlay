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
    public static int GROUND_POSITION = 60;//folowing variables are encoder tick values
    public static int LOW_POSITION = 520; //low junction
    public static int MEDIUM_POSITION = 870; //medium junction
    public static int HIGH_POSITION = 1200; //high junction
    public static int CONE_ONE = 190; //conestack 1 225
    public static int CONE_TWO = 240; //coestack 2 348
    public static int CONE_THREE = 270; //conestack 3 492
    public static int CONE_FOUR = 320; //conestack 4 653
    public static int RED_ZONE = 525; //the height at which it is safe to rotate the claw, this was initially 10
    public static int LINEAR_SLIDE_MINIMUM = 60; //lowest point for linear slide
    public static int LINEAR_SLIDE_MAXIMUM = 1220; //highest point for linear slide
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
