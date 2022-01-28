// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.subsystems.Climb;
import frc.robot.controls.ControlMap;
import edu.wpi.first.wpilibj.DigitalInput;

public class ServoOpen extends CommandBase {
  /** Creates a new Pivot. */
  private final Climb m_servoOpen;
    // Use addRequirements() here to declare subsystem dependencies.
    //Constructor
    public ServoOpen(Climb ServoOpen){
        m_servoOpen = ServoOpen;
        addRequirements(ServoOpen);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      m_servoOpen.ServoOpen();
      

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

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
