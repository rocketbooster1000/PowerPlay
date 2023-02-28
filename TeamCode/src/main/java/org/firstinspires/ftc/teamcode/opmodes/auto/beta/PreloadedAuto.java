package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.Camera;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.SleeveDetection;

@Config
@Autonomous()
public class PreloadedAuto extends OpMode {

    enum RobotState{
        PUSHING_CONE,
        HEADING_TO_JUNCTION,
        SCORING,
        PARKING,
        STILL
    }

    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    Slide slide = new Slide();
    Claw claw = new Claw();
    Camera camera = new Camera();
    ElapsedTime slideTimer = new ElapsedTime();

    public static double START_X = 36;
    public static double START_Y = -60;

    public static double PUSH_CONE_DISTANCE = 75;

    public static double COME_BACK_Y = -12;

    public static double SCORE_X = 24;
    public static double SCORE_Y = -10;


    TrajectorySequence pushCone;

    TrajectorySequence zoneOne;
    TrajectorySequence zoneTwo;
    TrajectorySequence zoneThree;

    TrajectorySequence comeBack;
    TrajectorySequence score;

    Pose2d junctionPose = new Pose2d(31, 0, Math.toRadians(0));


    SleeveDetection.ParkingPosition signalZone;

    RobotState robotState;

    TrajectorySequence parking;

    @Override
    public void init(){
        slide.init(hardwareMap);
        claw.init(hardwareMap);
        camera.init(hardwareMap);

        pushCone = drive.trajectorySequenceBuilder(new Pose2d(START_X, -START_Y, Math.toRadians(0.00)))
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

        zoneOne = drive.trajectorySequenceBuilder(new Pose2d(31, 0, Math.toRadians(0)))
                .strafeRight(12)
                .lineToLinearHeading(new Pose2d(12, -12, Math.toRadians(90)))
                .build();

        zoneTwo = drive.trajectorySequenceBuilder(junctionPose)
                .lineToLinearHeading(new Pose2d(36.00, -36.00, Math.toRadians(90))).build();

        zoneThree = drive.trajectorySequenceBuilder(junctionPose)
                .lineToLinearHeading(new Pose2d(36.00, -36.00, Math.toRadians(90)))
                .strafeRight(24)
                .build();


        drive.setPoseEstimate(pushCone.start());


    }


    @Override
    public void init_loop(){
        signalZone = camera.returnZoneEnumerated();
        telemetry.addData("Zone: ", signalZone);
        switch (signalZone){
            case LEFT:
                parking = zoneOne;
                break;
            case CENTER:
                parking = zoneTwo;
                break;
            case RIGHT:
                parking = zoneThree;
                break;
        }
        claw.grab();
    }

    @Override
    public void start(){
        robotState = RobotState.HEADING_TO_JUNCTION;
        drive.followTrajectorySequenceAsync(pushCone);
    }

    @Override
    public void loop(){
        switch (robotState){
            case PUSHING_CONE:

            case HEADING_TO_JUNCTION:
                if (slide.getTargetPos() != Constants.HIGH_POSITION){
                    slide.setSlidePosition(Constants.HIGH_POSITION);
                }

                claw.grab();

                if (!drive.isBusy()){
                    claw.release();
                    robotState = RobotState.SCORING;
                    slide.rotateServo();
                    slideTimer.reset();
                    drive.followTrajectorySequenceAsync(parking);
                }
                break;
            case PARKING:
                if (slideTimer.time() >= Constants.SERVO_ROTATE_TIME && slide.getTargetPos() != 0){
                    slide.setSlidePosition(0);
                }

                if (!drive.isBusy()) {
                    robotState = RobotState.STILL;
                }
                break;
            case STILL:
                telemetry.addData("Auto: ", "Finished");
                break;
        }
        drive.update();
    }
}
