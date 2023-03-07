package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.AutoDriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;

@Autonomous(name = "show the field inspection guy our robot moves in auto")
public class ForwardTest extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();
    double lastRunTime;
    AutoDriveTrain autoDriveTrain = new AutoDriveTrain();
    Camera camera = new Camera();
    Claw claw = new Claw();
    Slide slide = new Slide();

    @Override
    public void init(){
        autoDriveTrain.init(hardwareMap);
        claw.init(hardwareMap);
        slide.init(hardwareMap);
        claw.grab();
    }

    @Override
    public void init_loop(){
        claw.grab();
    }

    @Override
    public void start(){
        claw.grab();
        //grab
        //vision code and getting signal zone
        slide.setSlidePosition(500);
        runtime.reset();
    }

    @Override
    public void loop(){

        if (runtime.time() <= Constants.Auto.ONE_TILE_FORWARD){
            autoDriveTrain.driveForward();
        } else {
            autoDriveTrain.stopDriving();
            slide.setSlidePosition(0);
            telemetry.addLine("Stop and resetting slide");
        }
        claw.grab();
        telemetry.addData("Runtime: ", runtime.time());
    }

}