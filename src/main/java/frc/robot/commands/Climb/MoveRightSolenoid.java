// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Climb.SimRightPiston;

public class MoveRightSolenoid extends CommandBase {
  private final SimRightPiston m_piston;
  /** Creates a new MoveRightSolenoid. */
  public MoveRightSolenoid(SimRightPiston pist) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_piston = pist;
    addRequirements(pist);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(ControlMap.m_f.get()){
      m_piston.setSolenoidValue(Value.kForward);
    }
    else if(ControlMap.m_g.get()){
      m_piston.setSolenoidValue(Value.kOff);
    }
    else if(ControlMap.m_h.get()){
      m_piston.setSolenoidValue(Value.kReverse);
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
