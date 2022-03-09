// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Climb.SimLeftPiston;

public class MoveLeftSolenoid extends CommandBase {
  private final SimLeftPiston m_simPiston;
  /** Creates a new MoveSolenoid. */
  public MoveLeftSolenoid(SimLeftPiston piston) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_simPiston = piston;
    addRequirements(piston);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(ControlMap.m_x.get()){
      m_simPiston.setSolenoidValue(Value.kForward);
    }
    else if(ControlMap.m_c.get()){
      m_simPiston.setSolenoidValue(Value.kOff);
    }
    else if(ControlMap.m_v.get()){
      m_simPiston.setSolenoidValue(Value.kReverse);
    }
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
