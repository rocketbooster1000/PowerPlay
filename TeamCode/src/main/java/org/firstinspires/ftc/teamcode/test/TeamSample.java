package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
@Disabled
public class TeamSample extends OpMode{
    @Override
    public void init() {
        telemetry.addData("Start-up", ": Hello!");
        telemetry.addData("This is a test", "Initiation");
        //code here will be run ONCE when the init button is pressed
    }

    @Override
    public void loop() {
        boolean gamepadPressed = (gamepad1.left_stick_x != 0) && (gamepad1.left_stick_y != 0);
        if (gamepadPressed){
            telemetry.addData("Left y: ", gamepad1.left_stick_y);
            telemetry.addData("Left x: ", gamepad1.left_stick_x);
        }
        //code here will be run repeatedly (about 50 times per second) once the play buttuon is pressed
    }
}
