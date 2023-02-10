package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

//if motor direction is FORWARD:
//negative power is extend, positive is retract


public class Slide {
    private DcMotorEx linearSlideMotor;
    private Servo slideServo;
    private double ticksPerRevolution;
    private boolean hasBeenToldToRotate;
    public void init(HardwareMap hwMap){
        linearSlideMotor = hwMap.get(DcMotorEx.class, "Slide_Motor");
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        linearSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        slideServo = hwMap.get(Servo.class, "Slide_Servo");
        slideServo.setDirection(Servo.Direction.FORWARD);

        ticksPerRevolution = linearSlideMotor.getMotorType().getTicksPerRev();
        hasBeenToldToRotate = true;
        //TEMP 2/1
        //This will set the slide servo to sit at the back of the robot (where we put the claw when picking up from ground)
        slideServo.setPosition(0.71);
    }

    public void setSlidePosition(int encoderPosition){
        linearSlideMotor.setTargetPosition(encoderPosition);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideMotor.setPower(Constants.MOTOR_SLIDE_POWER);
    }

    public void moveSlide(double speed){
        if (linearSlideMotor.getCurrentPosition() >= (Constants.LINEAR_SLIDE_MINIMUM - Constants.LINEAR_SLIDE_MARGIN_ERROR) && linearSlideMotor.getCurrentPosition() <= (Constants.LINEAR_SLIDE_MAXIMUM - Constants.LINEAR_SLIDE_MARGIN_ERROR)){
            linearSlideMotor.setPower(speed * Constants.MOTOR_SLIDE_POWER);
        }
    }

    public void setLinearSlideMotorRunMode(){
        linearSlideMotor.setPower(0);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setLinearSlideMotorPositionMode(){
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideMotor.setPower(Constants.MOTOR_SLIDE_POWER);
    }

    public int getSlidePosition(){
        return linearSlideMotor.getCurrentPosition();
    }

    public void rotateServo(){
        if (hasBeenToldToRotate){
            slideServo.setPosition(Constants.SLIDE_SERVO_ROTATED_POSITION);
            hasBeenToldToRotate = false;
        } else {
            slideServo.setPosition(Constants.SLIDE_SERVO_ZERO_POSITION);
            hasBeenToldToRotate = true;
        }
    }

    public void setPowerNoLimitations(double power){
        linearSlideMotor.setPower(power * Constants.MOTOR_SLIDE_POWER);
    }

    public int getTargetPos(){
        return linearSlideMotor.getTargetPosition();
    }
    public int getSlidePos(){
        return linearSlideMotor.getCurrentPosition();
    }

    public double getServoPosition(){
        return slideServo.getPosition();
    }

}
