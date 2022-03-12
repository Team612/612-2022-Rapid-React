// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.PivotMotor;

public class PivotPistonsSeperate extends CommandBase {
  /** Creates a new PivotPistonsSeperate. */
  private final PivotMotor m_pivot;
  private final Climb m_climb;
  public PivotPistonsSeperate(PivotMotor pivot, Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_pivot = pivot;
    m_climb = climb;
    addRequirements(pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("PivotPistonsSeperate.initialize()");    
    m_climb.servoOpen();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_pivot.pivot(-ControlMap.gunner.getRawAxis(5));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("PivotPistonsSeperate.end() : " + interrupted);    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
