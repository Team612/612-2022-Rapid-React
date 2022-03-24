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


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Climb m_climb = new Climb();
  
  private final Pivot m_autoCommand = new Pivot(m_climb);

  private final TrajectoryCreation m_trajectory = new TrajectoryCreation();

  private final Intake m_intake = new Intake();
  private final ExtendClimb m_extend = new ExtendClimb(m_climb);
  private final RetractClimb m_retract = new RetractClimb(m_climb);
  private final HookOff m_hookOff = new HookOff(m_climb);
  private final HookOn m_hookOn = new HookOn(m_climb);
  private final Pivot m_pivot = new Pivot(m_climb);
  private final ToggleClimb m_toggleClimb = new ToggleClimb(m_climb);
  private final ToggleHooks m_toggleHooks = new ToggleHooks(m_climb);
  private final TopClose m_topclose = new TopClose(m_intake);
  private final TopOpen m_topopen = new TopOpen(m_intake);
  private final BottomClose m_bottomclose = new BottomClose(m_intake);
  private final BottomOpen m_bottomopen = new BottomOpen(m_intake);
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final DefaultDrive m_default = new DefaultDrive(m_drivetrain);

  private final FollowTrajectory m_follower = new FollowTrajectory();
  private final TrajectoryCreation m_traj = new TrajectoryCreation();

  //private final RotateWristDown m_rotatewristdown = new RotateWristDown(m_intake);
  //private final RotateWristUp m_rotatewristup = new RotateWristUp(m_intake);
  private final Arm m_arm = new Arm(m_intake);
  //private final StartEndCommand m_start_end_top_servo = new StartEndCommand(m_intake::TopServoClose, m_intake::TopServoOpen, m_intake);
  //private final StartEndCommand m_start_end_bottom_servo = new StartEndCommand(m_intake::BottomServoClose, m_intake:: BottomServoOpen, m_intake);
  //private final StartEndCommand m_start_end_wrist_servo = new StartEndCommand(m_intake::WristClose, m_intake::WristOpen, m_intake);


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureDefaultCommands();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_chooser.addOption("Mecanum Trajectory", m_follower.generateTrajectory(m_drivetrain, m_trajectory.testTrajectory));
    m_chooser.addOption("Bill", m_follower.generateTrajectory(m_drivetrain, m_trajectory.testTrajectory2));
    //m_chooser.addOption("Manual Code", object);
    m_chooser.addOption("Line up 1v1", m_follower.generateTrajectory(m_drivetrain, m_traj.path1v1));
    m_chooser.addOption("Line up 1v2", m_follower.generateTrajectory(m_drivetrain, m_traj.path1v2));
    m_chooser.addOption("Line up 1v3", m_follower.generateTrajectory(m_drivetrain, m_traj.path1v3));

    m_chooser.addOption("Two Ball Pick Up Left First", m_follower.generateTrajectory(m_drivetrain, m_traj.path2v1));
    m_chooser.addOption("Two Ball Pick Up Left First", m_follower.generateTrajectory(m_drivetrain, m_traj.path2v2));
    m_chooser.addOption("Two Ball Pick Up Left First", m_follower.generateTrajectory(m_drivetrain, m_traj.path2v3));
    m_chooser.addOption("Two Ball Pick Up Left First", m_follower.generateTrajectory(m_drivetrain, m_traj.path2v4));

    m_chooser.addOption("Two Ball Pick Up Right First", m_follower.generateTrajectory(m_drivetrain, m_traj.path3v1));
    m_chooser.addOption("Two Ball Pick Up Right First", m_follower.generateTrajectory(m_drivetrain, m_traj.path3v2));
    m_chooser.addOption("Two Ball Pick Up Right First", m_follower.generateTrajectory(m_drivetrain, m_traj.path3v3));
    m_chooser.addOption("Two Ball Pick Up Right First", m_follower.generateTrajectory(m_drivetrain, m_traj.path3v4));
    
    
    SmartDashboard.putData(m_chooser);

    //ControlMap.climbExtend.whenPressed(new ExtendClimb(m_climb));
    //ControlMap.climbRetract.whenPressed(new RetractClimb(m_climb));
    ControlMap.climbExtend.toggleWhenPressed(m_toggleClimb);
    ControlMap.staticHookOn.toggleWhenPressed(m_toggleHooks);
    //ControlMap.bottomClose.whenPressed(new BottomClose(m_intake));
    //ControlMap.bottomOpen.whenPressed(new BottomOpen(m_intake));
    ControlMap.bottomOpen.toggleWhenPressed(new ToggleBottomGrabber(m_intake));
    // ControlMap.staticHookOn.whenPressed(new HookOn(m_climb));
    // ControlMap.staticHookOff.whenPressed(new HookOff(m_climb));
    
    
    /*ControlMap.rotateWristDown.whenPressed(m_rotatewristdown);
    ControlMap.rotateWristUp.whenPressed(m_rotatewristup);
    ControlMap.topToggle.toggleWhenPressed(m_start_end_top_servo);
    ControlMap.bottomToggle.toggleWhenPressed(m_start_end_bottom_servo);
    ControlMap.wristToggle.toggleWhenPressed(m_start_end_wrist_servo);*/
    
  }

  private void configureDefaultCommands() {
    m_drivetrain.setDefaultCommand(m_default);
    m_climb.setDefaultCommand(m_pivot);
    m_intake.setDefaultCommand(m_arm);

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous sussy baka
    return m_chooser.getSelected();

  }
}
