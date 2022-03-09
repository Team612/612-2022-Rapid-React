// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Intake.SimIntakeArmMotor;

public class MoveIntakeTalon extends CommandBase {
  private final SimIntakeArmMotor m_pivot;
  /** Creates a new MoveTalon. */
  public MoveIntakeTalon(SimIntakeArmMotor pivot) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_pivot = pivot;
    addRequirements(pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_pivot.setSpeed(ControlMode.PercentOutput, ControlMap.m_control.getRawAxis(1));
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
