package org.firstinspires.ftc.teamcode.mechanisms;

import org.firstinspires.ftc.teamcode.Constants;

public class AutoDriveTrain extends DriveTrain{

    public void rotateClockWise(){
        driveAuto(
                0,
                0,
                -1,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void rotateCounterClockWise(){
        driveAuto(
                0,
                0,
                1,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void strafeLeft(){
        driveAuto(
                1,
                -90,
                0,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void strafeRight(){
        driveAuto(
                1,
                90,
                0,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void driveForward(){
        driveAuto(
                1,
                180,
                0,
                0,
                Constants.DRIVE_POWER_MODIFIER
        );
    }

    public void stopDriving(){
        driveAuto(
                0,
                0,
                0,
                0,
                0
        );
    }
}
