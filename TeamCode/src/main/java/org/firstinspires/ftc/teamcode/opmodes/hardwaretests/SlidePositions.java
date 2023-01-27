package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;

@TeleOp(group = "Hardware tests", name = "Slide")
public class SlidePositions extends OpMode{
    DcMotorEx slideMotor;
    double modifier;
    double power;
    boolean xAlreadyPressed;
    @Override
    public void init(){
        slideMotor = hardwareMap.get(DcMotorEx.class, "Slide_Motor");
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        modifier = 0.1;
        xAlreadyPressed = false;
    }

    @Override
    public void loop(){
        power = gamepad1.right_trigger - gamepad1.left_trigger;
        power *= modifier;
        telemetry.addData("Slide Power: ", power);
        slideMotor.setPower(power);
        telemetry.addData("Position in ticks: ", slideMotor.getCurrentPosition());
        if (gamepad1.x && !xAlreadyPressed){

        }
    }
}