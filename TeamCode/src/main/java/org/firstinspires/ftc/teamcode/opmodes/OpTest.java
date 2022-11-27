package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.mechanisms.ProgrammingBoard1;

@TeleOp
public class OpTest extends OpMode {
    ProgrammingBoard1 board = new ProgrammingBoard1();
    @Override
    public void init(){
        board.init(hardwareMap);
    }
    @Override
    public void loop(){
        String touchOrNoTouch = board.touchSensorPressed() ? "Pressed" : "Not Pressed";
        telemetry.addData("Touch Sensor ", touchOrNoTouch);
    }
}
