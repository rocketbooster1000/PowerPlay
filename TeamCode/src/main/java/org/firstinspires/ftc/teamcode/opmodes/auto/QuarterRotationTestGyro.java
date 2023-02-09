package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.AutoDriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.beta.Camera;

@Autonomous(name = "Quarter Rotation but gyro")
public class QuarterRotationTestGyro extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();
    double lastRunTime;
    boolean firstTimeStatic;
    AutoDriveTrain autoDriveTrain = new AutoDriveTrain();
    Camera camera = new Camera();
    Claw claw = new Claw();

    @Override
    public void init(){
        firstTimeStatic = true;

        autoDriveTrain.init(hardwareMap);
        telemetry.addData("Initialization ", "Complete");

    }

    @Override
    public void start(){
        autoDriveTrain.resetYaw();
        //grab
        //vision code and getting signal zone
        runtime.reset();
    }

    @Override
    public void loop(){
        if (autoDriveTrain.getHeadingDeg() < 90){
            autoDriveTrain.rotateCounterClockWise();
        } else {
            if (firstTimeStatic){
                lastRunTime = runtime.time();
                firstTimeStatic = false;
            }
            telemetry.addData("Last run time: ", lastRunTime);
        }
    }

}