// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.util.ResourceBundle.Control;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Climb.ClimbClose;
import frc.robot.commands.Climb.ClimbOpen;
import frc.robot.commands.Climb.ExtendClimb;
import frc.robot.commands.Climb.PivotClimb;
import frc.robot.commands.Climb.RetractClimb;
import frc.robot.commands.Climb.ToggleClimbHooks;
import frc.robot.commands.Climb.ToggleClimb;
import frc.robot.commands.Drivetrain.DefaultDrive;
import frc.robot.commands.Drivetrain.FollowTrajectory;
import frc.robot.commands.Drivetrain.ReverseRobot;
import frc.robot.commands.Drivetrain.TrajectoryCreation;
import frc.robot.commands.Intake.Arm;
import frc.robot.commands.Intake.ArmForward;
import frc.robot.commands.Intake.ArmReverse;
import frc.robot.commands.Intake.BottomClose;
import frc.robot.commands.Intake.BottomOpen;
import frc.robot.commands.Intake.ToggleBottom;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  //Subsystem Declarations
  private final Climb m_climb = new Climb();
  private final Intake m_intake = new Intake();
  private final Drivetrain m_drivetrain = new Drivetrain();

  //configure default commands
  private final DefaultDrive m_default = new DefaultDrive(m_drivetrain);
  private final PivotClimb m_pivot = new PivotClimb(m_climb);
  private final Arm m_arm = new Arm(m_intake);

  //Trajectories
  private final FollowTrajectory m_follower = new FollowTrajectory();
  private final TrajectoryCreation m_traj = new TrajectoryCreation();
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();


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
    m_chooser.addOption("Mecanum Trajectory", m_follower.generateTrajectory(m_drivetrain, m_traj.testTrajectory));
    m_chooser.addOption("Bill", m_follower.generateTrajectory(m_drivetrain, m_traj.testTrajectory2));
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

    testButtonBindings();
    //compButtonBindings();
  }

  private void testButtonBindings(){
    ControlMap.GUNNER_BACK.whenPressed(new ExtendClimb(m_climb));
    ControlMap.GUNNER_START.whenPressed(new RetractClimb(m_climb));
    ControlMap.GUNNER_Y.whileHeld(new ArmReverse(m_intake));
    ControlMap.GUNNER_A.whileHeld(new ArmForward(m_intake));
    ControlMap.GUNNER_B.toggleWhenPressed(new ToggleBottom(m_intake));
    ControlMap.GUNNER_LB.whenPressed(new ClimbClose(m_climb));
    ControlMap.GUNNER_RB.whenPressed(new ClimbOpen(m_climb));
    // ControlMap.DRIVER_A.whenPressed(new ReverseRobot(m_drivetrain));
    // ControlMap.GUNNER_RB.toggleWhenPressed(new StartEndCommand(m_climb::ServoClose, m_climb::ServoOpen, m_climb));
    // ControlMap.GUNNER_RB.toggleWhenPressed(new ToggleClimbHooks(m_climb));
  }

  private void compButtonBindings(){ //time to give up, toggles are bad
    ControlMap.GUNNER_Y.whileHeld(new ArmReverse(m_intake)); //outtake
    ControlMap.GUNNER_A.whileHeld(new ArmForward(m_intake)); //intake
    ControlMap.GUNNER_B.toggleWhenPressed(new ToggleBottom(m_intake)); //grabber
    ControlMap.GUNNER_START.whenPressed(new ExtendClimb(m_climb)); //
    ControlMap.GUNNER_BACK.whenPressed(new RetractClimb(m_climb));
    ControlMap.GUNNER_RB.whenPressed(new ClimbOpen(m_climb));
    ControlMap.GUNNER_LB.whenPressed(new ClimbOpen(m_climb));
  }
  /*
   pivot motor - gunner right x-axis (raw axis 4)
   arm - whileHeld - y is outtake (reverse) and a is intake (forward)
   grabber - toggle - b 
   pistons - start is extend and back is retract 
   hooks - lb hook on and rb is hook off

   toggle drivetrain directions - talk to fred
  */

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
    return m_chooser.getSelected();
  }
}
