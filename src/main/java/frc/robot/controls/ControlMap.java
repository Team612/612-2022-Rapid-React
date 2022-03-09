// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** Add your docs here. */
public class ControlMap {
    //Initialize keyboard
    public static Joystick m_control = new Joystick(0);

    //button bindings for the left solenoid
    public static JoystickButton m_z = new JoystickButton(m_control, 1);
    public static JoystickButton m_x = new JoystickButton(m_control, 2);
    public static JoystickButton m_c = new JoystickButton(m_control, 3);
    public static JoystickButton m_v = new JoystickButton(m_control, 4);

    //button bindings for the left servo intake claw
    public static JoystickButton m_b = new JoystickButton(m_control, 5);
    public static JoystickButton m_n = new JoystickButton(m_control, 6);

    //button bindings for the climb talon pivot
    public static JoystickButton m_m = new JoystickButton(m_control, 7);

    //button bindings for the left static hook servo
    public static JoystickButton m_l = new JoystickButton(m_control, 8);
    public static JoystickButton m_k = new JoystickButton(m_control, 9);

    //button bindings for the right solenoid
    public static JoystickButton m_j = new JoystickButton(m_control, 10);
    public static JoystickButton m_h = new JoystickButton(m_control, 11);
    public static JoystickButton m_g = new JoystickButton(m_control, 12);
    public static JoystickButton m_f = new JoystickButton(m_control, 13);
    
    //button bindings for the right static hook servo
    public static JoystickButton m_q = new JoystickButton(m_control, 14);
    public static JoystickButton m_t = new JoystickButton(m_control, 15);

    //button bindings for the right servo intake claw
    public static JoystickButton m_y = new JoystickButton(m_control, 16);
    public static JoystickButton m_u = new JoystickButton(m_control, 17);

    //button bindings for the intake arm talon
    public static JoystickButton m_i = new JoystickButton(m_control, 18);
}
