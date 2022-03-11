// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake.IntakeSimCommands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.SimIntake;

public class IntakeReverse extends CommandBase {
  private final SimIntake mIntake;
  /** Creates a new IntakeForward. */
  public IntakeReverse(SimIntake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    mIntake = intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    mIntake.setSpeed(ControlMode.PercentOutput, -0.5, mIntake.m_armTalon);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    mIntake.setSpeed(ControlMode.PercentOutput, 0, mIntake.m_armTalon);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !ControlMap.GUNNER_Y.get();
  }
}
