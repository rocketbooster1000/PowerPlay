package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.SleeveDetection;

@Config
@Autonomous()
@Disabled
public class AutoCycle extends OpMode {

    enum States{
        HEADING_TO_CONES,
        HEADING_TO_JUNCTION,
        PARKING
    }

    SampleMecanumDrive drive = null;
    Slide slide = new Slide();
    Claw claw = new Claw();
    Camera camera = new Camera();

    TrajectorySequence startTraj;
    TrajectorySequence junctionToStack;
    TrajectorySequence stackToJunction;
    TrajectorySequence left;
    TrajectorySequence center;
    TrajectorySequence right;

    States autoState;
    SleeveDetection.ParkingPosition signalZone;

    static int[] coneStackEncoderPositions;
    int coneIndex;

    public static double timeToCycle = 25;

    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init(){
        drive = new SampleMecanumDrive(hardwareMap);
        slide.init(hardwareMap);
        claw.init(hardwareMap);
        camera.init(hardwareMap);
        autoState = States.HEADING_TO_JUNCTION;
        coneStackEncoderPositions = new int[]{Constants.CONE_FOUR, Constants.CONE_THREE, Constants.CONE_TWO, Constants.CONE_ONE, Constants.GROUND_POSITION};
        coneIndex = 0;
    }

    @Override
    public void init_loop(){
        claw.grab();
        signalZone = camera.returnZoneEnumerated();
        telemetry.addData("Zone: ", signalZone);
    }

    @Override
    public void start(){
        startTraj = drive.trajectorySequenceBuilder(new Pose2d(-36.00, 60.00, Math.toRadians(180.00)))
                .lineTo(new Vector2d(-36.00, -0.00))
                .lineTo(new Vector2d(-30.00, -0.00))
                .build();
        drive.setPoseEstimate(startTraj.start());
        junctionToStack = drive.trajectorySequenceBuilder(new Pose2d(-30.00, -0.00, Math.toRadians(180.00)))
                .splineTo(new Vector2d(-38.94, 11.25), Math.toRadians(159.62))
                .splineTo(new Vector2d(-60.00, 12.00), Math.toRadians(180.00))
                .build();

        stackToJunction = drive.trajectorySequenceBuilder(junctionToStack.end())
                .splineTo(new Vector2d(-38.94, 12.75), Math.toRadians(200.38))
                .splineTo(new Vector2d(-30.00, -0.00), Math.toRadians(180.00))
                .build();

        drive.followTrajectorySequenceAsync(startTraj);
        slide.setSlidePosition(Constants.HIGH_POSITION);
        runtime.reset();
    }

    @Override
    public void loop(){
        switch (autoState){
            case HEADING_TO_JUNCTION:
                claw.grab();
                if (!drive.isBusy()){
                    claw.release();
                    slide.rotateServo();
                    if (runtime.time() > timeToCycle){
                        slide.setSlidePosition(0);
                        if (signalZone == SleeveDetection.ParkingPosition.LEFT){
                            drive.followTrajectorySequenceAsync(left);
                        } else if (signalZone == SleeveDetection.ParkingPosition.CENTER){
                            drive.followTrajectorySequenceAsync(center);
                        } else {
                            drive.followTrajectorySequenceAsync(right);
                        }
                        autoState = States.PARKING;
                        break;
                    }
                    slide.setSlidePosition(coneStackEncoderPositions[coneIndex]);
                    coneIndex++;
                    drive.followTrajectorySequenceAsync(junctionToStack);
                    autoState = States.HEADING_TO_CONES;
                }
                break;
            case HEADING_TO_CONES:

                if (!drive.isBusy()){
                    if (Math.abs(slide.getSlidePos() - slide.getTargetPos()) < 5){
                        claw.grab();
                        slide.setSlidePosition(Constants.HIGH_POSITION);
                        drive.followTrajectorySequenceAsync(junctionToStack);
                        autoState = States.HEADING_TO_JUNCTION;
                    }
                }
                break;
        }
        drive.update();
    }
}
