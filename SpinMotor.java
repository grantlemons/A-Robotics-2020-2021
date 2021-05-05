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
    Drivetrain drivetrain = new Drivetrain(hardwareMap, telemetry);
    BrainNew brain = new BrainNew(hardwareMap, drivetrain, telemetry);

    private DcMotor motor = null;
    public Servo pusher = null;

    // Dotenv dotenv = Dotenv.load();
    private String THROWER_MOTOR = "green";
    private String PUSHER_SERVO = "servo0";

    @Override
    public void runOpMode() {
        motor  = hardwareMap.get(DcMotor.class, THROWER_MOTOR);
        pusher = hardwareMap.get(Servo.class, PUSHER_SERVO);
        motor.setDirection(DcMotor.Direction.FORWARD);

        double targetAngle = 145;

        waitForStart();
        public ElapsedTime time = new ElapsedTime();
        while (opModeIsActive()) {
            motor.setPower(1.0);
            brain.sleep(5);
            pusher.setPosition(targetAngle/180);
        }
    }
}