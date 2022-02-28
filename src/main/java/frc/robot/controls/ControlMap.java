package frc.robot.controls;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/** Add your docs here. */
public class ControlMap {
    //Driver and Gunner Defined
public static XboxController driver = new XboxController(0);
public static XboxController gunner = new XboxController(1);

 

public static final JoystickButton groundToMid = new JoystickButton(gunner, 1);
public static final JoystickButton midToHigh = new JoystickButton(gunner, 2);
public static final JoystickButton highToTraversal = new JoystickButton(gunner, 3);


    
}
