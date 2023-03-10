package org.firstinspires.ftc.teamcode.mechanisms.beta;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensorLeft extends DistanceSensors{
    public double returnDistance(DistanceUnit du){
        return left.getDistance(du);
    }
}
