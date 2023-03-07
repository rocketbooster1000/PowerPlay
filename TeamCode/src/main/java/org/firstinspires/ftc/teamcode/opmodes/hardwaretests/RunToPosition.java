package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Slide;

@TeleOp(group = "Hardware tests")
@Disabled
public class RunToPosition extends OpMode {
    Slide slide = new Slide();
    boolean xAlreadyPressed;
    boolean yAlreadyPressed;
    public void init(){
        slide.init(hardwareMap);
        xAlreadyPressed = false;
        yAlreadyPressed = false;
    }
    public void loop(){
        if (gamepad1.x && !xAlreadyPressed){
            slide.setSlidePosition(3000);
        }
        xAlreadyPressed = gamepad1.x;
        if (gamepad1.y && !yAlreadyPressed){
            slide.setSlidePosition(2000);
        }
        yAlreadyPressed = gamepad1.y;
        telemetry.addData("Target: ", slide.getTargetPos());
        telemetry.addData("Current: ", slide.getSlidePosition());
    }
}
