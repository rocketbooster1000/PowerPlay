/*
* This package is meant to be modified and changed to test specific components such as servos, motors, sensors, etc.
* Not to be used as robot code
 */

package org.firstinspires.ftc.teamcode.mechanisms.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp()
public class HardwareTest extends OpMode{
    private HardwareMap hwMap;
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    @Override
    public void init(){
        frontLeft = hwMap.get(DcMotor.class, "Front_Left");
        frontRight = hwMap.get(DcMotor.class, "Front_Right");
        backLeft = hwMap.get(DcMotor.class, "Back_Left");
        backRight = hwMap.get(DcMotor.class, "Back_Right");
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
    @Override
    public void loop(){
        frontLeft.setPower(gamepad1.left_trigger);
        frontRight.setPower(gamepad1.right_trigger);
        backLeft.setPower(gamepad1.left_stick_y);
        backRight.setPower(gamepad1.right_stick_y);
    }
}
