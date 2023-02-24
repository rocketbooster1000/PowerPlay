package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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
        HEADING_TO_JUNCTION,
        SCORING,
        PARKING,
        STILL
    }

    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
    Slide slide = new Slide();
    Claw claw = new Claw();
    Camera camera = new Camera();

    TrajectorySequence startTraj = startTraj = drive.trajectorySequenceBuilder(new Pose2d(38.23, -64.72, Math.toRadians(0.00)))
            .lineTo(new Vector2d(31.28, -0.33))
            .build();
    TrajectorySequence zoneOne = drive.trajectorySequenceBuilder(startTraj.end())
            .lineTo(new Vector2d(34.59, -14.23))
            .splineTo(new Vector2d(11.42, -13.74), Math.toRadians(-6.01))
            .lineTo(new Vector2d(10.43, -37.57))
            .build();
    TrajectorySequence zoneTwo = drive.trajectorySequenceBuilder(startTraj.end())
            .lineToLinearHeading(new Pose2d(36.00, -36.00, Math.toRadians(90))).build();
    SleeveDetection.ParkingPosition signalZone;

    RobotState robotState;

    @Override
    public void init(){
        slide.init(hardwareMap);
        claw.init(hardwareMap);
        camera.init(hardwareMap);

        drive.setPoseEstimate(startTraj.start());


    }


    @Override
    public void init_loop(){
        signalZone = camera.returnZoneEnumerated();
        telemetry.addData("Zone: ", signalZone);
    }

    @Override
    public void start(){
        robotState = RobotState.HEADING_TO_JUNCTION;
        drive.followTrajectorySequenceAsync(startTraj);
    }

    @Override
    public void loop(){
        switch (robotState){
            case HEADING_TO_JUNCTION:
                if (!drive.isBusy()){

                }
        }
        drive.update();
    }
}
