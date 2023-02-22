package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.AutoDriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;

@Autonomous(name = "Strafe Right Test")
public class StrafeRightTest extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();
    double lastRunTime;
    AutoDriveTrain autoDriveTrain = new AutoDriveTrain();
    Camera camera = new Camera();
    Claw claw = new Claw();

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
        if (runtime.time() <= Constants.Auto.ONE_TILE_STRAFE){
            autoDriveTrain.strafeRight();
        } else {
            autoDriveTrain.stopDriving();
        }
        telemetry.addData("Runtime: ", runtime.time());
    }

}