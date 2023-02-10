package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.android.dx.dex.file.ValueEncoder;

public class ProgrammingBoard1 {
    private DigitalChannel touchSensor;
    private DcMotor motor;
    private double ticksPerRevolution;
    private Servo servo;
    private ValueEncoder encoder;

    public void init(HardwareMap hwMap){
        touchSensor = hwMap.get(DigitalChannel.class, "touch_sensor");
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
        motor = hwMap.get(DcMotor.class, "motor");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        ticksPerRevolution = motor.getMotorType().getTicksPerRev();
        servo = hwMap.get(Servo.class, "servo");
        servo.setDirection(Servo.Direction.FORWARD);
        encoder = hwMap.get(ValueEncoder.class, "Encoder");
    }

    public boolean touchSensorPressed(){
        return !(touchSensor.getState());
    }

    public boolean touchSensorReleased(){
        return touchSensor.getState();
    }

    public void setMotorSpeed(double speed){
        motor.setPower(speed);
    }

    public double getMotorRevlution(){
        return motor.getCurrentPosition() / ticksPerRevolution;
    }

    public void ZeroBehavior(boolean brake, boolean FLOAT){
        if (brake){
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } else if (FLOAT) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }
    public int returnSquare(int in){
        return (in * in);
    }
    public void setServoPos(double pos){
        servo.setPosition(pos);
    }
    public void servoDF(){
        servo.setDirection(Servo.Direction.FORWARD);
    }
    public void servoDR(){
        servo.setDirection(Servo.Direction.REVERSE);
    }
}
