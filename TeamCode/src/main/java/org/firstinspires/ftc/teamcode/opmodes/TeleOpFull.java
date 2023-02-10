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

//Cone_one was weird
//no claw.release ground
//slide servo
//claw.grab below red zone
// Where slide is at the bottom on the slide we always do that is turning servo position 0.71
// oppisiite side is 0.04 for dropping cones
package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.SlideLevels;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;

@TeleOp(name = "drivers, pick up your controllers")
public class TeleOpFull extends OpMode {
    //robot "subsystems"
    DriveTrain driveTrain = new DriveTrain();
    Slide slide = new Slide();
    Claw claw = new Claw();

    //State machine booleans for the gamepad (see LearnJavaForFTC pdf chapter 12 for more info)
    boolean aAlreadyPressed;
    boolean wantToGrab;
    boolean downAlreadyPressed;
    boolean leftAlreadyPressed;
    boolean upAlreadyPressed;
    boolean rightAlreadyPressed;
    boolean rightBumperAlreadyPressed;
    boolean leftBumperAlreadyPressed;
    boolean bAlreadyPressed;
    boolean leftJoystickPressed;
    boolean rightStickPressed;
    boolean isFirstTimeAfterTriggerRelease;
    boolean yAlreadyPressed;
    boolean isFirstTimeAfterTriggerPress;
    boolean rotationRequested;
    boolean canRotate;
    boolean redZoneFirstTime;
    boolean wantSlowDrive;

    //motor powers and modifiers
    double opmodeSlidePower;
    double drivePower;

    //enumerated types for juntion and cone levels
    SlideLevels level;
    SlideLevels junctionLevel;

    @Override
    public void init(){
        driveTrain.init(hardwareMap);
        //When the slide initializes the claw will go to the slide servo position 0.71
        slide.init(hardwareMap);
        claw.init(hardwareMap);

        //initializing variables
        wantToGrab = false;

        aAlreadyPressed = false;
        downAlreadyPressed = false;
        leftAlreadyPressed = false;
        upAlreadyPressed = false;
        rightAlreadyPressed = false;
        rightBumperAlreadyPressed = false;
        leftBumperAlreadyPressed = false;
        bAlreadyPressed = false;
        leftJoystickPressed = false;
        rightStickPressed = false;
        isFirstTimeAfterTriggerRelease = false;
        yAlreadyPressed = false;
        isFirstTimeAfterTriggerPress = true;
        rotationRequested = false;
        canRotate = false;
        redZoneFirstTime = true;
        wantSlowDrive = false;

        opmodeSlidePower = 0;
        drivePower = Constants.DRIVE_POWER_MODIFIER;

        level = SlideLevels.GROUND;
        junctionLevel = SlideLevels.GROUND;

        //reset hardware for operation
        slide.setSlidePosition(Constants.GROUND_POSITION);
        while (slide.getTargetPos() != slide.getSlidePosition()){
            telemetry.addData("Status: ", "Going to ground");
        }
        claw.grab();

        telemetry.addData("Initiation", " Complete");
    }

    @Override
    public void start(){
        //reset the gyro
        driveTrain.resetYaw();
        telemetry.addLine("Start-up complete, pick up your controllers");
    }

