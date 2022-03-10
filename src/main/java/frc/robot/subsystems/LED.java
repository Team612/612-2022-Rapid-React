// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LED extends SubsystemBase {

  AddressableLED m_led;
  static AddressableLEDBuffer m_ledBuffer;
  private int m_rainbowFirstPixelHue;

  /** Creates a new LED. */
  public LED() {

  }

  public void ledInit() {
    m_led = new AddressableLED(Constants.LED_PORT);
    m_ledBuffer = new AddressableLEDBuffer(60);

    //do it just once cause the setlength method sucks
    m_led.setLength(m_ledBuffer.getLength());

    //set data
    m_led.setData(m_ledBuffer);
    m_led.start();
  }

  public void setLED(int RGB_R, int RGB_G, int RGB_B) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, RGB_R, RGB_G, RGB_B);
   }


   
   m_led.setData(m_ledBuffer);
  }

  public static void extendArm(){
    for (int i = 0; i <= 255; i++) {

      final var hue = (i);
      m_ledBuffer.setHSV(0, (int) 0, hue, hue); //starts as black, as climb progresses arms turn blue
    }
  }

  public static void retractArm(){
    for (int i = 255; i >= 0; i--){
      final var hue = (i);
      m_ledBuffer.setHSV(0, (int) 0, hue, hue); //starts at blue, then slowly progesses to black
    }
  }

  public static void OpenServo(){
    m_ledBuffer.setHSV(0,237,255,0);
  }

  public static void CloseServo(){
    m_ledBuffer.setHSV(0,255,0,0 );
  }


  

    private void rainbow() {

    // For every pixel
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;

      // Set the value
      m_ledBuffer.setHSV(i, (int) hue, 255, 128);
    }

    // Increase by to make the rainbow "move"
    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;



  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    rainbow();
    m_led.setData(m_ledBuffer);
  }
}
