package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import java.util.Locale;

@TeleOp(name="Servo To Angle", group="Linear Opmode")

public class ServoToAngle extends LinearOpMode {
    public Servo pusher = null;
    private String PUSHER_SERVO = "servo0";

    @Override
    public void runOpMode() {
        pusher = hardwareMap.get(Servo.class, PUSHER_SERVO);
        double targetAngle = 145;
        waitForStart();
        while (opModeIsActive()) {
            pusher.setPosition(targetAngle/180);
        }
    }
}