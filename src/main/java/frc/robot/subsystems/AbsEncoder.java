// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AbsEncoder extends SubsystemBase {

  DutyCycleEncoder boreEncoder;

  /** Creates a new AbsEncoder. */
  public AbsEncoder() {

    //initialize encoder on DIO port
    boreEncoder = new DutyCycleEncoder(Constants.BORE_ENCODER);

  }

  public DutyCycleEncoder getBoreEncoder() {
    return boreEncoder;
  }

  @Override
  public void periodic() {


    // This method will be called once per scheduler run
  }
}
