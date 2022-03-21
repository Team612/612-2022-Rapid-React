// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.PivotMotor;

public class climbLogicThingy extends CommandBase {
  private final Climb m_climb;
  private final PivotMotor m_pivot;
  private final double moveDistance;
  double conversionFactor = 0;
  /** Creates a new climbLogicThingy. */
  public climbLogicThingy(Climb climb, PivotMotor pivot, double move) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    m_pivot = pivot;
    moveDistance = move;
    addRequirements(m_climb, m_pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(moveDistance > m_climb.getBoreEncoder()){
      conversionFactor = 1;
    }
    else{
      conversionFactor = -1;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    m_pivot.pivot(conversionFactor * 0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_pivot.pivot(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_climb.getBoreEncoder() == moveDistance){
      return true;
    }
    return false;
  }
}
