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
import org.firstinspires.ftc.teamcode.mechanisms.beta.Camera;
import org.firstinspires.ftc.teamcode.vision.SleeveDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous()
public class TestAuto extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();

    AutoDriveTrain autoDriveTrain = new AutoDriveTrain();
    //Camera camera = new Camera();
    Claw claw = new Claw();
    SleeveDetection sleevedetection = new SleeveDetection();
    double lastRuntTime;

    SignalZone signalZone;
    private OpenCvCamera camera;

    @Override
    public void init(){
        autoDriveTrain.init(hardwareMap);
        telemetry.addData("Initialization ", "Complete");
        lastRuntTime = 0;

    }

    @Override
    public void start(){

        //grab
        //vision code and getting signal zone
        //camera.returnZoneEnumerated() = SleeveDetection.ParkingPosition.LEFT;
        //camera.startStreaming(700,700, OpenCvCameraRotation.SIDEWAYS_LEFT);
        /*switch (sleevedetection.getPositionSane()){
            case 1:
                signalZone = SignalZone.ZONE_ONE;
                break;
            case 2:
                signalZone = SignalZone.ZONE_TWO;
                break;
            case 3:
                signalZone = SignalZone.ZONE_THREE;
                break;
            case 4:
                break;
        }*/
        signalZone = SignalZone.ZONE_ONE;
        runtime.reset();

    }

    @Override
    public void loop(){
        if (runtime.time() <= Constants.Auto.QUARTER_ROTATION){
            //autoDriveTrain.rotateClockWise();
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
        telemetry.addData("Parking Zone: ", signalZone);
    }

}