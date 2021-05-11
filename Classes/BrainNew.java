package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import android.graphics.Color;


public class BrainNew {
    private NormalizedColorSensor colorSensor = null;
    Drivetrain drivetrain;
    Telemetry telemetry;
    LinearOpMode opMode;
    ElapsedTime timer = new ElapsedTime();
    
    
    public BrainNew(HardwareMap hardwareMap, Drivetrain aDrivetrain, Telemetry aTelemetry) {
        drivetrain = aDrivetrain;
        telemetry = aTelemetry;
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
    }
    
    void goToPoint (double x, double y, double target_x, double target_y, String direction) {
        int difference_x = (int)target_x - (int)x;
        int difference_y = (int)target_y - (int)y;
        switch (direction) {
            case "rw":
                drivetrain.strafeDistance( difference_x, 1 );
                drivetrain.forwardDistance( -difference_y, 1 );
                break;
            case "bw":
                drivetrain.strafeDistance( -difference_x, 1 );
                drivetrain.forwardDistance( difference_y, 1 );
                break;
            case "b":
                drivetrain.forwardDistance( -difference_x, 1 );
                drivetrain.strafeDistance( -difference_y, 1 );
                break;
            case "g":
                drivetrain.forwardDistance( difference_x, 1 );
                drivetrain.strafeDistance( difference_y, 1 );
                break;
            default:
                break;
        }
    }
}