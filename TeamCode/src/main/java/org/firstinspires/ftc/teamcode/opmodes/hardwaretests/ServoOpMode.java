package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(group = "Hardware tests")
public class ServoOpMode extends OpMode{
    Servo servo;
    double servoPosition;

    boolean dPadUpAlreadyPressed;
    boolean dPadDownAlreadyPressed;

    @Override
    public void init(){
        servo = hardwareMap.get(Servo.class, "Slide_Servo");
        servoPosition = 0;

        dPadUpAlreadyPressed = false;
        dPadDownAlreadyPressed = false;
    }

    @Override
    public void loop() {
        if (servoPosition >= 0 && servoPosition <= 1) {
            if (gamepad1.dpad_up && !dPadUpAlreadyPressed) {
                servoPosition += 0.025;
            }
            if (gamepad1.dpad_down && !dPadDownAlreadyPressed) {
                if (servoPosition != 0){
                    servoPosition -= 0.025;
                }
            }
        }
        if (servoPosition > 1){
            servoPosition = 1;
        }
        if (servoPosition < 0){
            servoPosition = 0;
        }
        telemetry.addData("Servo target position: ", servoPosition);
        telemetry.addData("Servo Position Absolute: ", servo.getPosition());
        servo.setPosition(servoPosition);
        dPadUpAlreadyPressed = gamepad1.dpad_up;
        dPadDownAlreadyPressed = gamepad1.dpad_down;
    }
}
