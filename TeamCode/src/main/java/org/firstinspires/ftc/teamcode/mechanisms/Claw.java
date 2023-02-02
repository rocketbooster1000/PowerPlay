package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Claw {
    Servo clawServo;

    public void init(HardwareMap hwMap){
        clawServo = hwMap.get(Servo.class, "Claw_Servo");
        clawServo.setDirection(Servo.Direction.REVERSE);
        clawServo.scaleRange(Constants.CLAW_MIN, Constants.CLAW_MAX);
    }

    public void grab(){
        clawServo.setPosition(1);
    }

    /*public void release(){
        clawServo.setPosition(0);
    }*/
    public void release(){
        clawServo.setPosition(0.58);
    } //This will make it so when it releases it wont hit the slides of the robot
    public void setPosition(double position){
        clawServo.setPosition(position);
    }
}

