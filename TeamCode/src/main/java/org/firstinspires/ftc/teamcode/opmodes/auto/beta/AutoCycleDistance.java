package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.mechanisms.beta.DistanceSensorLeft;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.vision.SleeveDetection;

@Config
@Autonomous(name = "Distance left")
public class AutoCycleDistance extends OpMode {

    enum States{
        START,
        HEADING_TO_CONES_1,
        HEADING_TO_CONES_2,
        GRABBING,
        HEADING_TO_JUNCTION,
        PARKING
    }

    SampleMecanumDrive drive = null;
    Slide slide = new Slide();
    Claw claw = new Claw();
    Camera camera = new Camera();
    DistanceSensorLeft leftSensor = new DistanceSensorLeft();

    ElapsedTime slideTimer = new ElapsedTime();
    ElapsedTime clawTimer = new ElapsedTime();

    TrajectorySequence startTraj;
    TrajectorySequence junctionToStack;
    TrajectorySequence stackToJunction;
    TrajectorySequence left;
    TrajectorySequence center;
    TrajectorySequence right;
    TrajectorySequence centerAlt;
    TrajectorySequence rightAlt;

    States autoState;
    SleeveDetection.ParkingPosition signalZone;


    int coneIndex;

    public static double timeToCycle = 25;

    public static double startX = -36;
    public static double startY = -63;
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
    boolean firstTimeCone;
    boolean start;
    boolean clawFirstTime;


    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init(){
        drive = new SampleMecanumDrive(hardwareMap);
        slide.init(hardwareMap);
        claw.init(hardwareMap);
        camera.init(hardwareMap);
        leftSensor.init(hardwareMap);
        autoState = States.START;
        firstTimeCheckForRotation = true;
        firstTimeCone = true;
        start = true;
        clawFirstTime = true;
    }

    @Override
    public void init_loop(){
        claw.grab();
        signalZone = camera.returnZoneEnumerated();
        telemetry.addData("Zone: ", signalZone);
        telemetry.addData("DIstance from wall: ", leftSensor.returnDistance(DistanceUnit.INCH));
    }

    @Override
    public void start(){
        double turn = -90 + scoreHeading;
        startTraj = drive.trajectorySequenceBuilder(new Pose2d(startX, startY, Math.toRadians(90)))
                .lineTo(new Vector2d(changeX, changeY))
                .turn(Math.toRadians(turn))
                .lineToConstantHeading(new Vector2d(scoreX, scoreY))
                .build();


        junctionToStack = drive.trajectorySequenceBuilder(startTraj.end())
                .setReversed(true)
                .splineTo(new Vector2d(tangentX, tangentY), Math.toRadians(180))
                .splineTo(new Vector2d(coneX, coneY), Math.toRadians(180))
                .build();

        stackToJunction = drive.trajectorySequenceBuilder(new Pose2d(coneX, coneY, Math.toRadians(0)))
                .splineTo(new Vector2d(tangentX, tangentY), Math.toRadians(0))
                .splineTo(new Vector2d(scoreX, scoreY), Math.toRadians(scoreHeading))
                .build();

        left = drive.trajectorySequenceBuilder(stackToJunction.end())
                .lineToConstantHeading(new Vector2d(readyParkX, readyParky))
                .lineToLinearHeading(new Pose2d(zoneOneX, readyParky, Math.toRadians(90)))
                .build();

        center = drive.trajectorySequenceBuilder(stackToJunction.end())
                .lineToLinearHeading(new Pose2d(readyParkX, readyParky, Math.toRadians(90)))
                .build();

        right = drive.trajectorySequenceBuilder(stackToJunction.end())
                .lineToConstantHeading(new Vector2d(readyParkX, readyParky))
                .lineToLinearHeading(new Pose2d(zoneThreeX, readyParky, Math.toRadians(90)))
                .build();

        centerAlt = drive.trajectorySequenceBuilder(junctionToStack.end())
                .lineTo(new Vector2d(zoneTwoX, coneY))
                .turn(Math.toRadians(90))
                .build();

        rightAlt = drive.trajectorySequenceBuilder(junctionToStack.end())
                .lineTo(new Vector2d(zoneThreeX, coneY))
                .turn(Math.toRadians(90))
                .build();



        drive.setPoseEstimate(new Pose2d(startX, startY, Math.toRadians(90)));
        drive.followTrajectorySequenceAsync(startTraj);
        slide.setSlidePosition(Constants.MEDIUM_POSITION);
        runtime.reset();
    }

    @Override
    public void loop(){
        int[] coneStackEncoderPositions = {Constants.CONE_FOUR, Constants.CONE_THREE, Constants.CONE_TWO, Constants.CONE_TWO, Constants.GROUND_POSITION};
        switch (autoState){
            case START:
                if (start && slide.getSlidePos() > Constants.RED_ZONE){
                    slide.rotateServo();
                    start = false;
                }
                claw.grab();
                if (!drive.isBusy()){
                    claw.release();
                    clawTimer.reset();
                    slideTimer.reset();
                    firstTimeCheckForRotation = true;
                    firstTimeCone = true;
                    if (runtime.time() > timeToCycle){
                        slide.setSlidePosition(0);
                        if (signalZone == SleeveDetection.ParkingPosition.LEFT){
                            drive.followTrajectorySequenceAsync(left);
                        } else if (signalZone == SleeveDetection.ParkingPosition.CENTER){
                            drive.followTrajectorySequenceAsync(center);
                        }else {
                            drive.followTrajectorySequenceAsync(right);
                        }
                        autoState = States.PARKING;
                        break;
                    }

                    drive.followTrajectorySequenceAsync(junctionToStack);
                    autoState = States.HEADING_TO_CONES_1;
                }
                break;
            case HEADING_TO_JUNCTION:
                claw.grab();
                if (!drive.isBusy()){
                    claw.release();
                    clawTimer.reset();
                    slideTimer.reset();
                    firstTimeCheckForRotation = true;
                    firstTimeCone = true;
                    clawFirstTime = true;
                    if (runtime.time() > timeToCycle){
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

                    drive.followTrajectorySequenceAsync(junctionToStack);
                    autoState = States.HEADING_TO_CONES_1;
                }
                break;
            case HEADING_TO_CONES_1:
                if  (clawTimer.time() > 0.3){
                    slide.rotateServo();
                    autoState = States.HEADING_TO_CONES_2;
                }
                break;
            case HEADING_TO_CONES_2:
                if (slideTimer.time() > 1 && firstTimeCone){
                    slide.setSlidePosition(coneStackEncoderPositions[coneIndex]);
                    coneIndex++;
                    firstTimeCheckForRotation = false;
                    firstTimeCone = false;
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
                    if (runtime.time() > 25){
                        if (signalZone == SleeveDetection.ParkingPosition.LEFT){
                            autoState = States.PARKING;

                        } else if (signalZone == SleeveDetection.ParkingPosition.CENTER){
                            autoState = States.PARKING;
                            drive.followTrajectorySequenceAsync(centerAlt);
                        } else {
                            autoState = States.PARKING;
                            drive.followTrajectorySequenceAsync(rightAlt);
                        }
                        break;
                    }
                    slide.setSlidePosition(Constants.MEDIUM_POSITION);
                    slide.rotateServo();
                    drive.followTrajectorySequenceAsync(stackToJunction);
                    autoState = States.HEADING_TO_JUNCTION;
                }
                break;
            case PARKING:
                if (slide.getTargetPos() != 0){
                    slide.setSlidePosition(0);
                }
                break;
        }
        telemetry.addData("Cone indes: ", coneIndex);
        drive.update();
    }
}
