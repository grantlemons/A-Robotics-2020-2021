package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Locale;

@TeleOp(name="Spin Motor", group="Linear Opmode")

public class SpinMotor extends LinearOpMode {
    private DcMotor motor = null;
    public Servo pusher = null;

    Dotenv dotenv = Dotenv.load();
    private String THROWER_MOTOR = dotenv.get("THROWER_MOTOR");
    private String PUSHER_SERVO = dotenv.get("PUSHER_SERVO");
    
    @Override
    public void runOpMode() {
        motor  = hardwareMap.get(DcMotor.class, THROWER_MOTOR);
        pusher = hardwareMap.get(Servo.class, PUSHER_SERVO);
        motor.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()) {
            motor.setPower(1.0);
        }
    }
}