package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.SignalZone;
import org.firstinspires.ftc.teamcode.mechanisms.AutoDriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;

@Autonomous()
public class TestAuto extends LinearOpMode{
    AutoDriveTrain autoDriveTrain;
    Claw claw;
    SignalZone signalZone;

    @Override
    public void runOpMode() throws InterruptedException{
        claw = new Claw();
        autoDriveTrain = new AutoDriveTrain();
        signalZone = SignalZone.ZONE_ONE;


        autoDriveTrain.init(hardwareMap);
        claw.init(hardwareMap);

        claw.grab();
        telemetry.addData("Initialization", "complete");


        waitForStart();
        resetRuntime();
        telemetry.update();


        /*while (getRuntime() < Constants.Auto.QUARTER_ROTATION){
            autoDriveTrain.rotateClockWise();
        }
        telemetry.addData("Status: ", "Rotated");

        claw.release();
        telemetry.addData("Status: ", "Released cone");

        resetRuntime();*/

        while (getRuntime() < Constants.Auto.ONE_TILE_FORWARD){
            autoDriveTrain.driveForward();
        }
        resetRuntime();

        if (signalZone == SignalZone.ZONE_ONE) {
            while (getRuntime() < Constants.Auto.ONE_TILE_STRAFE) {
                autoDriveTrain.strafeLeft();
            }
        }




    }
}
