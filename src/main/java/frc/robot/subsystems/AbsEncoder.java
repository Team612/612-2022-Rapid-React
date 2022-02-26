// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AbsEncoder extends SubsystemBase {

  DutyCycleEncoder boreEncoder = new DutyCycleEncoder(Constants.BORE_ENCODER);
  


  /** Creates a new AbsEncoder. */
  public AbsEncoder() {

    //initialize encoder on DIO port

  }

  public void getBoreEncoder() {
    System.out.println("encoder dist: " + boreEncoder.getDistance());
    /*
    In 
    */
    System.out.println("encoder freq: " + boreEncoder.getFrequency());
  }

  @Override
  public void periodic() {


    // This method will be called once per scheduler run
  }
}
