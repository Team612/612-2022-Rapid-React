// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class AutoOuttake extends CommandBase {
  /** Creates a new AutoOuttake. */
  Intake m_intake;
  public AutoOuttake(Intake intake) {
    m_intake = intake;
    addRequirements(intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.BottomServoClose();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intake.setArm(-Constants.intakeArmSpeed);
    if(m_intake.getBoreEncoder() < Constants.upperIntakeLim){
      m_intake.BottomServoOpen();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_intake.upperLimitGoesOff() || m_intake.getBoreEncoder() <= .6){
      return true;
    }
    return false;
  }
}
