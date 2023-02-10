package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "runtime test")
public class RunTimeTest extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    double lastRunTIme;
    boolean checkRunTimeFirstTime;
    boolean xALreadyPressed;
    boolean cycleRequested;
    boolean weHaveRotated;
    @Override
    public void init(){
        lastRunTIme = 0;
        checkRunTimeFirstTime = true;
        xALreadyPressed = false;
        cycleRequested = false;
        weHaveRotated = false;
    }

    @Override
    public void start(){
        runtime.reset();
    }

    @Override
    public void loop(){
        if (gamepad1.x && !xALreadyPressed){
            cycleRequested = false;
        }

        if (cycleRequested){
            if (checkRunTimeFirstTime){
                lastRunTIme = runtime.time();
                checkRunTimeFirstTime = false;
            }
            if ((runtime.time() - lastRunTIme) >= 1){
                weHaveRotated = true;
                checkRunTimeFirstTime = true;
                cycleRequested = false;
            } else {
                weHaveRotated = false;
            }
        }

        telemetry.addData("We have rotated: ", weHaveRotated);
        telemetry.addData("Cycle requested: ", cycleRequested);
        telemetry.addData("Time since request: ", (runtime.time() - lastRunTIme));
    }
}
