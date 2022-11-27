package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ProgrammingBoard1 {
    private DigitalChannel touchSensor;

    public void init(HardwareMap hwMap){
        touchSensor = hwMap.get(DigitalChannel.class, "touch_sensor");
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
    }

    public boolean touchSensorPressed(){
        return !(touchSensor.getState());
    }

    public boolean touchSensorReleased(){
        return touchSensor.getState();
    }
}
