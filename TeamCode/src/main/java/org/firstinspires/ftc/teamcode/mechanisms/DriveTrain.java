/*
* This class contains the mechanism functionality for a field centric mecanum drive
* This class is used in the MecanumOp class
* Naming may be changed later
*/
package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Constants;

//This makes the drivetrain class
public class DriveTrain {
    //This declares/makes the motor objects
    private DcMotorEx frontLeftMotor;
    private DcMotorEx frontRightMotor;
    private DcMotorEx backLeftMotor;
    private DcMotorEx backRightMotor;
    //This is gyro stuff or imu stuff
    private IMU imu;

    public void init(HardwareMap hwMap){
        //This sets up the motors it also tells us what the names of our motors are in a string format
        frontLeftMotor = hwMap.get(DcMotorEx.class, "Front_Left");
        frontRightMotor = hwMap.get(DcMotorEx.class, "Front_Right");
        backLeftMotor = hwMap.get(DcMotorEx.class, "Back_Left");
        backRightMotor = hwMap.get(DcMotorEx.class, "Back_Right");
        //This makes our motors run using encoders
        frontLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        //This sets the motors default direction that it spins
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //This makes sure that when the motors are given no input they will not do anything
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //This is gyro/imu stuff
        imu = hwMap.get(IMU.class, "imu");
        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                                RevHubOrientationOnRobot.UsbFacingDirection.UP
                        )
                )
        );
    }
    /* This is just a different way of setting power to the motors
    public void allMotorSpeeds(double frontLeft, double frontRight, double backLeft, double backRight){
        frontLeftMotor.setPower(frontLeft);
        frontRightMotor.setPower(frontRight);
        backLeftMotor.setPower(backLeft);
        backRightMotor.setPower(backRight);
    } */

    //This tells the motors how fast to go
    public void drive(double rotation, double strafe, double forwardPower, double heading, double scalar){
        double [] motorArraySpeeds = Constants.returnMecanumValues(rotation, strafe, forwardPower, heading, scalar);

        frontLeftMotor.setPower(motorArraySpeeds[Constants.MECANUM_FRONT_LEFT_MOTOR]);
        frontRightMotor.setPower(motorArraySpeeds[Constants.MECANUM_FRONT_RIGHT_MOTOR]);
        backLeftMotor.setPower(motorArraySpeeds[Constants.MECANUM_BACK_LEFT_MOTOR]);
        backRightMotor.setPower(motorArraySpeeds[Constants.MECANUM_BACK_RIGHT_MOTOR]);
    }
    //This gets the heading by reading the gyro
    public double getHeadingDeg(){
        YawPitchRollAngles mecanumOrientation = imu.getRobotYawPitchRollAngles();
        return mecanumOrientation.getYaw(AngleUnit.DEGREES);
    }

    public void driveAuto(double magnitude, double angle, double rotation, double heading, double scalePower){
        double[] motorArraySpeeds = Constants.returnMecanumValuesAuto(magnitude, angle, rotation, heading, scalePower);

        frontLeftMotor.setPower(motorArraySpeeds[Constants.MECANUM_FRONT_LEFT_MOTOR]);
        frontRightMotor.setPower(motorArraySpeeds[Constants.MECANUM_FRONT_RIGHT_MOTOR]);
        backLeftMotor.setPower(motorArraySpeeds[Constants.MECANUM_BACK_LEFT_MOTOR]);
        backRightMotor.setPower(motorArraySpeeds[Constants.MECANUM_BACK_RIGHT_MOTOR]);
    }
    //This resets the gyro
    public void resetYaw(){
        imu.resetYaw();
    }

}
