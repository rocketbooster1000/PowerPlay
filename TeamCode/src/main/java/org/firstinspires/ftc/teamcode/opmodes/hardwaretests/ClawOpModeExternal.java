package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Claw;

@TeleOp(group = "Hardware tests")
public class ClawOpModeExternal extends OpMode{
    Claw claw = new Claw();

    boolean aAlreadyPressed;
    boolean wantToGrab;

    @Override
    public void init(){
        claw.init(hardwareMap);
        aAlreadyPressed = false;
        wantToGrab = false;
        claw.grab();
    }

    @Override
    public void loop(){
        if (gamepad1.a && !aAlreadyPressed){
            wantToGrab = !wantToGrab;
        }
        aAlreadyPressed = gamepad1.a;
        if (wantToGrab){
            claw.release();
            telemetry.addData("Claw: ", "released");
        } else {
            claw.grab();
            telemetry.addData("Claw: ", "grabbed");
        }
        telemetry.addData("Claw pos: ", claw.getPosition());
    }
}
