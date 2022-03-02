// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ToggleCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Drivetrain;

public class moveMecanumNormal extends CommandBase {
  private final Drivetrain m_drivetrain;
  /** Creates a new moveForward. */
  public moveMecanumNormal(Drivetrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = drive;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.driveMecanum(ControlMap.driver.getRawAxis(0), ControlMap.driver.getRawAxis(1), 
                              ControlMap.driver.getRawAxis(2));
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
