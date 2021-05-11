package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous

public class AutoBoth extends LinearOpMode {
    public void runOpMode() {
        Config config = new Config();
        Drivetrain drivetrain = new Drivetrain(hardwareMap, telemetry);
        UltimateGoalWebcam goal = new UltimateGoalWebcam(hardwareMap);
        BrainNew brain = new BrainNew(hardwareMap, drivetrain, telemetry);
        Shooter shooter = new Shooter(hardwareMap);
        telemetry.addData("Ready", "Yes");
        telemetry.update();
        waitForStart();
        boolean run = true;
        while (opModeIsActive()) {
            if (run) {
                double[] coordinates = goal.getCoordinates();
                double X = coordinates[0];
                double Y = coordinates[1];

                drivetrain.forwardToColorStop(config.SIDE, -0.5, 0);
                drivetrain.forwardDistance(-6, 0.5);

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