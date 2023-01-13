/*
* This file contains the tele-op opmode for the robot
* To drive, use the joysticks
* To operate the lift either use:
*   -Left and right bumpers for the cone stacks (It will toggle through)
*   -D pad down, left, up, right for the ground, low, medium and high junctions respectively
*   -or manually operate with the left and right triggers
* To grab and/or release the claw, press A
* To rotate the claw press B
*
* Important!:
* There are implemented fail-safes in case gamepad inputs are conflicting
* However there are still possible fail points
*   -Please do not try to lift and rotate at the same time
*   -Please do not try to manually operate the slide while pressing a preset position
*   -Please do not hold down a button for an extended period of time
*
* Before initializing please pull the linear slide all the way to the bottom
 */

package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.SlideLevels;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.mechanisms.jasonmectest.mech.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;

@TeleOp(name = "drivers, pick up your controllers")
public class TeleOpFull extends OpMode {
    DriveTrain driveTrain = new DriveTrain();
    Slide slide = new Slide();
    Claw claw = new Claw();

    boolean aAlreadyPressed;
    boolean wantToGrab;
    boolean downAlreadyPressed;
    boolean leftAlreadyPressed;
    boolean upAlreadyPressed;
    boolean rightAlreadyPressed;
    boolean rightBumperAlreadyPressed;
    boolean leftBumperAlreadyPressed;
    boolean bAlreadyPressed;

    double opmodeSlidePower;

    SlideLevels level;
    SlideLevels junctionLevel;

    @Override
    public void init(){
        driveTrain.init(hardwareMap);
        slide.init(hardwareMap);
        claw.init(hardwareMap);

        wantToGrab = false;

        aAlreadyPressed = false;
        downAlreadyPressed = false;
        leftAlreadyPressed = false;
        upAlreadyPressed = false;
        rightAlreadyPressed = false;
        rightBumperAlreadyPressed = false;
        leftBumperAlreadyPressed = false;
        bAlreadyPressed = false;

        opmodeSlidePower = 0;

        level = SlideLevels.GROUND;
        junctionLevel = SlideLevels.GROUND;

        slide.setSlidePosition(Constants.GROUND_POSITION);
        claw.release();

        telemetry.addData("Initiation", " Complete");
    }

    @Override
    public void start(){
        driveTrain.resetYaw();
        telemetry.addLine("Start-up complete, pick up your controllers");
    }

    @Override
    public void loop(){
        driveTrain.drive(
                gamepad1.right_stick_x,
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                driveTrain.getHeadingDeg(),
                Constants.DRIVE_POWER_MODIFIER
        );
        telemetry.addData("Heading: ", driveTrain.getHeadingDeg());

        if (gamepad1.a && !aAlreadyPressed){
            wantToGrab = !wantToGrab;
            if (wantToGrab){
                claw.grab();
            } else {
                claw.release();
            }
        }
        aAlreadyPressed = gamepad1.a;


        if (gamepad1.dpad_down && !downAlreadyPressed){
            slide.setSlidePosition(Constants.GROUND_POSITION);
            claw.release();
            junctionLevel = SlideLevels.GROUND;
            telemetry.addData("Level: ", junctionLevel);
        }
        downAlreadyPressed = gamepad1.dpad_down;

        if (gamepad1.dpad_left && !leftAlreadyPressed){
            slide.setSlidePosition(Constants.LOW_POSITION);
            junctionLevel = SlideLevels.JUNCTION_LOW;
            telemetry.addData("Level: ", junctionLevel);
        }
        leftAlreadyPressed = gamepad1.dpad_left;

        if (gamepad1.dpad_up && !upAlreadyPressed){
            slide.setSlidePosition(Constants.MEDIUM_POSITION);
            junctionLevel = SlideLevels.JUNCTION_MEDIUM;
            telemetry.addData("Level: ", junctionLevel);
        }
        upAlreadyPressed = gamepad1.dpad_up;

        if (gamepad1.dpad_right && !rightAlreadyPressed){
            slide.setSlidePosition(Constants.HIGH_POSITION);
            junctionLevel = SlideLevels.JUNCTION_HIGH;
            telemetry.addData("Level: ", junctionLevel);
        }
        rightAlreadyPressed = gamepad1.dpad_right;

        if ((gamepad1.left_trigger != 0) || (gamepad1.right_trigger !=0)){
            opmodeSlidePower = gamepad1.right_trigger - gamepad1.left_trigger;
            slide.setLinearSlideMotorRunMode();
            telemetry.addData("Slide Mode: ", "Manual");
            slide.moveSlide(opmodeSlidePower);
        }

        if (!(gamepad1.left_bumper && gamepad1.right_bumper)) {
            if (gamepad1.right_bumper && !rightBumperAlreadyPressed) {
                switch (level) {
                    case GROUND:
                        slide.setSlidePosition(Constants.CONE_ONE);
                        level = SlideLevels.CONE_ONE;
                    case CONE_ONE:
                        slide.setSlidePosition(Constants.CONE_TWO);
                        level = SlideLevels.CONE_TWO;
                    case CONE_TWO:
                        slide.setSlidePosition(Constants.CONE_THREE);
                        level = SlideLevels.CONE_THREE;
                    case CONE_THREE:
                        slide.setSlidePosition(Constants.CONE_FOUR);
                        level = SlideLevels.CONE_FOUR;
                    case CONE_FOUR:
                        slide.setSlidePosition(Constants.GROUND_POSITION);
                        level = SlideLevels.GROUND;
                }
                telemetry.addData("Claw Level: ", level);
            }

            if (gamepad1.left_bumper && !leftBumperAlreadyPressed) {
                switch (level) {
                    case GROUND:
                        slide.setSlidePosition(Constants.CONE_FOUR);
                        level = SlideLevels.CONE_FOUR;
                    case CONE_FOUR:
                        slide.setSlidePosition(Constants.CONE_THREE);
                        level = SlideLevels.CONE_THREE;
                    case CONE_THREE:
                        slide.setSlidePosition(Constants.CONE_TWO);
                        level = SlideLevels.CONE_TWO;
                    case CONE_TWO:
                        slide.setSlidePosition(Constants.CONE_ONE);
                        level = SlideLevels.CONE_ONE;
                    case CONE_ONE:
                        slide.setSlidePosition(Constants.GROUND_POSITION);
                        level = SlideLevels.GROUND;
                }
                telemetry.addData("Claw Leve: ", level);
            }
        }
        leftBumperAlreadyPressed = gamepad1.left_bumper;
        rightBumperAlreadyPressed = gamepad1.right_bumper;

        if (gamepad1.b && !bAlreadyPressed){
            if (slide.getSlidePosition() < Constants.RED_ZONE){
                slide.setSlidePosition(Constants.RED_ZONE);
            }
            slide.rotateServo();
        }
        bAlreadyPressed = gamepad1.b;

        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger ==0){
            slide.setLinearSlideMotorPositionMode();
            telemetry.addData("Slide Mode: ", "Presets");
        }


    }

    @Override
    public void stop(){
        slide.setSlidePosition(Constants.GROUND_POSITION);
        claw.release();
    }
}
