package org.firstinspires.ftc.teamcode.mechanisms.beta;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DistanceSensorRight extends DistanceSensors{
    public double returnDistance(DistanceUnit du){
        return right.getDistance(du);
    }
}
