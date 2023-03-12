package org.firstinspires.ftc.teamcode.opmodes.auto.beta;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Camera;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.SleeveDetection;

@Config
@Autonomous()
public class RRParking extends OpMode {
    SampleMecanumDrive drive = null;
    Camera camera = new Camera();

    TrajectorySequence start;
    TrajectorySequence left;
    TrajectorySequence right;

    SleeveDetection.ParkingPosition signalZone;

    public static double startX = -36;
    public static double startY = -63;

    public static double parkY = -35;
    public static double zone1 = -60;
    public static double zone2 = -36;
    public static double zone3 = -12;

    boolean firstTime;

    @Override
    public void init(){
        drive = new SampleMecanumDrive(hardwareMap);
        camera.init(hardwareMap);

        firstTime = true;

        start = drive.trajectorySequenceBuilder(new Pose2d(startX, startY, Math.toRadians(90)))
                .lineTo(new Vector2d(startX, parkY))
                .build();

        left = drive.trajectorySequenceBuilder(start.end())
                .lineToConstantHeading(new Vector2d(zone1, parkY))
                .build();

        right = drive.trajectorySequenceBuilder(start.end())
                .lineTo(new Vector2d(zone3,parkY))
                .build();



    }

    @Override
    public void init_loop(){
        signalZone = camera.returnZoneEnumerated();
        telemetry.addData("Signal zone: ", signalZone);
    }

    @Override
    public void start(){
        drive.setPoseEstimate(start.start());
        drive.followTrajectorySequenceAsync(start);
    }

    @Override
    public void loop(){
        if (!drive.isBusy() && firstTime){
            if (signalZone == SleeveDetection.ParkingPosition.LEFT){
                drive.followTrajectorySequenceAsync(left);
            } else if (signalZone == SleeveDetection.ParkingPosition.RIGHT) {
                drive.followTrajectorySequenceAsync(right);
            }
            firstTime = false;
        }
        drive.update();
    }
}
