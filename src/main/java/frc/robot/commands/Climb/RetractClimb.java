// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class RetractClimb extends CommandBase {
  private final Climb m_climb;

  // Constructor
  public RetractClimb(Climb climb) {
    m_climb = climb;
    addRequirements(climb);
  }

  // Extend the pivot arm
  @Override
  public void initialize() {
    System.out.println("RetractClimb.initialize()");    
    m_climb.retractArm();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("RetractClimb.end() : " + interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
