// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.RollerIntake;

public class NewWheelIntake extends CommandBase {
  private final RollerIntake m_rollerintake;
  /** Creates a new IntakeCargo. */
  public NewWheelIntake(RollerIntake roller) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_rollerintake = roller;
    addRequirements(roller);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_rollerintake.intakeOutake(0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_rollerintake.intakeOutake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_rollerintake.getDebouncerVals()){
      return true;
    }
    return false;
  }
}