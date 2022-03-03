// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Climb;


public class PivotClimb extends CommandBase {
  /** Creates a new Pivot. */
  private final Climb m_pivot;
  public PivotClimb(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_pivot = climb;
    addRequirements(climb);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_pivot.ServoClose();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_pivot.pivot(ControlMap.gunner.getRawAxis(4));
    m_pivot.getBoreEncoder();
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
