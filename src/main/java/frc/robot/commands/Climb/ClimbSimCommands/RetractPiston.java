// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb.ClimbSimCommands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SimClimb;

public class RetractPiston extends CommandBase {
  private final SimClimb m_climb;
  /** Creates a new RetractPiston. */
  public RetractPiston(SimClimb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climb.setSolenoidValue(Value.kReverse, m_climb.m_leftSolenoid);
    m_climb.setSolenoidValue(Value.kReverse, m_climb.m_rightSolenoid);
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
