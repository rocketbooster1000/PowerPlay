/*
* This class contains the mechanism functionality for a field centric mecanum drive
* This class is used in the MecanumOp class
* Naming may be changed later
*/
package org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.mech;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Constants;

public class ProgrammingBoard2 {
    private DcMotorEx frontLeftMotor;
    private DcMotorEx frontRightMotor;
    private DcMotorEx backLeftMotor;
    private DcMotorEx backRightMotor;

    private IMU imu;
    private YawPitchRollAngles mecanumOrientation; //new object/class member to retrieve heading
    //Parameters for an orthogonal expansion hub mounting
    private IMU.Parameters mecanumParameters = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            )
    );

    public void init(HardwareMap hwMap){
        frontLeftMotor = hwMap.get(DcMotorEx.class, "Front_Left");
        frontRightMotor = hwMap.get(DcMotorEx.class, "Front_Right");
        backLeftMotor = hwMap.get(DcMotorEx.class, "Back_Left");
        backRightMotor = hwMap.get(DcMotorEx.class, "Back_Right");
        frontLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        imu = hwMap.get(IMU.class, "imu");
        imu.initialize(mecanumParameters);
        mecanumOrientation = imu.getRobotYawPitchRollAngles(); //setting up the object/class member
    }

    public void allMotorSpeeds(double frontLeft, double frontRight, double backLeft, double backRight){
        frontLeftMotor.setPower(frontLeft);
        frontRightMotor.setPower(frontRight);
        backLeftMotor.setPower(backLeft);
        backRightMotor.setPower(backRight);
    }

    public void setMecanumPower(double rotation, double strafe, double forwardPower, double heading, double scalar){
        double [] motorArraySpeeds = Constants.returnMotorValues(rotation, strafe, forwardPower, heading, scalar);

        frontLeftMotor.setPower(motorArraySpeeds[Constants.MECANUM_FRONT_LEFT_MOTOR]);
        frontRightMotor.setPower(motorArraySpeeds[Constants.MECANUM_FRONT_RIGHT_MOTOR]);
        backLeftMotor.setPower(motorArraySpeeds[Constants.MECANUM_BACK_LEFT_MOTOR]);
        backRightMotor.setPower(motorArraySpeeds[Constants.MECANUM_BACK_RIGHT_MOTOR]);
    }

    public double getHeadingDeg(){
        return mecanumOrientation.getYaw(AngleUnit.DEGREES);
    }

    public void resetYaw(){
        imu.resetYaw();
    }
}
