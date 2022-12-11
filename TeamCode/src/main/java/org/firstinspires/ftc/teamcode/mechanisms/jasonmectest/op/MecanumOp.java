package org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.op;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.mech.ProgrammingBoard2;
import org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.op.MecMath;

@TeleOp()
public class MecanumOp extends OpMode {
    ProgrammingBoard2 board = new ProgrammingBoard2();
    MecMath mec = new MecMath();
    double rotation;
    double drivePower;
    double angle;
    double powerModifier = 1;
    @Override
    public void init(){
        board.init(hardwareMap);
    }
    double inputMagnitude(double x, double y){
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return (Math.sqrt(x * x + y * y));
    }

    double inputAngle(double x, double y){
        x = x * Math.sqrt(1 - y * y / 2);
        y = y * Math.sqrt(1 - x * x / 2);
        return (Math.toDegrees(Math.atan(y / x)));
    }
    @Override
    public void loop(){
        rotation = powerModifier * gamepad1.right_stick_x;
        drivePower = powerModifier * inputMagnitude(gamepad1.left_stick_x, gamepad1.left_stick_y);
        angle = inputAngle(gamepad1.left_stick_x, gamepad1.left_stick_y);
        mec.motorValues(rotation, drivePower, angle);
        board.allMotorSpeeds(mec.frontLeft, mec.frontRight, mec.backLeft, mec.backRight);
    }
}
