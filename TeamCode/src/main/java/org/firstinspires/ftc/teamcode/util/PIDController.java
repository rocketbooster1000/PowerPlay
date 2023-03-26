package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PIDController {
    private double kP;
    private double kI;
    private double kD;

    private ElapsedTime timer;

    private double totalError;
    private double dt;
    private double lastTime;
    private double lastError;
    private double reference;
    private double lastTarget;
    private double lastEstimate;

    public PIDController(double kP, double kI, double kD){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        totalError = 0;
        dt = 0;
        timer = new ElapsedTime();
    }

    public void setPID(double kP, double kI, double kD){
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        timer.reset();
        lastTime = 0;
    }

    public double calculate(double estimate, double reference){
        this.reference = reference;


        double value = kP * pController(estimate, this.reference) + kI * iController(estimate, this.reference) - kD * dController(estimate, this.reference);
        lastTarget = this.reference;
        lastError = reference - estimate;
        lastEstimate = estimate;

        return value;

    }

    public double pController(double estimate, double target){
        return (target - estimate);
    }

    public double iController(double estimate, double target){
        return totalError += ((target - estimate) * timer.time());
    }

    public double dController(double estimate, double target){
        if (lastTarget != target){
            return (((target - estimate) - (target - lastEstimate)) / timer.time());
        }
        return (((target - estimate) - lastError) / timer.time());
    }
}
