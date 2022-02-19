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
    public static JoystickButton climbExtend = new JoystickButton(gunner, 6);
    public static JoystickButton climbRetract = new JoystickButton(gunner, 7);
    public static JoystickButton staticHookOn = new JoystickButton(gunner, 8);
    public static JoystickButton staticHookOff = new JoystickButton(gunner, 9);
    /*public static JoystickButton topOpen = new JoystickButton(gunner, 10);
    public static JoystickButton topClose = new JoystickButton(gunner, 11);
    public static JoystickButton bottomOpen = new JoystickButton(gunner, 12);
    public static JoystickButton bottomClose = new JoystickButton(gunner, 13); */
    public static JoystickButton topToggle = new JoystickButton(gunner, 69);
    public static JoystickButton bottomToggle = new JoystickButton(gunner, 420);
    public static JoystickButton rotateWristUp = new JoystickButton(gunner, 10);
    public static JoystickButton rotateWristDown = new JoystickButton(gunner, 11);
    public static JoystickButton wristToggle = new JoystickButton(gunner, 42);

    
}