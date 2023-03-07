package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Constants;
import org.firstinspires.ftc.teamcode.mechanisms.DriveTrain;

@TeleOp(name = "Mecanum")
public class MecanumOp extends OpMode {
    DriveTrain board = new DriveTrain();
    boolean yAlreadyPressed;

    @Override
    public void init(){
        board.init(hardwareMap);
        yAlreadyPressed = false;
        telemetry.addData("Initialization ", "Complete");
    }

    @Override
    public void start(){
        board.resetYaw();
    }

    @Override
    public void loop(){
        board.drive(
                -gamepad1.right_stick_x,
                -gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                board.getHeadingDeg(),
                Constants.DRIVE_POWER_MODIFIER
        );
        telemetry.addData("Theoretical Heading: ", board.getHeadingDeg());
        if (gamepad1.y && !yAlreadyPressed){
            board.resetYaw();
        }

    }
}
