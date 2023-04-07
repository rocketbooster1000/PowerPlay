package org.firstinspires.ftc.teamcode.mechanisms.beta;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.PIDController;

@Config
public class Actuator {
    DcMotorEx motor;
    PIDController controller;

    public static double kP = 0;
    public static double kI = 0;
    public static double kD = 0;

    private double setPoint;

    public void init(HardwareMap hwMap){
        motor = hwMap.get(DcMotorEx.class, "PID_Motor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        controller = new PIDController(kP, kI, kD);
        setPoint = 0;
    }

    public void update(){
        controller.setPID(kP, kI, kD);
        double power = controller.calculate(motor.getCurrentPosition(), setPoint);
        motor.setPower(power);
    }

    public void setTarget(double setPoint){
        this.setPoint = setPoint;
    }

}
