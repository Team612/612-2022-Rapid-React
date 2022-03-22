// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class climbLogicThingy extends CommandBase {
  private final Intake m_intake;
  private final double moveDistance;
  double conversionFactor = 0;
  /** Creates a new climbLogicThingy. */
  public climbLogicThingy(Intake intake, double move) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    moveDistance = move;
    addRequirements(m_intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(moveDistance > m_intake.getBoreEncoder()){
      conversionFactor = 1;
    }
    else{
      conversionFactor = -1;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intake.setShuffleBoard(m_intake.getBoreEncoder());
    m_intake.TalonFlex(conversionFactor * Constants.intakeArmSpeed);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.TalonFlex(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_intake.getBoreEncoder() == moveDistance){
      return true;
    }
    return false;
  }
}
