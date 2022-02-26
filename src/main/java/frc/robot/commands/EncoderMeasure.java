// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.AbsEncoder;

public class EncoderMeasure extends CommandBase {
  //subsystem data field
  private final AbsEncoder m_encoder;


  /** Creates a new EncoderMeasure. */
  public EncoderMeasure(AbsEncoder m_encoder) {
    this.m_encoder = m_encoder;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_encoder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    System.out.println("Distance traveled: " + m_encoder.getBoreEncoder().getPositionOffset());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
