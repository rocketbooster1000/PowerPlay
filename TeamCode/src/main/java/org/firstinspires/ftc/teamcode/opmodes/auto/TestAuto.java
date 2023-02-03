package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.SignalZone;
import org.firstinspires.ftc.teamcode.mechanisms.AutoDriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;

@Autonomous()
public class TestAuto extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();
    double lastRunTime;
    AutoDriveTrain autoDriveTrain = new AutoDriveTrain();

    @Override
    public void init(){
        autoDriveTrain.init(hardwareMap);
        telemetry.addData("Initialization ", "Complete");

    }

    @Override
    public void start(){
        runtime.reset();
    }

    @Override
    public void loop(){
        if (runtime.time() <= Constants.Auto.ONE_TILE_FORWARD){
            autoDriveTrain.driveForward();
        } else {
            autoDriveTrain.stopDriving();
        }
        telemetry.addData("Runtime: ", runtime.time());
    }

}