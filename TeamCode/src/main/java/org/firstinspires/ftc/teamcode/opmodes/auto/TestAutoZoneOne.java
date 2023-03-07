package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.util.SignalZone;
import org.firstinspires.ftc.teamcode.mechanisms.AutoDriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;

@Autonomous()
@Disabled
public class TestAutoZoneOne extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();

    AutoDriveTrain autoDriveTrain = new AutoDriveTrain();
    Camera camera = new Camera();
    Claw claw = new Claw();
    Slide slide = new Slide();

    double lastRuntTime;

    SignalZone signalZone;

    @Override
    public void init(){
        claw.init(hardwareMap);
        slide.init(hardwareMap);
        autoDriveTrain.init(hardwareMap);
        claw.grab();
        telemetry.addData("Initialization ", "Complete");
        lastRuntTime = 0;
    }

    @Override
    public void init_loop(){
        claw.grab();
    }

    @Override
    public void start(){
        claw.grab();
        slide.setSlidePosition(500);
        signalZone = SignalZone.ZONE_ONE;
        //grab
        //vision code and getting signal zone
        runtime.reset();
    }

    @Override
    public void loop(){
        claw.grab();
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
                        slide.setSlidePosition(9);
                    }
                    break;
                case ZONE_TWO:
                    autoDriveTrain.stopDriving();
                    slide.setSlidePosition(9);
                    break;
                case ZONE_THREE:
                    if (runtime.time() <= (Constants.Auto.QUARTER_ROTATION + Constants.Auto.ONE_TILE_FORWARD + Constants.Auto.ONE_TILE_STRAFE)){
                        autoDriveTrain.strafeRight();
                    } else {
                        autoDriveTrain.stopDriving();
                        slide.setSlidePosition(9);
                    }

            }
        }
        telemetry.addData("Runtime: ", runtime.time());
    }

}