package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import java.util.Locale;

@TeleOp(name="Spin Motor", group="Linear Opmode")

public class SpinMotor extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();

    private DcMotor motor = null;
    public Servo pusher = null;
    Config config = new Config();

    @Override
    public void runOpMode() {
        motor  = hardwareMap.get(DcMotor.class, config.THROWER_MOTOR);
        pusher = hardwareMap.get(Servo.class, config.PUSHER_SERVO);
        motor.setDirection(DcMotor.Direction.FORWARD);

        double targetAngle = 145;

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

            if ( runtime.time() < 5 ) {
                motor.setPower(1.0);
            }
            else if ( runtime.time() > 5 ) {
                pusher.setPosition(targetAngle/180);
            }

        }
    }
}