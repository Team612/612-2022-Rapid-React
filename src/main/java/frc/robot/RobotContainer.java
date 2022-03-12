// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Climb.ClimbClose;
import frc.robot.commands.Climb.ClimbOpen;
import frc.robot.commands.Climb.ExtendClimb;
import frc.robot.commands.Climb.NeutralClimb;
//import frc.robot.commands.Climb.PivotClimb;
import frc.robot.commands.Climb.PivotPistonsSeperate;
import frc.robot.commands.Climb.RetractClimb;
import frc.robot.commands.Climb.ToggleClimbHooks;
import frc.robot.commands.Climb.ToggleClimb;
import frc.robot.commands.Drivetrain.DefaultDrive;
import frc.robot.commands.Drivetrain.FollowTrajectory;
import frc.robot.commands.Drivetrain.TrajectoryCreation;
import frc.robot.commands.Drivetrain.ZeroYaw;
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
import frc.robot.subsystems.PivotMotor;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

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

  // Subsystem Declarations
  private final Climb m_climb = new Climb();
  private final Intake m_intake = new Intake();
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final PivotMotor m_pivotmotor = new PivotMotor();

  // configure default commands
  private final DefaultDrive m_default = new DefaultDrive(m_drivetrain);
  //private final PivotClimb m_pivot = new PivotClimb(m_climb);
  private final PivotPistonsSeperate m_pivot = new PivotPistonsSeperate(m_pivotmotor, m_climb);
  private final Arm m_arm = new Arm(m_intake);

  // Trajectories
  private final FollowTrajectory m_follower = new FollowTrajectory();
  private final TrajectoryCreation m_traj = new TrajectoryCreation();
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  private final SequentialCommandGroup m_autonomousSequentialOnePath = new SequentialCommandGroup(
    new BottomClose(m_intake)
    .andThen(new ArmReverse(m_intake))
    .andThen(new BottomOpen(m_intake))
    .andThen(new ZeroYaw())
    .andThen(m_follower.generateTrajectory(m_drivetrain, m_traj.getOutOfTarmac))
  );

  private final SequentialCommandGroup m_outTarmacGetBall = new SequentialCommandGroup(
    m_follower.generateTrajectory(m_drivetrain, m_traj.getOutOfTarmac)
    .andThen(m_follower.generateTrajectory(m_drivetrain, m_traj.moveToBall))
  );


  public RobotContainer() { 
    // Configure the button bindings
    configureButtonBindings();
    configureDefaultCommands();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_chooser.addOption("get out of tarmac", m_outTarmacGetBall);
    // m_chooser.addOption("auto test", m_outTarmacGetBall);
    // m_chooser.addOption("PathPlanner test", m_follower.generateTrajectory(m_drivetrain, m_traj.path2v3));
    SmartDashboard.putData(m_chooser);

    manualButtonBindings();
    // autoButtonBindings();
  }

  private void manualButtonBindings() {
    ControlMap.GUNNER_BACK.whenPressed(new ExtendClimb(m_climb));
    ControlMap.GUNNER_START.whenPressed(new RetractClimb(m_climb));
    ControlMap.GUNNER_Y.whileHeld(new ArmReverse(m_intake));
    ControlMap.GUNNER_A.whileHeld(new ArmForward(m_intake));
    ControlMap.GUNNER_X.whenPressed(new BottomOpen(m_intake));
    ControlMap.GUNNER_B.whenPressed(new BottomClose(m_intake));
    ControlMap.GUNNER_LB.whenPressed(new ClimbClose(m_climb));
    ControlMap.GUNNER_RB.whenPressed(new ClimbOpen(m_climb));
    ControlMap.GUNNER_DUP.whenPressed(new NeutralClimb(m_climb));
    // ControlMap.GUNNER_RB.toggleWhenPressed(new
    // StartEndCommand(m_climb::ServoClose, m_climb::ServoOpen, m_climb));
    // ControlMap.GUNNER_RB.toggleWhenPressed(new ToggleClimbHooks(m_climb));
  }

  private void autoButtonBindings() {
    ControlMap.GUNNER_A.toggleWhenPressed(new ToggleBottom(m_intake));
    ControlMap.GUNNER_BACK.toggleWhenPressed(new ToggleClimb(m_climb));
    ControlMap.GUNNER_Y.whenPressed(new ToggleClimbHooks(m_climb));
    // ControlMap.GUNNER_A.toggleWhenPressed(new ToggleArm(m_intake));
  }
  /*
   * things we can toggle:
   * arm - a
   * grabber - b
   * pistons - back
   * hooks - y
   * pivot motor - start
   */

  private void configureDefaultCommands() {
    m_drivetrain.setDefaultCommand(m_default);
    m_pivotmotor.setDefaultCommand(m_pivot);
    m_intake.setDefaultCommand(m_arm);

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
    // return m_follower.generateTrajectory(m_drivetrain, m_traj.getOutOfTarmac);
    // return m_follower.generateTrajectory(m_drivetrain, m_traj.moveForwardTwoMeters);
  }
}
