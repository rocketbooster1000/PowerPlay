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
        //grab
        //vision code and getting signal zone
        runtime.reset();
    }

    @Override
    public void loop(){
        if (runtime.time() <= Constants.Auto.ONE_TILE_FORWARD){
            autoDriveTrain.driveForward();
        } else if (runtime.time() > Constants.Auto.ONE_TILE_FORWARD && runtime.time() < (2 * Constants.Auto.ONE_TILE_FORWARD)){
            autoDriveTrain.driveForward();
        } else if (runtime.time() > (2 * Constants.Auto.ONE_TILE_FORWARD) && runtime.time() < (Constants.Auto.STAY_STILL_AND_RELEASE + 2 * Constants.Auto.ONE_TILE_FORWARD)){
            autoDriveTrain.stopDriving();
            //release
        } else {
            //go to signal zone
        }
        telemetry.addData("Runtime: ", runtime.time());
    }

}