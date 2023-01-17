package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoOpMode extends OpMode{
    Servo servo;
    double servoPosition;

    boolean dPadUpAlreadyPressed;
    boolean dPadDownAlreadyPressed;

    @Override
    public void init(){
        servo = hardwareMap.get(Servo.class, "Claw_Servo");
        servoPosition = 0;

        dPadUpAlreadyPressed = false;
        dPadDownAlreadyPressed = false;
    }

    @Override
    public void loop(){
        if (gamepad1.dpad_up && !dPadUpAlreadyPressed){
            servoPosition += 0.1;
        }
        dPadUpAlreadyPressed = gamepad1.dpad_up;
        if (gamepad1.dpad_down && !dPadDownAlreadyPressed) {
            servoPosition -= 0.1;
        }
        dPadDownAlreadyPressed = gamepad1.dpad_down;
        telemetry.addData("Servo Position: ", servoPosition);
        servo.setPosition(servoPosition);
    }
}
