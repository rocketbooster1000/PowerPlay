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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.SlideLevels;
import org.firstinspires.ftc.teamcode.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;
import org.firstinspires.ftc.teamcode.mechanisms.Slide;

@TeleOp(name = "drivers, pick up your controllers (new)")
public class TeleOpFullNew extends OpMode {
    //robot "subsystems"
    DriveTrain driveTrain = new DriveTrain();
    Slide slide = new Slide();
    Claw claw = new Claw();

    private ElapsedTime runtime = new ElapsedTime();

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
    boolean cycleRequested;
    boolean xAlreadyPressed;
    boolean cycledHigh;
    boolean checkServoFistTime;

    //motor powers and modifiers
    double opmodeSlidePower;
    double drivePower;
    double lastRunTime;

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
        xAlreadyPressed = false;
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
        cycleRequested = false;
        cycledHigh = false;
        checkServoFistTime = true;
        lastRunTime = 0;

        opmodeSlidePower = 0;
        drivePower = Constants.DRIVE_POWER_MODIFIER;

        level = SlideLevels.GROUND;
        junctionLevel = SlideLevels.GROUND;

        //reset hardware for operation
        slide.setSlidePosition(Constants.GROUND_POSITION);
        telemetry.addData("Status: ", "Going to ground");
        while (slide.getTargetPos() != slide.getSlidePosition()){

        }
        claw.grab();

        telemetry.addData("Initiation", " Complete");
        telemetry.addData("pick up", " your controllers");
    }

    @Override
    public void start(){
        //reset the gyro
        driveTrain.resetYaw();
        runtime.reset();
    }

    @Override
    public void loop(){
        //drive
        if (gamepad1.left_bumper && !leftJoystickPressed){
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




        //--------------------------------------------
        //slide


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
                    slide.setSlidePosition(Constants.LOW_POSITION);
                    level = SlideLevels.JUNCTION_LOW;
                    break;
                case JUNCTION_LOW:
                    slide.setSlidePosition(Constants.MEDIUM_POSITION);
                    level = SlideLevels.JUNCTION_MEDIUM;
                    break;
                case JUNCTION_MEDIUM:
                    slide.setSlidePosition(Constants.HIGH_POSITION);
                    level = SlideLevels.JUNCTION_HIGH;
                    break;
                case JUNCTION_HIGH:
                    slide.setSlidePosition(Constants.GROUND_POSITION);
                    level = SlideLevels.GROUND;
                    break;
            }
        }


        if (gamepad1.left_bumper && !leftBumperAlreadyPressed) {
            switch (level) {
                case GROUND:
                    slide.setSlidePosition(Constants.HIGH_POSITION);
                    level = SlideLevels.JUNCTION_HIGH;
                    break;
                case JUNCTION_HIGH:
                    slide.setSlidePosition(Constants.MEDIUM_POSITION);
                    level = SlideLevels.JUNCTION_MEDIUM;
                    break;
                case JUNCTION_MEDIUM:
                    slide.setSlidePosition(Constants.LOW_POSITION);
                    level = SlideLevels.JUNCTION_LOW;
                    break;
                case JUNCTION_LOW:
                    slide.setSlidePosition(Constants.GROUND_POSITION);
                    level = SlideLevels.JUNCTION_HIGH;
                    break;
            }
        } //switch statement for LB and RB toggles





        telemetry.addData("Level: ", level);
        telemetry.addData("Slide encoder ticks: ", slide.getSlidePosition());
        telemetry.addData("Target position: ", slide.getTargetPos());

        if (slide.getSlidePosition() > (Constants.LINEAR_SLIDE_MAXIMUM + 3)){
            slide.setSlidePosition(Constants.LINEAR_SLIDE_MAXIMUM);
        }

        if (slide.getSlidePosition() < (Constants.LINEAR_SLIDE_MINIMUM - 3)){
            slide.setSlidePosition(Constants.LINEAR_SLIDE_MINIMUM);
        } //ensure slide in operable window of position

        if (gamepad1.x && !xAlreadyPressed){
            rotationRequested = false;
            cycleRequested = true;
            cycledHigh = !cycledHigh;
            if (cycledHigh){
                claw.grab();
                wantToGrab = false;
                slide.setSlidePosition(Constants.HIGH_POSITION);
            } else {
                claw.release();
                wantToGrab = true;
            }    
        }

        if (gamepad1.b && !bAlreadyPressed) {
            rotationRequested = true;
        }

        canRotate = slide.getSlidePos() >= Constants.RED_ZONE;

        if (!canRotate && rotationRequested) {
            if (redZoneFirstTime) {
                slide.setSlidePosition(Constants.RED_ZONE);
                redZoneFirstTime = false;
            }
        }
        
        if (cycleRequested){
            if (canRotate){
                slide.rotateServo();
                if (!cycledHigh) {
                    if (checkServoFistTime){
                        lastRunTime = runtime.time();
                        checkServoFistTime = false;
                    }
                    if ((runtime.time() - lastRunTime) >= 1){
                        slide.setSlidePosition(Constants.GROUND_POSITION);
                        checkServoFistTime = false;
                        cycleRequested = false;
                    }
                }
            } else {
                if (!cycledHigh){
                    slide.setSlidePosition(Constants.RED_ZONE);
                }
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
        telemetry.addData("User wants the cycle to go to high position: ", cycledHigh);

        if (!wantToGrab){
            claw.grab();
        } else {
            claw.release();
        }
        telemetry.addData("Claw ", (!wantToGrab) ? "Grabbed" : "Released");

        aAlreadyPressed = gamepad1.a;
        bAlreadyPressed = gamepad1.b;
        xAlreadyPressed = gamepad1.x;
        yAlreadyPressed = gamepad1.y;
        downAlreadyPressed = gamepad1.dpad_down;
        leftAlreadyPressed = gamepad1.dpad_left;
        upAlreadyPressed = gamepad1.dpad_up;
        rightAlreadyPressed = gamepad1.dpad_right;
        leftBumperAlreadyPressed = gamepad1.left_bumper;
        rightBumperAlreadyPressed = gamepad1.right_bumper;
        leftJoystickPressed = gamepad1.left_stick_button;
        rightStickPressed = gamepad1.right_stick_button;

    }

    @Override
    public void stop(){
        slide.setSlidePosition(Constants.GROUND_POSITION);
    }
}