    @Override
    public void loop(){
        //drive
        if (gamepad1.left_bumper && !leftBumperAlreadyPressed){
            wantSlowDrive = !wantSlowDrive;
            drivePower = (wantSlowDrive) ? Constants.SLOW_DRIVE_MODIFIER : Constants.DRIVE_POWER_MODIFIER;
        }

        driveTrain.drive(
                -gamepad1.right_stick_x,
                -gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                driveTrain.getHeadingDeg(),
                drivePower
        ); //drive

        if (gamepad1.y && !yAlreadyPressed){
            driveTrain.resetYaw();
        }
        yAlreadyPressed = gamepad1.y; //reset heading state machine

        telemetry.addData("Heading: ", driveTrain.getHeadingDeg());
        telemetry.addData("Drive power: ", drivePower);

        //----------------------------------
        //Claw
        if (gamepad1.a && !aAlreadyPressed){
            if (wantToGrab){
                wantToGrab = false;
            } else {
                claw.release();
                wantToGrab = true;
            }
        }

        if (!wantToGrab){
            claw.grab();
        } else {
            claw.release();
        }
        aAlreadyPressed = gamepad1.a; //confused by this state machine? see the chapter 12 example in the LearnJavaForFTC pdf on state machines

        //--------------------------------------------
        //slide
        if (gamepad1.dpad_down && !downAlreadyPressed){
            slide.setSlidePosition(Constants.GROUND_POSITION);
            junctionLevel = SlideLevels.GROUND;
        }
        downAlreadyPressed = gamepad1.dpad_down;

        if (gamepad1.dpad_left && !leftAlreadyPressed){
            slide.setSlidePosition(Constants.LOW_POSITION);
            junctionLevel = SlideLevels.JUNCTION_LOW;
        }
        leftAlreadyPressed = gamepad1.dpad_left;

        if (gamepad1.dpad_up && !upAlreadyPressed){
            slide.setSlidePosition(Constants.MEDIUM_POSITION);
            junctionLevel = SlideLevels.JUNCTION_MEDIUM;
        }
        upAlreadyPressed = gamepad1.dpad_up;

        if (gamepad1.dpad_right && !rightAlreadyPressed){
            slide.setSlidePosition(Constants.HIGH_POSITION);
            junctionLevel = SlideLevels.JUNCTION_HIGH;
        }
        rightAlreadyPressed = gamepad1.dpad_right;

        telemetry.addData("Junction Level: ", junctionLevel);

        if ((gamepad1.left_trigger != 0) || (gamepad1.right_trigger !=0)){
            opmodeSlidePower = gamepad1.right_trigger - gamepad1.left_trigger;
            if (isFirstTimeAfterTriggerPress){
                slide.setLinearSlideMotorRunMode();
                isFirstTimeAfterTriggerPress = false;
            }
            telemetry.addData("Slide Mode: ", "Manual");
            slide.moveSlide(opmodeSlidePower);
            isFirstTimeAfterTriggerRelease = true;
        } else {
            isFirstTimeAfterTriggerPress = true;
            if (isFirstTimeAfterTriggerRelease){
                slide.setSlidePosition(slide.getSlidePosition());
                isFirstTimeAfterTriggerRelease = false;
            }
            telemetry.addData("Slide Mode: ", "Run to position");
        }

        if (gamepad1.right_bumper && !rightBumperAlreadyPressed) {
            switch (level) {
                case GROUND:
                    slide.setSlidePosition(Constants.CONE_ONE);
                    level = SlideLevels.CONE_ONE;
                    break;
                case CONE_ONE:
                    slide.setSlidePosition(Constants.CONE_TWO);
                    level = SlideLevels.CONE_TWO;
                    break;
                case CONE_TWO:
                    slide.setSlidePosition(Constants.CONE_THREE);
                    level = SlideLevels.CONE_THREE;
                    break;
                case CONE_THREE:
                    slide.setSlidePosition(Constants.CONE_FOUR);
                    level = SlideLevels.CONE_FOUR;
                    break;
                case CONE_FOUR:
                    slide.setSlidePosition(Constants.GROUND_POSITION);
                    level = SlideLevels.GROUND;
                    break;
            }
        }

        /*
        if (gamepad1.left_bumper && !leftBumperAlreadyPressed) {
            switch (level) {
                case GROUND:
                    slide.setSlidePosition(Constants.CONE_FOUR);
                    level = SlideLevels.CONE_FOUR;
                    break;
                case CONE_FOUR:
                    slide.setSlidePosition(Constants.CONE_THREE);
                    level = SlideLevels.CONE_THREE;
                    break;
                case CONE_THREE:
                    slide.setSlidePosition(Constants.CONE_TWO);
                    level = SlideLevels.CONE_TWO;
                    break;
                case CONE_TWO:
                    slide.setSlidePosition(Constants.CONE_ONE);
                    level = SlideLevels.CONE_ONE;
                    break;
                case CONE_ONE:
                    slide.setSlidePosition(Constants.GROUND_POSITION);
                    level = SlideLevels.GROUND;
                    break;
            }
        } //switch statement for LB and RB toggles
         */

        leftBumperAlreadyPressed = gamepad1.left_bumper;
        rightBumperAlreadyPressed = gamepad1.right_bumper;
        telemetry.addData("Cone level: ", level);
        telemetry.addData("Slide encoder ticks: ", slide.getSlidePosition());
        telemetry.addData("Target position: ", slide.getTargetPos());

        if (slide.getSlidePosition() > (Constants.LINEAR_SLIDE_MAXIMUM + 3)){
            slide.setSlidePosition(Constants.LINEAR_SLIDE_MAXIMUM);
        }

        if (slide.getSlidePosition() < (Constants.LINEAR_SLIDE_MINIMUM - 3)){
            slide.setSlidePosition(Constants.LINEAR_SLIDE_MINIMUM);
        } //ensure slide in operable window of position

        if (gamepad1.b && !bAlreadyPressed) {
            rotationRequested = true;
        }
        bAlreadyPressed = gamepad1.b; //rotation servo

        canRotate = slide.getSlidePos() >= Constants.RED_ZONE;

        if (!canRotate && rotationRequested){
            if (redZoneFirstTime){
                slide.setSlidePosition(Constants.RED_ZONE + 10);
                redZoneFirstTime = false;
            }
        }

        if (rotationRequested && canRotate){
            slide.rotateServo();
            rotationRequested = false;
            redZoneFirstTime = true;
        }

        telemetry.addData("rotation requested: ", rotationRequested);
        telemetry.addData("can rotate: ", canRotate);
        telemetry.addData("redZoneFirstTime: ", redZoneFirstTime);
        telemetry.addData("Slide Servo Position: ", slide.getServoPosition());
    }

    @Override
    public void stop(){
        slide.setSlidePosition(Constants.GROUND_POSITION);
    }
}
