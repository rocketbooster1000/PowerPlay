/*
* Here is the OpMode for a mecanum drive
* This contains all logic to make a robot centric mecanum drive work
 */

package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;

//This makes a new drivetrain

@TeleOp(name = "Mecanum")
public class MecanumOp extends OpMode {
    DriveTrain board = new DriveTrain();
    @Override
    public void init(){
        board.init(hardwareMap);
        telemetry.addData("Initiation", " Complete");
    }

    @Override
    public void start(){
        board.resetYaw();
    }

    //This is code for driving the robot
    @Override
    public void loop(){


        board.drive(
                -gamepad1.right_stick_x,
                -gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                board.getHeadingDeg(),
                Constants.DRIVE_POWER_MODIFIER
        );
        telemetry.addData("Theoretical Heading: ", board.getHeadingDeg());


    }
}
