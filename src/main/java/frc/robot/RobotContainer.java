// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Trajectories.TrajectoryCreation;
import frc.robot.commands.*;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

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
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final DefaultDrive m_defaultdrive = new DefaultDrive(m_drivetrain);
  private final FollowTrajectory m_follower = new FollowTrajectory();

  private final TrajectoryCreation m_trajectory = new TrajectoryCreation();

  private final Climb m_climb = new Climb();
  private final Intake m_intake = new Intake();
  private final ExtendClimb m_extend = new ExtendClimb(m_climb);
  private final RetractClimb m_retract = new RetractClimb(m_climb);
  private final HookOff m_hookOff = new HookOff(m_climb);
  private final HookOn m_hookOn = new HookOn(m_climb);
  private final Pivot m_pivot = new Pivot(m_climb);
  private final TopClose m_topclose = new TopClose(m_intake);
  private final TopOpen m_topopen = new TopOpen(m_intake);
  private final BottomClose m_bottomclose = new BottomClose(m_intake);
  private final BottomOpen m_bottomopen = new BottomOpen(m_intake);
  private final RotateWristDown m_rotatewristdown = new RotateWristDown(m_intake);
  private final RotateWristUp m_rotatewristup = new RotateWristUp(m_intake);
  private final Arm m_arm = new Arm(m_intake);
  private final StartEndCommand m_start_end_top_servo = new StartEndCommand(m_intake::TopServoClose, m_intake::TopServoOpen, m_intake);
  private final StartEndCommand m_start_end_bottom_servo = new StartEndCommand(m_intake::BottomServoClose, m_intake:: BottomServoOpen, m_intake);
  private final StartEndCommand m_start_end_wrist_servo = new StartEndCommand(m_intake::WristClose, m_intake::WristOpen, m_intake);


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
    //m_chooser.addOption("Manual Code", object);
    SmartDashboard.putData(m_chooser);

    ControlMap.climbExtend.whenPressed(m_extend);
    ControlMap.climbRetract.whenPressed(m_retract);
    ControlMap.staticHookOn.whenPressed(m_hookOn);
    ControlMap.staticHookOff.whenPressed(m_hookOff);
    ControlMap.rotateWristDown.whenPressed(m_rotatewristdown);
    ControlMap.rotateWristUp.whenPressed(m_rotatewristup);
    ControlMap.topToggle.toggleWhenPressed(m_start_end_top_servo);
    ControlMap.bottomToggle.toggleWhenPressed(m_start_end_bottom_servo);
    ControlMap.wristToggle.toggleWhenPressed(m_start_end_wrist_servo);

  }

  private void configureDefaultCommands() {
    m_drivetrain.setDefaultCommand(m_defaultdrive);
    m_climb.setDefaultCommand(m_pivot);
    m_climb.setDefaultCommand(m_arm);
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
