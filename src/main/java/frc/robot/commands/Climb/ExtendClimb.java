// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class ExtendClimb extends CommandBase {
  private final Climb m_climb;

  //Constructor
  public ExtendClimb(Climb climb){
    m_climb = climb;
    addRequirements(climb);
  }
  //Extend the pivot arm
  @Override
  public void initialize(){
    System.out.println("ExtendClimb.initialize()");    
    m_climb.extendArm();
  }
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("ExtendClimb.end() : " + interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
