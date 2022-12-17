/*
* This class contains the mechanism functionality for a field centric mecanum drive
* This class is used in the MecanumOp class
* Naming may be changed later
*/
package org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.mech;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class ProgrammingBoard2 {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    private IMU imu;
    private YawPitchRollAngles mecanumOrientation; //new object/class member to retrieve heading
    //Parameters for an orthagonal expansion hub mounting
    IMU.Parameters mecanumParameters = new IMU.Parameters(
            new RevHubOrientationOnRobot(
                    RevHubOrientationOnRobot.LogoFacingDirection.UP,
                    RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
            )
    );

    public void init(HardwareMap hwMap){
        frontLeftMotor = hwMap.get(DcMotor.class, "Front_Left");
        frontRightMotor = hwMap.get(DcMotor.class, "Front_Right");
        backLeftMotor = hwMap.get(DcMotor.class, "Back_Left");
        backRightMotor = hwMap.get(DcMotor.class, "Back_Right");
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

    public double getHeadingDeg(){
        return mecanumOrientation.getYaw(AngleUnit.DEGREES);
    }
}
