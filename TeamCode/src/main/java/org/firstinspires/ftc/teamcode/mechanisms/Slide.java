package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Slide {
    private DcMotorEx linearSlideMotor;
    private Servo slideServo;
    private double ticksPerRevolution;
    private boolean hasBeenToldToRotate;
    public void init(HardwareMap hwMap){
        linearSlideMotor = hwMap.get(DcMotorEx.class, "Slide_Motor");
        linearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        linearSlideMotor.setPower(Constants.MOTOR_SLIDE_POWER);

        slideServo = hwMap.get(Servo.class, "Slide_Servo");
        slideServo.setDirection(Servo.Direction.FORWARD);

        ticksPerRevolution = linearSlideMotor.getMotorType().getTicksPerRev();
        hasBeenToldToRotate = false;
    }

    public void setSlidePosition(int encoderPosition){
        linearSlideMotor.setTargetPosition(encoderPosition);
    }

    public void moveSlide(double speed){
        if (linearSlideMotor.getCurrentPosition() > Constants.LINEAR_SLIDE_MINIMUM && linearSlideMotor.getCurrentPosition() < Constants.LINEAR_SLIDE_MAXIMUM){
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
            slideServo.setPosition(Constants.SLIDE_SERVO_ZERO_POSITION);
            hasBeenToldToRotate = false;
        } else {
            slideServo.setPosition(Constants.SLIDE_SERVO_ROTATED_POSITION);
            hasBeenToldToRotate = true;
        }
    }




}
