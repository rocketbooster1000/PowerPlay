package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        Pose2d junctionPose = new Pose2d(31, 0, Math.toRadians(0));


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedDark())
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(30, 15, Math.toRadians(213), Math.toRadians(60), 13.75)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(31, 0, Math.toRadians(0)))
                                .strafeRight(12)
                                .lineToLinearHeading(new Pose2d(12, -12, Math.toRadians(90)))
                                .build()
                );

        RoadRunnerBotEntity secondBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(30, 15, Math.toRadians(213), Math.toRadians(60), 13.75)
                        .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(38.23, -64.72, Math.toRadians(0.00)))
                                        .lineTo(new Vector2d(31, -0))
                                        .build()
                        );

        RoadRunnerBotEntity thirdBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(30, 15, Math.toRadians(213), Math.toRadians(60), 13.75)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(junctionPose)
                                .lineToLinearHeading(new Pose2d(36.00, -36.00, Math.toRadians(90))).build()
                );

        RoadRunnerBotEntity fourthBot = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeBlueLight())
                .setConstraints(30, 15, Math.toRadians(213), Math.toRadians(60), 13.75)
                    .followTrajectorySequence(drive ->
                            drive.trajectorySequenceBuilder(junctionPose)
                                    .lineToLinearHeading(new Pose2d(36.00, -36.00, Math.toRadians(90)))
                                    .strafeRight(24)
                                    .build()
                    );


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(secondBot)
                .addEntity(thirdBot)
                .addEntity(fourthBot)
                .start();
    }
}