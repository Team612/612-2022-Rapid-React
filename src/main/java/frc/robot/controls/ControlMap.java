// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.controls;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class ControlMap{
    //driver ports
    public static int DRIVER_PORT = 0;
    public static int GUNNER_PORT = 1;
    
    //Controller objects
    public static Joystick driver = new Joystick(DRIVER_PORT);
    public static Joystick gunner = new Joystick(GUNNER_PORT);

    public static double pivotValue;

    public static final JoystickButton X = new JoystickButton(gunner, 3);
    public static final JoystickButton Y = new JoystickButton(gunner, 4);
    public static final JoystickButton B = new JoystickButton(gunner, 2);
}