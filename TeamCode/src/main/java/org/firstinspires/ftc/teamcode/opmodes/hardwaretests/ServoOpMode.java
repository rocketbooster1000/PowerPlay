package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "Hardware tests")
public class ServoOpMode extends OpMode{
    Servo servo;
    double servoPosition;
    double minPosition;
    double increment;

    boolean dPadUpAlreadyPressed;
    boolean dPadDownAlreadyPressed;
    boolean firstIteration;

    @Override
    public void init(){
        minPosition = 0;
        increment = 0.01;
        servo = hardwareMap.get(Servo.class, "Slide_Servo");
        servoPosition = minPosition;

        dPadUpAlreadyPressed = false;
        dPadDownAlreadyPressed = false;
        firstIteration = true;
    }

    @Override
    public void loop() {




        if (servoPosition >= minPosition && servoPosition <= 1) {
            if (gamepad1.dpad_up && !dPadUpAlreadyPressed) {
                servoPosition += increment;
            }
            if (gamepad1.dpad_down && !dPadDownAlreadyPressed) {
                if (servoPosition != minPosition){
                    servoPosition -= increment;
                }
            }
        }
        if (servoPosition > 1){
            servoPosition = 1;
        }
        if (servoPosition < minPosition){
            servoPosition = minPosition;
        }
        telemetry.addData("Ella is super cool also, Servo target position: ", servoPosition);
        telemetry.addData("Servo Position Absolute: ", servo.getPosition());
        if (firstIteration){
            if (gamepad1.dpad_up){
                firstIteration = false;
            }
        } else {
            servo.setPosition(servoPosition);
        }
        dPadUpAlreadyPressed = gamepad1.dpad_up;
        dPadDownAlreadyPressed = gamepad1.dpad_down;
    }
}
