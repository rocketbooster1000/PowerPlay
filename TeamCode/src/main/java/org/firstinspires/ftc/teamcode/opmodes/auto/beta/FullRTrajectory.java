package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous
public class FullRTrajectory extends OpMode {
    SampleMecanumDrive drive = null;

    TrajectorySequence start;

    @Override
    public void init(){
        drive = new SampleMecanumDrive(hardwareMap);
        start = drive.trajectorySequenceBuilder(new Pose2d(-36, -60, Math.toRadians(90)))
                .lineToLinearHeading(new Pose2d(-36, -15, Math.toRadians(-42)))
                .lineToConstantHeading(new Vector2d(-28, -18))
                .setReversed(true)
                .splineTo(new Vector2d(-44, -12), Math.toRadians(180))
                .splineTo(new Vector2d(-60, -12), Math.toRadians(180))
                .waitSeconds(1)
                .setReversed(false)
                .splineTo(new Vector2d(-44, -12), Math.toRadians(0))
                .splineTo(new Vector2d(-28, -18), Math.toRadians(-42))
                .build();
        drive.setPoseEstimate(start.start());
    }

    @Override
    public void start(){
        drive.followTrajectorySequenceAsync(start);
    }

    @Override
    public void loop(){
        drive.update();
    }
}
