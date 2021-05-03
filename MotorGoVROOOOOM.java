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
import java.util.Locale;

@TeleOp(name="VROOOM", group="Linear Opmode")

public class MotorGoVROOOOOM extends LinearOpMode {
    private DcMotor vroom = null;
    public Servo pusher = null;
    
    @Override
    public void runOpMode() {
        vroom  = hardwareMap.get(DcMotor.class, "green");
        pusher = hardwareMap.get(Servo.class, "shoot");
        vroom.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()) {
            vroom.setPower(1.0);
            
            for (int i = 0; i < 5; i++) {
                if (i == 5) {
                    pusher.setPosition(0);
                    try {Thread.sleep(250);} catch(InterruptedException ule) {}
                    pusher.setPosition(0);
                }
                try {Thread.sleep(1000);} catch(InterruptedException ule) {}
            }
        }
    }
}