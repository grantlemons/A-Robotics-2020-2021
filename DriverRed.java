package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@TeleOp(name="DriverRed", group="Linear Opmode")

public class DriverRed extends LinearOpMode {
    @Override
    public void runOpMode() {
        //Init StoneTracker
        StoneTracker stoneTracker = new StoneTracker();
//        stoneTracker.init(hardwareMap);
//        stoneTracker.activate();

        Drivetrain drivetrain = new Drivetrain(hardwareMap, telemetry);
        Brain brain = new Brain(hardwareMap, drivetrain, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            //*******************
            // Drivetrain
            //*******************
            //Disable drivetrain with all buttons for when on table
            if(!gamepad1.right_bumper) {
                drivetrain.driveWithLimit(
                    -gamepad1.left_stick_y,
                    gamepad1.right_stick_x,
                    gamepad1.left_stick_x
                );
            }
            if (gamepad1.right_bumper) {
                drivetrain.driveWithLimit(
                    -gamepad1.left_stick_y*0.15,
                    gamepad1.right_stick_x*0.15,
                    gamepad1.left_stick_x*0.15
                );
            }

            while(gamepad1.x) {drivetrain.setHeading(180);}
            while(gamepad1.a) {drivetrain.setHeading(-90);} // +angle is left
            while(gamepad1.y) {drivetrain.setHeading(90);} // -angle is right
            while(gamepad1.b) {drivetrain.setHeading(0);}
        }
    }
}