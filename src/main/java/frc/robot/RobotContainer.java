// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ResourceBundle.Control;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.controls.ControlMap;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.commands.*;

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

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    ControlMap.midToHigh.whenPressed(new ServoOpen(m_climb).andThen(new ExtendArm(m_climb)).andThen(new RetractArm(m_climb)).andThen(new ServoClose(m_climb)).andThen(new PartialExtend(m_climb)).andThen(new Pivot(m_climb)));
    ControlMap.highToTraversal.whenPressed(new ServoOpen(m_climb).andThen(new ExtendArm(m_climb)).andThen(new RetractArm(m_climb)).andThen(new ServoClose(m_climb)));
    ControlMap.groundToMid.whenPressed(new ExtendArm(m_climb).andThen(new ServoOpen(m_climb)).andThen(new RetractArm(m_climb)).andThen(new ServoClose(m_climb)).andThen(new PartialExtend(m_climb)).andThen(new Pivot(m_climb)));
    
  }

  private Command ParallelCommandGroup(Pivot pivot, RetractArm retractArm) {
    return null;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
