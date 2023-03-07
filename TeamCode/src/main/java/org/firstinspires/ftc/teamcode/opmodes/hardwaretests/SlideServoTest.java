package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Slide;

@TeleOp(group = "Hardware tests")
@Disabled
public class SlideServoTest extends OpMode {
    Slide slide = new Slide();
    boolean bAlreadyPressed;
    @Override
    public void init(){
        slide.init(hardwareMap);
        bAlreadyPressed = false;
    }

    @Override
    public void loop(){
        if (gamepad1.b && bAlreadyPressed){
            slide.rotateServo();
        }
        bAlreadyPressed = gamepad1.b;
        telemetry.addData("B already?: ", bAlreadyPressed);
    }
}
