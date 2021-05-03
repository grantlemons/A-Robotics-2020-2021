package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
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
import java.util.List;
import java.util.Locale;

@TeleOp(name="DriveB", group="Linear Opmode")

public class Drive extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightFront = null;
    private DcMotor rightBack = null;
    private DcMotor slideMotor = null;
    private DcMotor liftMotor = null;
    BNO055IMU imu;
    Orientation angles;
    double drive;
    double turn;
    double strafe;
    double leftback;
    double rightfront;
    double leftfront;
    double rightback;
    double liftPower, slidePower;
    boolean rightStickStrafe = true;
    boolean stopped = false;
    
    public void hardwareInit() {
        leftFront  = hardwareMap.get(DcMotor.class, "purple");
        rightFront = hardwareMap.get(DcMotor.class, "black");
        leftBack = hardwareMap.get(DcMotor.class, "red");
        rightBack = hardwareMap.get(DcMotor.class, "orange");
        slideMotor = hardwareMap.get(DcMotor.class, "blue");
        liftMotor = hardwareMap.get(DcMotor.class, "green");
        
        imu = hardwareMap.get(BNO055IMU.class, "gyro");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
    }
    @Override
    public void runOpMode() {
        hardwareInit();
        telemetry.addData("Status:", "Initialized");
        telemetry.update();
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            double claw = gamepad2.right_trigger - gamepad2.left_trigger;
            drive();
            grabber();
            powerToMotors();
            runTelemetry();
        }
        if (tfod != null) {
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                      telemetry.addData("# Object Detected", updatedRecognitions.size());
                      int i = 0;
                      for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        strafe = recognition.estimateAngleToObject(AngleUnit.DEGREES) * 0.25;
                        drive = recognition.getHeight() * 0.03;
                        telemetry.addData("","Drivetrain (%f) (%f) (%f)",strafe,drive,recognition.estimateAngleToObject(AngleUnit.DEGREES));
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                      }
                      telemetry.update();
                    }
                }

              
        telemetry.addData("","Before InitVuforia");
        telemetry.update();
        initVuforia();
        telemetry.addData("","After InitVuforia");
        telemetry.update();
// not in new library       if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
//        } else {
//            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
//        }
        telemetry.addData("","after initTfod");
        telemetry.update();

        if (tfod != null) {
            tfod.activate();
        }
        telemetry.addData("","");

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (tfod != null) {
            tfod.shutdown();
        }
    }
    
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "webcam");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
            "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minimumConfidence = 0.4;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    public void runTelemetry() {
        telemetry.addData("Run Time:", runtime.toString());
        telemetry.addData("Motors:","RB:(%.2f) LB:(%.2f) RF:(%.2f) LF:(%.2f) D:(%.2f) T:(%.2f) S:(%.2f)", rightback,leftback,rightfront,leftfront,drive,turn,strafe);
        telemetry.addData("Heading: ", "(%.2f)", AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle));

        telemetry.addData("Path0",  "Motors at %7d :%7d",
                          slideMotor.getCurrentPosition(),
                          liftMotor.getCurrentPosition());

        telemetry.update();
    }
    public void powerToMotors() {
        if (!stopped) {
            leftfront = drive + turn + strafe;
            rightfront = drive - turn - strafe;
            leftback = drive + turn - strafe;
            rightback = drive - turn + strafe;
            leftBack.setPower(leftback);
            rightFront.setPower(rightfront);
            leftFront.setPower(leftfront);
            rightBack.setPower(rightback);
            slideMotor.setPower(slidePower);
            liftMotor.setPower(liftPower);
        }
    }

    public void drive() {
        if(rightStickStrafe) {
            //Drive Normally
            if (!gamepad1.left_bumper && !gamepad1.x && !stopped) {
                drive = -gamepad1.left_stick_y;
                strafe  = gamepad1.right_stick_x;
                turn = gamepad1.left_stick_x;
            }
        } else {
            //Drive Normally
            if (!gamepad1.left_bumper && !gamepad1.x && !stopped) {
                drive = -gamepad1.left_stick_y;
                strafe  = gamepad1.left_stick_x;
                turn = gamepad1.right_stick_x;
            }
        }
    }
    
    private void grabber() {
        liftPower = gamepad2.left_stick_y;
        slidePower = -gamepad2.right_stick_y;
    }
    
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private static final String VUFORIA_KEY =
            "AYME2p3/////AAABmeVjQ60ipUjutO8p+A5CWZgB9Kpp3Sm0nTmkXigsOPptZ5kOtQO3KMhDVl+dbGxPtlPm7RCZMPj6Vu1DnCA01y4cz9S6Bh5m5jEecvtvw6c11JFf3jFr63uqQkPEemN8sjJJmFeMgu9PyxAREcPwn86rpRhYrAq7m3RtuT+UjzVOt9fZsp33URsKgsgraY932jDOa033slaKf2sh829y23jyMmPTC1yxU+fxDsDoePByS9AhiJG+c1WWF/w8VS94ORuIXbqc+nBcgGYpLXtFYLZLAPTyNkCgWWtVMDvoFV/SD8v3C+/cpz4+uIjzfqtqimyZlb8OpO/xv/kvXTnHZo0AXGR4tZNvAleetU4M9VIf";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;
}