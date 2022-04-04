// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class BottomClose extends CommandBase {
  /** Creates a new TopClose. */
  private final Intake m_intake;
  public BottomClose(Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
 
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_intake.getInputState()){
      System.out.println("inside auto close");
      if(m_intake.getButtonVal() && m_intake.getBoreEncoder() > 0.93){ //why are we only closing here again????
        m_intake.BottomServoClose();
        System.out.println("auto close");
      }
    }
    else{
      m_intake.BottomServoClose();
      System.out.println("manual close");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("bottom close?: " + !m_intake.isServoOpen());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !m_intake.isServoOpen();
  }
}
