package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class AutoDriveTrain {
    DriveTrain driveTrain = new DriveTrain();
    public void init(HardwareMap hwMap){
        driveTrain.init(hwMap);
        driveTrain.resetYaw();
    }

    public void rotateClockWise(){
        driveTrain.driveAuto(
                0,
                0,
                1,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void strafeLeft(){
        driveTrain.driveAuto(
                1,
                90,
                0,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void strafeRight(){
        driveTrain.driveAuto(
                1,
                -90,
                0,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void driveForward(){
        driveTrain.driveAuto(
                1,
                180,
                0,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void stopDriving(){
        driveTrain.driveAuto(
                0,
                0,
                0,
                0,
                0
        );
    }
}
