/*
* This package is meant to be modified and changed to test specific components such as servos, motors, sensors, etc.
* Not to be used as robot code
 */

package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp()
public class HardwareTest extends OpMode{
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor slideMotor;
    Servo clawServo;
    Servo slideServo;
    @Override
    public void init(){
        frontLeft = hardwareMap.get(DcMotor.class, "Front_Left");
        frontRight = hardwareMap.get(DcMotor.class, "Front_Right");
        backLeft = hardwareMap.get(DcMotor.class, "Back_Left");
        backRight = hardwareMap.get(DcMotor.class, "Back_Right");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        slideMotor = hardwareMap.get(DcMotor.class, "Slide_Motor");
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        clawServo = hardwareMap.get(Servo.class, "Claw_Servo");
        slideServo = hardwareMap.get(Servo.class, "Slide_Servo");


    }
    @Override
    public void loop(){
        frontLeft.setPower(gamepad1.left_trigger);
        frontRight.setPower(gamepad1.right_trigger);
        backLeft.setPower(gamepad1.left_stick_y);
        backRight.setPower(gamepad1.right_stick_y);
        if (gamepad1.dpad_up){
            slideMotor.setPower(0.1);
        }
        if (gamepad1.dpad_down){
            slideMotor.setPower(-0.1);
        }

    }
}
