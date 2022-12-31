package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard1;

@TeleOp()
@Disabled
public class OpTest extends OpMode {
    ProgrammingBoard1 board = new ProgrammingBoard1();
    boolean toggleState;
    double pressCount = 0;
    boolean dPadPressed;
    boolean previousToggleState;
    boolean variableSpeedOn;
    double motorSpeed;
    boolean bumperAlreadyPressed;
    boolean motorOn;
    @Override
    public void init(){
        board.init(hardwareMap);
    }

    double squareInputWithSign(double input){
        return ((input < 0) ? - (input * input) : (input * input));
    }
    @Override
    public void loop(){
        if (gamepad1.dpad_up && !dPadPressed){
            variableSpeedOn = !variableSpeedOn;
            telemetry.addData("Variable Speed On: ", variableSpeedOn);
        }
        if (variableSpeedOn){
            motorSpeed = (gamepad1.left_stick_y < 0.5 && gamepad1.left_stick_y > -0.5) ?
                    squareInputWithSign(gamepad1.left_stick_y) :
                    gamepad1.left_stick_y;
        } else {
            motorSpeed = 0.5;
        }
        dPadPressed = gamepad1.dpad_up;

        telemetry.addData("Touch Sensor ", ((board.touchSensorPressed()) ? "Pressed" : "Not Pressed"));
        telemetry.addData("Motor rotations", board.getMotorRevlution());
        board.ZeroBehavior(gamepad1.a, gamepad1.b);

        //The following block of code turns a bumper press into a toggle for a motor
        if (gamepad1.left_bumper && !bumperAlreadyPressed){
            motorOn = !motorOn;
            telemetry.addData("Motor on", motorOn);
            if (motorOn){
                board.setMotorSpeed(motorSpeed);
            } else {
                board.setMotorSpeed(0);
            }
        }
        bumperAlreadyPressed = gamepad1.left_bumper;
        /*
         * The code bellow is commented out on purpose
         * It was a less efficient way to perform the same tasks
         * However it was written as a fun challenge to the programmer
         */
        /*
        if (gamepad1.left_bumper) { //only execute code if gamepad is pressed
            pressCount++;
            if (previousToggleState != toggleState){
                pressCount++;
            }
            if (pressCount % 2 == 0) { //if even means motor off
                board.setMotorSpeed(0);
                toggleState = false;
            } else { //if odd means motor on
                board.setMotorSpeed(motorSpeed);
                toggleState = true;
            }
            pressCount++;
            previousToggleState = toggleState;
        } else {
            previousToggleState = !toggleState;
        }
        */

    }
}
//comment