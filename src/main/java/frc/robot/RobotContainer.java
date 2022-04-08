// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Climb.ClimbClose;
import frc.robot.commands.Climb.ClimbOpen;
import frc.robot.commands.Climb.CompressorOff;
import frc.robot.commands.Climb.CompressorOn;
import frc.robot.commands.Climb.ExtendClimb;
import frc.robot.commands.Climb.NeutralClimb;
import frc.robot.commands.Climb.PivotMoveToPosition;
import frc.robot.commands.Climb.PivotPistonsSeperate;
import frc.robot.commands.Climb.RetractClimb;
import frc.robot.commands.Drivetrain.DefaultDrive;
import frc.robot.commands.Drivetrain.FollowTrajectory;
import frc.robot.commands.Drivetrain.TrajectoryCreation;
import frc.robot.commands.Intake.Arm;
import frc.robot.commands.Intake.ArmForward;
import frc.robot.commands.Intake.AutoClose;
import frc.robot.commands.Intake.AutoDelay;
import frc.robot.commands.Intake.AutoOuttake;
import frc.robot.commands.Intake.BottomClose;
import frc.robot.commands.Intake.BottomOpen;
import frc.robot.commands.Intake.ButtonOff;
import frc.robot.commands.Intake.ButtonOn;
import frc.robot.commands.Intake.ReleaseAtSpot;
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
  private final Climb m_climb = Climb.getInstance();
  public final Intake m_intake = Intake.getInstance();
  private final Drivetrain m_drivetrain = Drivetrain.getInstance();
  private final PivotMotor m_pivotmotor = PivotMotor.getInstance();

  // configure default commands
  private final DefaultDrive m_default = new DefaultDrive(m_drivetrain);
  private final PivotPistonsSeperate m_pivot = new PivotPistonsSeperate(m_pivotmotor, m_climb);
  private final Arm m_arm = new Arm(m_intake);

  // Trajectories
  private final FollowTrajectory m_follower = new FollowTrajectory();
  private final TrajectoryCreation m_traj = new TrajectoryCreation();
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  //Auto Shuffleboard
  ShuffleboardTab m_autoTab = Shuffleboard.getTab("autonomous");
  NetworkTableEntry m_autoDelay = m_autoTab.add("delay amount", 0).getEntry();

  private final SequentialCommandGroup dumpGetOut = new SequentialCommandGroup(
    new AutoDelay(m_autoDelay.getDouble(5.0))
    .andThen(new AutoClose(m_intake))
    .andThen(new AutoOuttake(m_intake))
    .andThen(m_follower.generateTrajectory(m_drivetrain, m_traj.getOutOfTarmac))
  );

  private final SequentialCommandGroup dumpGoToBall = new SequentialCommandGroup(
    new AutoClose(m_intake)
    .andThen(new AutoOuttake(m_intake))
    .andThen(m_follower.generateTrajectory(m_drivetrain, m_traj.moveToBallPart1))
    .andThen(m_follower.generateTrajectory(m_drivetrain, m_traj.moveToBallPart2))
  );

  private final SequentialCommandGroup onlyDump = new SequentialCommandGroup(
    new AutoClose(m_intake)
    .andThen(new AutoOuttake(m_intake))
  );

  private void servoInit(){
    m_intake.BottomServoClose();
  }

  private void openCimb(){
    m_climb.servoOpen();
  }

  public void setButtonStateTrue(){
    m_intake.setInputState(true);
  } 

  public RobotContainer() { 
    // Configure the button bindings
    configureButtonBindings();
    servoInit();
    openCimb();
    setButtonStateTrue();
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
    m_chooser.addOption("Get out of tarmac", m_follower.generateTrajectory(m_drivetrain, m_traj.getOutOfTarmac));
    m_chooser.addOption("Dump get out of tarmac", dumpGetOut);
    m_chooser.addOption("Dump go to ball", dumpGoToBall);
    m_chooser.addOption("Only Dump" , onlyDump);
    SmartDashboard.putData(m_chooser);

    compButtonBindings();
  }

  private void compButtonBindings() {
    ControlMap.GUNNER_BACK.whenPressed(new ExtendClimb(m_climb));
    ControlMap.GUNNER_START.whenPressed(new RetractClimb(m_climb));
    ControlMap.GUNNER_Y.whileHeld(new ReleaseAtSpot(m_intake));
    ControlMap.GUNNER_A.whileHeld(new ArmForward(m_intake));
    ControlMap.GUNNER_X.whenPressed(new BottomClose(m_intake));
    ControlMap.GUNNER_B.whenPressed(new BottomOpen(m_intake));
    //ControlMap.GUNNER_X.whenPressed(new BottomOpen(m_intake));

    ControlMap.GUNNER_LB.whenPressed(new ClimbClose(m_climb));
    ControlMap.GUNNER_RB.whenPressed(new ClimbOpen(m_climb));
    ControlMap.GUNNER_DUP.whenPressed(new NeutralClimb(m_climb));

    ControlMap.GUNNER_DDOWN.whileHeld(new PivotMoveToPosition(m_pivotmotor));    
    ControlMap.DRIVER_A.whenPressed(new CompressorOn(m_climb));
    ControlMap.DRIVER_B.whenPressed(new CompressorOff(m_climb));

    //new stuff
    ControlMap.DRIVER_START.whenPressed(new ButtonOff(m_intake));
    ControlMap.DRIVER_BACK.whenPressed(new ButtonOn(m_intake));
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
  }
}
