// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.NewIntakeCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GetOutOfMySwamp;

public class OuttakeCargo extends CommandBase {
  private final GetOutOfMySwamp m_gGetOutOfMySwamp;
  /** Creates a new IntakeCargo. */
  public OuttakeCargo(GetOutOfMySwamp m_swamp) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_gGetOutOfMySwamp = m_swamp;
    addRequirements(m_swamp);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_gGetOutOfMySwamp.intakeOutake(-0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_gGetOutOfMySwamp.intakeOutake(0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(!m_gGetOutOfMySwamp.getDebouncerVals()){
      return true;
    }
    return false;
  }
}
