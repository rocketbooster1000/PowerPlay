package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;

@TeleOp(name = "Get slide encoder positions")
public class SlidePositions extends OpMode{
    Slide slide = new Slide();
    Claw claw = new Claw();
    @Override
    public void init(){
        slide.init(hardwareMap);
        claw.init(hardwareMap);
    }

    @Override
    public void loop(){
        telemetry.addData("Slide servo encoder tick: ", slide.getSlidePosition());
        telemetry.addData("Claw Left: ", claw.getClawPositions()[0]);
        telemetry.addData("Claw Right: ", claw.getClawPositions()[1]);
    }
}
