package org.firstinspires.ftc.teamcode.mechanisms.beta;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DistanceSensors {
    DistanceSensor left;
    DistanceSensor right;

    public void init(HardwareMap hwMap){
        left = hwMap.get(DistanceSensor.class, "Distance_Left");
        right = hwMap.get(DistanceSensor.class, "Distance_Right");
    }
}
