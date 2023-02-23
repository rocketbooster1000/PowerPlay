package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Constants;
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
        START,
        HEADING_TO_CONES
    }

    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    Slide slide = new Slide();
    Claw claw = new Claw();
    Camera camera = new Camera();

    TrajectorySequence startTraj;
    TrajectorySequence junctionToStack;
    TrajectorySequence stackToJunction;

    States autoState;
    SleeveDetection.ParkingPosition signalZone;

    @Override
    public void init(){
        slide.init(hardwareMap);
        claw.init(hardwareMap);
        camera.init(hardwareMap);
        autoState = States.START;
    }

    @Override
    public void init_loop(){
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
    }

    @Override
    public void loop(){
        switch (autoState){
            case START:

                if (!drive.isBusy()){
                    claw.release();
                    slide.rotateServo();
                    slide.setSlidePosition(Constants.CONE_FOUR);
                    drive.followTrajectorySequenceAsync(junctionToStack);
                    autoState = States.HEADING_TO_CONES;
                }
            case HEADING_TO_CONES:

                if (!drive.isBusy()){
                    claw.grab();

                }
        }
        drive.update();
    }
}
