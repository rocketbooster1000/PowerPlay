package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

public class Claw {
    Servo clawServoLeft;
    Servo clawServoRight;

    public void init(HardwareMap hwMap){
        clawServoLeft = hwMap.get(Servo.class, "Claw_Servo_Left");
        clawServoRight = hwMap.get(Servo.class, "Claw_Servo_Right");
        clawServoRight.setDirection(Servo.Direction.FORWARD);
        clawServoLeft.setDirection(Servo.Direction.REVERSE);
        clawServoLeft.scaleRange(Constants.CLAW_MIN, Constants.CLAW_MAX);
        clawServoRight.scaleRange(Constants.CLAW_MIN, Constants.CLAW_MAX);
    }

    public void grab(){
        clawServoLeft.setPosition(1);
        clawServoRight.setPosition(0);
    }

    public void release(){
        clawServoLeft.setPosition(0);
        clawServoRight.setPosition(1);
    }
}
