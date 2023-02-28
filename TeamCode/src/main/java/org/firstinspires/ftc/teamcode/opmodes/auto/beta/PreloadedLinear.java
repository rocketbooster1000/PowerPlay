package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Camera;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

@Config
@Autonomous()
public class PreloadedLinear extends LinearOpMode {
    Claw claw;
    Slide slide;
    Camera camera;
    SampleMecanumDrive drive;
    @Override
    public void runOpMode() throws InterruptedException{
        drive = new SampleMecanumDrive(hardwareMap);
        claw = new Claw();
        slide = new Slide();
        camera = new Camera();
        claw.init(hardwareMap);
        slide.init(hardwareMap);
        camera.init(hardwareMap);

    }
}
