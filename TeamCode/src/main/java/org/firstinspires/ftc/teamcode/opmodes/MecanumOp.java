/*
* Here is the OpMode for a mecanum drive
* This contains all logic to make a field centric mecanum drive work
* As of 16 December 2022 it may be necessary to:
* a) change the direction of certain motors in the ProgrammingBoard2 class
* b) take the opposite of rotation as a workaround for potential logic issues in the MecMath class
 */


package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.mech.MecMath;
import org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.mech.ProgrammingBoard2;

@TeleOp()
public class MecanumOp extends OpMode {
    ProgrammingBoard2 board = new ProgrammingBoard2();
    double rotation;
    double drivePower;
    double angle;
    double powerModifier = 1;
    double heading;
    @Override
    public void init(){
        board.init(hardwareMap);
        telemetry.addData("Initiation", " Complete");
    }


    //remaps cords to circular instead of square, finds distance to center
    /*
    double inputMagnitude(double x, double y){
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return (Math.sqrt(x * x + y * y));
        //replaced by the MechMath static methods
    }


    //takes the angle

    double inputAngle(double x, double y){
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return (Math.toDegrees(Math.atan(y / x)));
        //replaced by the MechMath static methods
    }
    */

    @Override
    public void loop(){
        //finds the inputs from gamepad
        heading = board.getHeadingDeg();
        rotation = powerModifier * gamepad1.right_stick_x;
        drivePower = powerModifier * MecMath.inputMagnitude(gamepad1.left_stick_x, gamepad1.left_stick_y);
        angle = MecMath.inputAngle(gamepad1.left_stick_x, gamepad1.left_stick_y) + heading;
        telemetry.addData("Heading: ", heading);

        board.setMecanumPower(rotation, drivePower, angle);
    }
}
