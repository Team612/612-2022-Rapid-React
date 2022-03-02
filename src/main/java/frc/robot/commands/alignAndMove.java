// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class alignAndMove extends CommandBase {
  /** Creates a new alignAndMove. */
  private ProfiledPIDController rotationController = new ProfiledPIDController(Constants.PThetaController, 0, Constants.DThetaController, Constants.ThetaControllerConstraints);
  private ProfiledPIDController forwardController = new ProfiledPIDController(Constants.PXController, 0, Constants.DXController, Constants.PControllerConstraints);
  private double forwardSpeed = 0;
  private double rotationSpeed = 0;
  private final Drivetrain m_drive;
  public alignAndMove(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drivetrain;
    addRequirements(drivetrain);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var result = Constants.camera.getLatestResult();
      //System.out.println(result.hasTargets());
      if(result.hasTargets()){
        rotationSpeed = -rotationController.calculate(result.getBestTarget().getYaw(), 0);
        System.out.println(rotationSpeed);
        double range = (Constants.CAMERA_HEIGHT_METERS - Constants.TARGET_HEIGHT_METERS)/ 
        Math.tan(Constants.CAMERA_PITCH_RADIANS + Units.degreesToRadians(result.getBestTarget().getPitch()));
        forwardSpeed = forwardController.calculate(range, Constants.GOAL_RANGE_METERS);
        System.out.println(forwardSpeed);
      }
      else{
        rotationSpeed = 0;
        forwardSpeed = 0;
      }
    m_drive.driveMecanum(forwardSpeed, 0, rotationSpeed);
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
