// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class AutoOutake extends CommandBase {
  /** Creates a new AutoOutake. */
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
    System.out.println("Intake ultrasonic in Inches: " + m_intake.getUltrasonicOutakeInches());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.BottomServoOpen();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_intake.getUltrasonicOutakeInches() <= Constants.ULTRASONIC_OUTTAKE_THRESH;
  }
}