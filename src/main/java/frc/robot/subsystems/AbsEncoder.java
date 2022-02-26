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
    With the rev logo facing towards the user:
    clockwise is negative, counterclockwise is positive
    distance of 1 indicates one full rotation. there is no upper bound
    (i.e. if there's more than one rotation, the distance will return a value greater than 1)
    therefore, should take mod of distance and 1.0
    when rio is reset, it'll remove the whole number and do just the decimal

    negative acts weird: if it's at -1.56 and resets, it'll return to 0.4.
    */
    System.out.println("encoder freq: " + boreEncoder.getFrequency());
  }

  @Override
  public void periodic() {


    // This method will be called once per scheduler run
  }
}
