package org.firstinspires.ftc.teamcode.opmodes.hardwaretests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.beta.Actuator;

@Config
@TeleOp
public class PIDTest extends OpMode{
    public static double target = 0;
    Actuator actuator = new Actuator();

    @Override
    public void init(){
        actuator.init(hardwareMap);
    }

    @Override
    public void loop(){
        actuator.setTarget(target);
        actuator.update();
    }
}
