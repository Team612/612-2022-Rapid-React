// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RollerIntake;

public class AutoOuttake extends CommandBase {
  /** Creates a new AutoOuttake. */
  private final RollerIntake m_roller;
  private double initialTime;
  public AutoOuttake(RollerIntake roller) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_roller = roller;
    addRequirements(roller);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initialTime = WPIUtilJNI.now() * 1.0e-6;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_roller.intakeOutake(-0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_roller.intakeOutake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(WPIUtilJNI.now() * 1.0e-6 - initialTime >= 3.0) return true;
    return false;
  }
}
