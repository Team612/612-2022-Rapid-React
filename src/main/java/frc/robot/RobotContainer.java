// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import java.io.File;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.*;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Trajectories.TrajectoryCreation;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.DeleteValues;
import frc.robot.commands.FollowTrajectory;
import frc.robot.commands.GetValues2;
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
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final DefaultDrive m_defaultdrive = new DefaultDrive(m_drivetrain);
  private final FollowTrajectory m_follower = new FollowTrajectory();
  private final TrajectoryCreation m_trajectory = new TrajectoryCreation();
  private final GetValues2 m_getvalues_2 = new GetValues2(m_drivetrain);
  private final DeleteValues m_deletevalue = new DeleteValues();

  // private final static Date date = new Date();
  // public final static File file = new File("/tmp/test.txt");// +  String.valueOf(date.getTime()));
  // public static FileWriter writer;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureDefaultCommands();
    
  }

  // public static void write(String text){
  //   try{
  //     if(writer == null){
  //       writer = new FileWriter(file, true);
  //     }
  //     writer.write(text);
  //     writer.flush(); //flushes the buffer
  //     System.out.println(text);
  //   }
  //   catch(IOException e){
  //     e.printStackTrace();
  //   }
  // }

  // public static void closeFile(){
  //   try{
  //     if(writer != null){
  //       writer.close();
  //     }
  //   }
  //   catch(IOException e){
  //     e.printStackTrace();
  //   }
  //   writer = null;
  // }

  // public static void deleteFile(){
  //   file.delete();
  // }

  private void configureButtonBindings() {
    m_chooser.addOption("Mecanum Trajectory", m_follower.generateTrajectory(m_drivetrain, m_trajectory.testTrajectory));
    m_chooser.addOption("Bill", m_follower.generateTrajectory(m_drivetrain, m_trajectory.testTrajectory2));
    m_chooser.addOption("New New Path", m_follower.generateTrajectory(m_drivetrain, m_trajectory.examplePath));
    m_chooser.addOption("Get Values 2", m_getvalues_2);
    m_chooser.addOption("delete values", m_deletevalue);

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
    // return m_chooser.getSelected();
    return m_chooser.getSelected();
  }
}
