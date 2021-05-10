package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.Locale;
import java.lang.System.getenv;

public class Shooter {
    public ElapsedTime runtime = new ElapsedTime();

    private DcMotor motor = null;
    public Servo pusher = null;
    HardwareMap hardwareMap = null;
    
    private String THROWER_MOTOR = System.getenv("THROWER_MOTOR");
    private String PUSHER_SERVO = System.getenv("PUSHER_SERVO");

    double targetAngle = 145;

    public Shooter ( HardwareMap hwareMap ) {
        hardwareMap = hwareMap;
        motor  = hardwareMap.get(DcMotor.class, THROWER_MOTOR);
        pusher = hardwareMap.get(Servo.class, PUSHER_SERVO);
        motor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void shoot() {
        runtime.reset();

        if ( runtime.time() < 1 ) {
            spinUp();
        }
        else if ( runtime.time() == 5 ) {
            push();
        }
        stopWheel();
    }

    public void spinUp() {
        motor.setPower(1.0);
    }

    public void stopWheel() {
        motor.setPower(0.0);
    }

    public void push() {
        pusher.setPosition(targetAngle/180);
    }
}