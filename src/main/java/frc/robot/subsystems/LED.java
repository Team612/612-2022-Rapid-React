// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LED extends SubsystemBase {
  public AddressableLED m_led;
  static AddressableLEDBuffer m_ledBuffer;
  private int m_rainbowFirstPixelHue;

  /** Creates a new LED. */
  public LED() {
    

  }
  

  public void ledInit() {
    m_led = new AddressableLED(Constants.LED_PORT);
    m_ledBuffer = new AddressableLEDBuffer(5460);

    //do it just once cause the setlength method sucks
    m_led.setLength(5460);

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

  public void extendArm(){
    for (int i = 0; i <= 255; i++) {

      final var hue = (i);
      m_led.setLength(150); //testing purposes here.
      m_ledBuffer.setRGB(i, (int) 0, hue, hue); //starts as black, as climb progresses arms turn blue
      m_led.setData(m_ledBuffer);
      m_led.start();
    }
  }

  public void retractArm(){
    for (int i = 255; i >= 0; i--){
      final var hue = (i);
      m_ledBuffer.setRGB(i, (int) 0, hue, hue); //starts at blue, then slowly progesses to black
      m_led.setData(m_ledBuffer);
      m_led.start();
    }
  }

  public void OpenServo(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
    m_ledBuffer.setRGB(i,237,255,0);
    m_led.setData(m_ledBuffer);
    m_led.start();
  }
}

  public void CloseServo(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
    m_ledBuffer.setRGB(0,255,0,0 );
    m_led.setData(m_ledBuffer);
    m_led.start();
  }
}


  

 

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

