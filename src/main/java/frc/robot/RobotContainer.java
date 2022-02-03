// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveMotorVoltages;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DefaultDrive;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.MecanumControllerCommand;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final DefaultDrive m_defaultdrive = new DefaultDrive(m_drivetrain);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
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

  public Command generateTrajectory(){
    TrajectoryConfig config = new TrajectoryConfig(Constants.kMaxVelocityMetersPerSecond, Constants.maxAccelerationMetersPerSecondSq)
    .setKinematics(Constants.kDriveKinematics);

    Trajectory testTrajectory = TrajectoryGenerator.generateTrajectory(
      new Pose2d(0, 0, new Rotation2d(0)), 
      List.of(new Translation2d(2,0), new Translation2d(2, -1.5), new Translation2d(0, -1.5)), 
      new Pose2d(0,0, new Rotation2d(0)), 
      config);


     MecanumControllerCommand mecanumControllerCommand =
      new MecanumControllerCommand(
        testTrajectory,
        m_drivetrain::getPose,
        Constants.kFeedforward,
        Constants.kDriveKinematics,

        //Position controllers 
        new PIDController(Constants.kPXController, 0, 0),
        new PIDController(Constants.kPYController, 0, 0),
        new ProfiledPIDController(Constants.kPThetaController, 0, 0, Constants.kThetaControllerConstraints),

        Constants.kMaxVelocityMetersPerSecond,

        //Velocity PID's
        new PIDController(Constants.kPFrontLeftVel, 0, 0),
        new PIDController(Constants.kPRearLeftVel, 0, 0),
        new PIDController(Constants.kPFrontRightVel, 0, 0),
        new PIDController(Constants.kPRearRightVel, 0, 0),
        m_drivetrain::getCurrentWheelSpeeds,
        m_drivetrain::mecanumVolts,
        m_drivetrain);
      
    
        //setting up sequence of commands
        //resetting the drivetrain odometry
        return new InstantCommand(() -> m_drivetrain.resetOdometry(testTrajectory.getInitialPose()), m_drivetrain)
          //run the actual MecanumControllor
          .andThen(mecanumControllerCommand)
          //Make sure that the robot stops
          .andThen(new InstantCommand (() -> m_drivetrain.mecanumVolts(new MecanumDriveMotorVoltages(0,0,0,0)), m_drivetrain));

    
   /* // Reset odometry to the starting pose of the trajectory.
    m_drivetrain.resetOdometry(testTrajectory.getInitialPose());
    // Run path following command, then stop at the end.
    return mecanumControllerCommand.andThen(() -> m_drivetrain.driveMecanum(0, 0, 0));*/
    

}

  private void configureButtonBindings() {
    m_chooser.addOption("Mecanum Trajectory", generateTrajectory());
    SmartDashboard.putData(m_chooser);
  }

  private void configureDefaultCommands(){
    m_drivetrain.setDefaultCommand(m_defaultdrive);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return m_chooser.getSelected();
    //return m_chooser.getSelected();
    
    return generateTrajectory();
  }
}
