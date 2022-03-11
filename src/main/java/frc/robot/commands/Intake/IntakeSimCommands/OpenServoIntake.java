// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake.IntakeSimCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SimIntake;

public class OpenServoIntake extends CommandBase {
  private final SimIntake m_intake;
  /** Creates a new CloseServoIntake. */
  public OpenServoIntake(SimIntake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.setServoAngle(90, m_intake.m_leftServoClaw);
    m_intake.setServoAngle(90, m_intake.m_rightServoClaw);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
