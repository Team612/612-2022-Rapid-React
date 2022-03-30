// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.PivotMotor;

public class PivotMoveToPosition extends CommandBase {
  /** Creates a new pivotMoveToPosition. */
  private final PivotMotor m_pivotMotor;
  private boolean isDestination = false;
  public PivotMoveToPosition(PivotMotor pivotMotor) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_pivotMotor = pivotMotor;
    addRequirements(pivotMotor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    isDestination = m_pivotMotor.moveToPosition(Constants.targetPivot, 5, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_pivotMotor.pivot(0);
  }

  // Returns true when the command should end.
  //
  @Override
  public boolean isFinished() {
    return isDestination;
  }
}
