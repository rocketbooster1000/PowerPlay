package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.SleeveDetection;

@Config
@Autonomous(name = "Right auto preloaded")
public class PreloadedAuto extends OpMode {

    enum RobotState{
        PUSHING_CONE,
        COMING_BACK,
        SCORE,
        GETTING_READY_TO_PARK,
        PARKING,
        STILL
    }

    SampleMecanumDrive drive = null;
    Slide slide = new Slide();
    Claw claw = new Claw();
    Camera camera = new Camera();

    public static double START_X = 36;
    public static double START_Y = -60;

    public static double PUSH_CONE_DISTANCE = 5;

    public static double COME_BACK_Y = .01;

    public static double SCORE_X = 27;
    public static double SCORE_Y = 4.5;

    public static double ZONE_ONE_X = 12;
    public static double ZONE_TWO_X = 36;
    public static double ZONE_THREE_X = 64;


    TrajectorySequence pushCone;

    TrajectorySequence zoneOne;
    TrajectorySequence zoneTwo;
    TrajectorySequence zoneThree;

    TrajectorySequence comeBack;
    TrajectorySequence score;
    TrajectorySequence readyForPark;




    SleeveDetection.ParkingPosition signalZone;

    RobotState robotState;



    @Override
    public void init(){
        drive = new SampleMecanumDrive(hardwareMap);
        slide.init(hardwareMap);
        claw.init(hardwareMap);
        camera.init(hardwareMap);

        pushCone = drive.trajectorySequenceBuilder(new Pose2d(START_X, START_Y, Math.toRadians(0.00)))
                .lineTo(new Vector2d(START_X, PUSH_CONE_DISTANCE))
                .build();

        comeBack = drive.trajectorySequenceBuilder(pushCone.end())
                .lineTo(new Vector2d(START_X, COME_BACK_Y))
                .build();

        score = drive.trajectorySequenceBuilder(comeBack.end())
                .lineTo(new Vector2d(SCORE_X, COME_BACK_Y))
                .turn(Math.toRadians(-90))
                .lineTo(new Vector2d(SCORE_X, SCORE_Y))
                .build();

        readyForPark = drive.trajectorySequenceBuilder(score.end())
                .lineTo(new Vector2d(SCORE_X, COME_BACK_Y))
                .build();



        zoneOne = drive.trajectorySequenceBuilder(readyForPark.end())
                .lineToLinearHeading(new Pose2d(ZONE_ONE_X, COME_BACK_Y, Math.toRadians(90)))
                .build();

        zoneTwo = drive.trajectorySequenceBuilder(readyForPark.end())
                .lineToLinearHeading(new Pose2d(ZONE_TWO_X, COME_BACK_Y, Math.toRadians(90)))
                .build();

        zoneThree = drive.trajectorySequenceBuilder(readyForPark.end())
                        .lineToLinearHeading(new Pose2d(ZONE_THREE_X, COME_BACK_Y, Math.toRadians(90)))
                                .build();


        drive.setPoseEstimate(pushCone.start());


    }


    @Override
    public void init_loop(){
        signalZone = camera.returnZoneEnumerated();
        telemetry.addData("Zone: ", signalZone);
        claw.grab();
    }

    @Override
    public void start(){
        robotState = RobotState.PUSHING_CONE;
        drive.followTrajectorySequenceAsync(pushCone);
    }

    @Override
    public void loop(){
        switch (robotState){
            case PUSHING_CONE:
                claw.grab();
                if (slide.getTargetPos() != Constants.HIGH_POSITION){
                    slide.setSlidePosition(Constants.HIGH_POSITION);
                }
                if (!drive.isBusy()){
                    drive.followTrajectorySequenceAsync(comeBack);
                    robotState = RobotState.COMING_BACK;
                }
                break;
            case COMING_BACK:
                claw.grab();

                if (!drive.isBusy()){
                    drive.followTrajectorySequenceAsync(score);
                    robotState = RobotState.SCORE;
                }
                break;
            case SCORE:
                claw.grab();
                if (!drive.isBusy() && (Math.abs(slide.getSlidePos() - Constants.HIGH_POSITION) <= 5)){
                    claw.release();
                    drive.followTrajectorySequenceAsync(readyForPark);
                    robotState = RobotState.GETTING_READY_TO_PARK;
                }
                break;
            case GETTING_READY_TO_PARK:
                if (!drive.isBusy()){
                    slide.setSlidePosition(0);
                    if (signalZone == SleeveDetection.ParkingPosition.LEFT){
                        drive.followTrajectorySequenceAsync(zoneOne);
                    } else if (signalZone == SleeveDetection.ParkingPosition.CENTER){
                        drive.followTrajectorySequenceAsync(zoneTwo);
                    } else if (signalZone == SleeveDetection.ParkingPosition.RIGHT){
                        drive.followTrajectorySequenceAsync(zoneThree);
                    }
                    robotState = RobotState.PARKING;
                }
                break;
            case PARKING:
                if (!drive.isBusy()){
                    robotState = RobotState.STILL;
                }
                break;
            case STILL:
                telemetry.addData("Status: ", "done");

        }
        telemetry.addData("Robot state: ", robotState);
        drive.update();
    }
}
