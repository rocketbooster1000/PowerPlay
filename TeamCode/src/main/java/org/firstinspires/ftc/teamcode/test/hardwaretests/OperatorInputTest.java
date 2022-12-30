package org.firstinspires.ftc.teamcode.test.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "OI Test")
public class OperatorInputTest extends OpMode {

    
    @Override
    public void init(){
        telemetry.addData("Initiation: ", "Initiated");

    }
    public void loop(){
        telemetry.addData("Left stick x: ", gamepad1.left_stick_x);
        telemetry.addData("left stick y: ", gamepad1.left_stick_y);
        telemetry.addData("D pad up: ", gamepad1.dpad_up);

    }
}
