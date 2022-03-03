// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class AutoOutake extends CommandBase {
  /** Creates a new AutoOutake. */
  Ultrasonic m_ultrasonicOutake = new Ultrasonic(Constants.ULTRASONIC_OUTAKE[0], Constants.ULTRASONIC_OUTAKE[1]);
  private final Intake m_intake;
  public AutoOutake(Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.BottomServoClose();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Ultrasonic.setAutomaticMode(true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.BottomServoOpen();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_ultrasonicOutake.getRangeInches() <= 2;
  }
}
