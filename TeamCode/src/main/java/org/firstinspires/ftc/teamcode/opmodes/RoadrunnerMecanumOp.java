/*
* Here is the OpMode for a mecanum drive
* This contains all logic to make a robot centric mecanum drive work
 */

package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

//This makes a new drivetrain

@TeleOp(name = "Roadrunner Mecanum")
public class RoadrunnerMecanumOp extends OpMode {
    enum DriveMode{
        SELF_WRITTEN,
        ROADRUNNER
    }
    DriveTrain board = new DriveTrain();
    SampleMecanumDrive roadrunnerBoard = new SampleMecanumDrive(hardwareMap);
    boolean yAlreadyPressed;
    DriveMode driveMode;

    @Override
    public void init(){
        board.init(hardwareMap);
        yAlreadyPressed = false;
        driveMode = DriveMode.SELF_WRITTEN;
        telemetry.addData("Initiation", " Complete");
    }

    @Override
    public void start(){
        board.resetYaw();
    }

    //This is code for driving the robot
    @Override
    public void loop(){
        switch (driveMode){
            case SELF_WRITTEN:
                board.drive(
                        -gamepad1.right_stick_x,
                        -gamepad1.left_stick_x,
                        gamepad1.left_stick_y,
                        board.getHeadingDeg(),
                        Constants.DRIVE_POWER_MODIFIER
                );
                telemetry.addData("Theoretical Heading: ", board.getHeadingDeg());

                if (gamepad1.y && !yAlreadyPressed){
                    board.resetYaw();
                }
                yAlreadyPressed = gamepad1.y;

                if (gamepad1.left_bumper){
                    driveMode = DriveMode.ROADRUNNER;
                }
            case ROADRUNNER:
                roadrunnerBoard.setWeightedDrivePower(
                        new Pose2d(
                                -gamepad1.left_stick_y,
                                -gamepad1.left_stick_x,
                                -gamepad1.right_stick_x
                        )
                );

                if (gamepad1.right_bumper){
                    driveMode = DriveMode.SELF_WRITTEN;
                }

        }
        telemetry.addData("Mode: ", driveMode);
    }
}
