/*
* This class is not intended to be used
* All functionality has been moved to the ProgrammingBoard2 class
* It exists here as a concept and for future reference
* -jason
 */


package org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.mech;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class IMUTest {
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
        imu = hwMap.get(IMU.class, "imu");
        imu.initialize(mecanumParameters);
        mecanumOrientation = imu.getRobotYawPitchRollAngles(); //setting up the object/class member

    }
    public double getHeadingDeg(){
        return mecanumOrientation.getYaw(AngleUnit.DEGREES);
    }

}
