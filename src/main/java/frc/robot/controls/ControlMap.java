package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/** Add your docs here. */
public class ControlMap {
    //Driver and Gunner Defined
    public static XboxController driver = new XboxController(1);
    public static XboxController gunner = new XboxController(0);

    public static double pivotValue;
 

    public static final JoystickButton X = new JoystickButton(gunner, 0);

    public static Joystick joystick = new Joystick(0);

    public static final JoystickButton Y = new JoystickButton(gunner, 0);

    
}
