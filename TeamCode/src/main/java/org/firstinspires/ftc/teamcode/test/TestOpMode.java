package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
@Disabled
public class TestOpMode extends OpMode{
    RobotLocation robotLocation = new RobotLocation(0, 0, 0);
    double inverseTurn;

    {
        inverseTurn = -gamepad1.left_stick_x;
    }

    @Override
    public void init(){
        robotLocation.getHeadingDeg();
    }
    @Override
    public void loop(){
        robotLocation.changeHeadingDeg(inverseTurn);
        if (gamepad1.a){
            robotLocation.changeHeadingDeg(1);
        } else if (gamepad1.b){
            robotLocation.changeHeadingDeg(-1);
        }
        if (gamepad1.dpad_left){
            robotLocation.changeX(0.1);
        } else if (gamepad1.dpad_right){
            robotLocation.changeX(-0.1);
        }
        robotLocation.getAngle();
    }
    public void stop(){
        telemetry.addData("Angle", robotLocation.angle);
    }
}
