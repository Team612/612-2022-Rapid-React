// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoDelay extends CommandBase {
  private final double delay;
  /** Creates a new AutoDelay. */
  public AutoDelay(double t) {
    // Use addRequirements() here to declare subsystem dependencies.
    delay = t;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Timer.delay(delay);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
