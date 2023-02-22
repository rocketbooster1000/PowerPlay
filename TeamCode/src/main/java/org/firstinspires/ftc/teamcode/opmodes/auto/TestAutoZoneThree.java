package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.SignalZone;
import org.firstinspires.ftc.teamcode.mechanisms.AutoDriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;

@Autonomous()
public class TestAutoZoneThree extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();

    AutoDriveTrain autoDriveTrain = new AutoDriveTrain();
    Camera camera = new Camera();
    Claw claw = new Claw();

    double lastRuntTime;

    SignalZone signalZone;

    @Override
    public void init(){
        autoDriveTrain.init(hardwareMap);
        telemetry.addData("Initialization ", "Complete");
        lastRuntTime = 0;
    }

    @Override
    public void start(){
        signalZone = SignalZone.ZONE_THREE;
        //grab
        //vision code and getting signal zone
        runtime.reset();
    }

    @Override
    public void loop(){
        if (runtime.time() <= Constants.Auto.QUARTER_ROTATION){
            autoDriveTrain.rotateCounterClockWise();
        } else if (runtime.time() <= (Constants.Auto.QUARTER_ROTATION + Constants.Auto.ONE_TILE_FORWARD)){
            autoDriveTrain.driveForward();
        } else {
            switch (signalZone){
                case ZONE_ONE:
                    if (runtime.time() <= (Constants.Auto.QUARTER_ROTATION + Constants.Auto.ONE_TILE_FORWARD + Constants.Auto.ONE_TILE_STRAFE)){
                        autoDriveTrain.strafeLeft();
                    } else {
                        autoDriveTrain.stopDriving();
                    }
                    break;
                case ZONE_TWO:
                    autoDriveTrain.stopDriving();
                    break;
                case ZONE_THREE:
                    if (runtime.time() <= (Constants.Auto.QUARTER_ROTATION + Constants.Auto.ONE_TILE_FORWARD + Constants.Auto.ONE_TILE_STRAFE)){
                        autoDriveTrain.strafeRight();
                    } else {
                        autoDriveTrain.stopDriving();
                    }

            }
        }
        telemetry.addData("Runtime: ", runtime.time());
    }

}