// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.controls.ControlMap;
import edu.wpi.first.wpilibj.DigitalInput;

public class Pivot extends CommandBase {
  /** Creates a new Pivot. */
  private final Climb m_pivot;
  private double mValue;
  DigitalInput motorLimit = new DigitalInput(0);
    // Use addRequirements() here to declare subsystem dependencies.
    //Constructor
    public Pivot(Climb pivot){
        m_pivot = pivot;
        addRequirements(pivot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //mValue = ControlMap.gunner.getRawAxis(4);
    m_pivot.pivot();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(motorLimit.get()){
      return true;
    }
    return false;
  }
}
