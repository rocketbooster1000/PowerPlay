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
public class AutoCycle extends OpMode {

    enum States{
        HEADING_TO_CONES,
        GRABBING,
        HEADING_TO_JUNCTION,
        PARKING
    }

    SampleMecanumDrive drive = null;
    Slide slide = new Slide();
    Claw claw = new Claw();
    Camera camera = new Camera();

    ElapsedTime slideTimer = new ElapsedTime();

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

    public static double startX = -36;
    public static double startY = -60;
    public static double changeX = -36;
    public static double changeY = -15;
    public static double scoreX = -28;
    public static double scoreY = -18;
    public static double scoreHeading = -42;
    public static double tangentX = -44;
    public static double tangentY = -12;
    public static double coneX = -60;
    public static double coneY = -12;
    public static double readyParkX = -36;
    public static double readyParky = -12;
    public static double zoneOneX = -60;
    public static double zoneTwoX = -36;
    public static double zoneThreeX = -12;

    boolean firstTimeCheckForRotation;


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
        firstTimeCheckForRotation = true;
    }

    @Override
    public void init_loop(){
        claw.grab();
        signalZone = camera.returnZoneEnumerated();
        telemetry.addData("Zone: ", signalZone);
    }

    @Override
    public void start(){
        startTraj = drive.trajectorySequenceBuilder(new Pose2d(startX, startY, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(changeX, changeY, Math.toRadians(scoreHeading)))
                .lineToConstantHeading(new Vector2d(scoreX, scoreY))
                .build();


        junctionToStack = drive.trajectorySequenceBuilder(new Pose2d(scoreX, scoreY, Math.toRadians(scoreHeading)))
                .setReversed(true)
                .splineTo(new Vector2d(tangentX, tangentY), Math.toRadians(180))
                .splineTo(new Vector2d(coneX, coneY), Math.toRadians(180))
                .build();

        stackToJunction = drive.trajectorySequenceBuilder(new Pose2d(-60, -12, Math.toRadians(0)))
                .setReversed(false)
                .splineTo(new Vector2d(tangentX, tangentY), Math.toRadians(0))
                .splineTo(new Vector2d(scoreX, scoreY), Math.toRadians(scoreHeading))
                .build();

        left = drive.trajectorySequenceBuilder(stackToJunction.end())
                .lineToConstantHeading(new Vector2d(readyParkX, readyParky))
                .lineToLinearHeading(new Pose2d(zoneOneX, readyParky, Math.toRadians(90)))
                .build();

        right = drive.trajectorySequenceBuilder(stackToJunction.end())
                .lineToConstantHeading(new Vector2d(readyParkX, readyParky))
                .lineToLinearHeading(new Pose2d(zoneThreeX, readyParky, Math.toRadians(90)))
                .build();

        drive.setPoseEstimate(startTraj.start());
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
                    slideTimer.reset();
                    firstTimeCheckForRotation = true;
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
                    slide.setSlidePosition(Constants.RED_ZONE);
                    drive.followTrajectorySequenceAsync(junctionToStack);
                    autoState = States.HEADING_TO_CONES;
                }
                break;
            case HEADING_TO_CONES:
                if (slideTimer.time() > 1){
                    slide.setSlidePosition(coneStackEncoderPositions[coneIndex]);
                    coneIndex++;
                    firstTimeCheckForRotation = false;
                }
                if (!drive.isBusy()){
                    if (Math.abs(slide.getSlidePos() - slide.getTargetPos()) < 5){
                        claw.grab();
                        slide.setSlidePosition(Constants.RED_ZONE);
                        autoState = States.GRABBING;
                    }
                }
                break;
            case GRABBING:
                if (Math.abs(slide.getSlidePos() - slide.getTargetPos()) < 5){
                    slide.setSlidePosition(Constants.HIGH_POSITION);
                    slide.rotateServo();
                    drive.followTrajectorySequenceAsync(junctionToStack);
                    autoState = States.HEADING_TO_JUNCTION;
                }
        }

        drive.update();
    }
}
