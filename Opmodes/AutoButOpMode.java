package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
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
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.util.Locale;

@TeleOp(name = "AutoButOpMode", group = "Sensor")

public class AutoButOpMode extends LinearOpMode {
    public void runOpMode() {
        Config config = new Config();
        Drivetrain drivetrain = new Drivetrain(hardwareMap, telemetry, opModeIsActive());
        UltimateGoalWebcam goal = new UltimateGoalWebcam(hardwareMap, telemetry, opModeIsActive());
        BrainNew brain = new BrainNew(hardwareMap, drivetrain, telemetry, opModeIsActive());
        Shooter shooter = new Shooter(hardwareMap);
        telemetry.addData("Ready", "Yes");
        telemetry.update();
        waitForStart();
        boolean run = true;
        while (opModeIsActive()) {
            if (run) {
                goal.getCoordinates();
                double X = goal.getX();
                double Y = goal.getY();

                drivetrain.forwardToColorStop(config.SIDE, -0.5, 0);
                drivetrain.forwardDistance(-6, 0.5);

                telemetry.addData("Pos (in)", "{X, Y} = %.1f, %.1f", X, Y);
                telemetry.update();

                switch (config.SIDE) {
                    case "blue":
                        brain.goToPoint(X, Y, 2, 39, "g");
                        break;
                    case "red":
                        brain.goToPoint(X, Y, 2, -39, "g");
                        break;
                }
                shooter.shoot();
                run = false;
            }
        }
    }
}