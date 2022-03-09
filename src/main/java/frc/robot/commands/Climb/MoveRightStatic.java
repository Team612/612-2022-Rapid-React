// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Climb.SimRightServoClimb;

import java.util.Random;

public class MoveRightStatic extends CommandBase {
  public int max = 180;
  public int min = 1;
  private final SimRightServoClimb m_staticLeft;
  /** Creates a new MoveLeftStatic. */
  public MoveRightStatic(SimRightServoClimb m_climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_staticLeft = m_climb;
    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Random num = new Random();
    int output = num.nextInt(max);
    output += min;
    if(ControlMap.m_t.get()){
      m_staticLeft.setServoAngle(output);
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
