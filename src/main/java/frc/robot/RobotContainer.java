// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Trajectories.TrajectoryCreation;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.FollowTrajectory;
import frc.robot.commands.alignAndMove;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();
  public static Drivetrain m_drivetrain = new Drivetrain();
  private final DefaultDrive m_defaultdrive = new DefaultDrive(m_drivetrain);
  private final FollowTrajectory m_follower = new FollowTrajectory();
  private final TrajectoryCreation m_trajectory = new TrajectoryCreation();
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureDefaultCommands();
  }

  private void configureButtonBindings() {
    m_chooser.addOption("Mecanum Trajectory", m_follower.generateTrajectory(m_drivetrain, m_trajectory.testTrajectory));
    m_chooser.addOption("Bill", m_follower.generateTrajectory(m_drivetrain, m_trajectory.testTrajectory2));
    // m_chooser.addOption("New New Path", m_follower.generateTrajectory(m_drivetrain, m_trajectory.examplePath));
    m_chooser.addOption("ur mom", new alignAndMove(m_drivetrain));
    //m_chooser.addOption("align and move towards", m_follower.generateTrajectory(m_drivetrain, m_trajectory.alignTrajectory));
    SmartDashboard.putData(m_chooser);
  }

  private void configureDefaultCommands() {
    m_drivetrain.setDefaultCommand(m_defaultdrive);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_chooser.getSelected();
  }
}
