package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Camera;

@Autonomous(name = "Signal Sleeve but external")
public class VisionExternalOp extends OpMode {
    Camera camera = new Camera();

    @Override
    public void init(){
        camera.init(hardwareMap);
    }

    public void loop(){
        telemetry.addData("Zone int: ", camera.returnZone());
        telemetry.addData("Zone enum: ", camera.returnZoneEnumerated());
    }
}
