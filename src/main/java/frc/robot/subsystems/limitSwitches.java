// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class limitSwitches extends SubsystemBase {
  /** Creates a new limitSwitches. */

  // DigitalInput inOutBottomSwitch = new DigitalInput(Constants.INOUT_BOTTOM_LIMIT);
  // DigitalInput inOutTopSwitch = new DigitalInput(Constants.INOUT_TOP_LIMIT);

  boolean isForward;
  boolean bottomPressed;
  boolean topPressed;

  public limitSwitches(boolean isForward, boolean bottomPressed, boolean topPressed) {

    this.isForward = isForward;
    this.bottomPressed = bottomPressed;
    this.topPressed = topPressed;

  }

  public void limits() {
  
    if (bottomPressed) {
      System.out.println("has reached bottom");
    }
    if (topPressed) {
      System.out.println("has reached top");
    }
    
  } 

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
