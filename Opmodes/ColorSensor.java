package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@TeleOp(name = "ColorSensor", group = "Linear Opmode")

public class ColorSensor extends LinearOpMode {
  /** The colorSensor field will contain a reference to our color sensor hardware object */
  NormalizedColorSensor colorSensor;
  /** The relativeLayout field is used to aid in providing interesting visual feedback
   * in this sample application; you probably *don't* need something analogous when you
   * use a color sensor on your robot */
  View relativeLayout;

   public void runOpMode() throws InterruptedException {

    // Get a reference to the RelativeLayout so we can later change the background
    // color of the Robot Controller app to match the hue detected by the RGB sensor.
    int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
    relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

    try {
      runSample(); // actually execute the sample
    } finally {
      // Set the panel back to the default color
      relativeLayout.post(new Runnable() {
        public void run() {
          relativeLayout.setBackgroundColor(Color.WHITE);
        }
      });
      }
  }

  protected void runSample() throws InterruptedException {

    // values is a reference to the hsvValues array.
    float[] hsvValues = new float[3];
    final float values[] = hsvValues;

    // bPrevState and bCurrState keep track of the previous and current state of the button
    boolean bPrevState = false;
    boolean bCurrState = false;

    // Get a reference to our sensor object.
    colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");

    // If possible, turn the light on in the beginning (it might already be on anyway,
    // we just make sure it is if we can).
    if (colorSensor instanceof SwitchableLight) {
      ((SwitchableLight)colorSensor).enableLight(true);
    }

    // Wait for the start button to be pressed.
    waitForStart();

    // Loop until we are asked to stop
    while (opModeIsActive()) {
      // Check the status of the x button on the gamepad
      bCurrState = gamepad1.x;

      // If the button state is different than what it was, then act
      if (bCurrState != bPrevState) {
        // If the button is (now) down, then toggle the light
        if (bCurrState) {
          if (colorSensor instanceof SwitchableLight) {
            SwitchableLight light = (SwitchableLight)colorSensor;
            light.enableLight(!light.isLightOn());
          }
        }
      }
      bPrevState = bCurrState;

      // Read the sensor
      NormalizedRGBA colors = colorSensor.getNormalizedColors();

      //Color.colorToHSV(colors.toColor(), hsvValues);
      telemetry.addLine()
              .addData("H", "%.3f", hsvValues[0])
              .addData("S", "%.3f", hsvValues[1])
              .addData("V", "%.3f", hsvValues[2]);
      telemetry.addLine()
              .addData("a", "%.5f", colors.alpha)
              .addData("r", "%.5f", colors.red)
              .addData("g", "%.5f", colors.green)
              .addData("b", "%.5f", colors.blue);

      /** We also display a conversion of the colors to an equivalent Android color integer.
       * @see Color */
      int color = colors.toColor();
      telemetry.addLine("raw Android color: ")
              .addData("a", "%02x", Color.alpha(color))
              .addData("r", "%02x", Color.red(color))
              .addData("g", "%02x", Color.green(color))
              .addData("b", "%02x", Color.blue(color));

      if (colors.red > colors.blue*2) {
          telemetry.addData("color", "red");
      }
      if (colors.blue > colors.red*2.5) {
          telemetry.addData("color", "blue");
      }

      float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);
      colors.red   /= max;
      colors.green /= max;
      colors.blue  /= max;
      color = colors.toColor();

      telemetry.addLine("normalized color:  ")
              .addData("a", "%02x", Color.alpha(color))
              .addData("r", "%02x", Color.red(color))
              .addData("g", "%02x", Color.green(color))
              .addData("b", "%02x", Color.blue(color));
      telemetry.update();

      // to the HSVToColor method.
      relativeLayout.post(new Runnable() {
        public void run() {
          relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
        }
      });
    }
  }
}
