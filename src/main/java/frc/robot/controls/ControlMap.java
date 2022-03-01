package frc.robot.controls;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class ControlMap{
    //driver ports
    private static int DRIVER_PORT = 0;
    private static int GUNNER_PORT = 1;
    
    //Controller objects
    public static Joystick driver = new Joystick(DRIVER_PORT);
    public static Joystick gunner = new Joystick(GUNNER_PORT);
    public static JoystickButton climbExtend = new JoystickButton(gunner, 1); //A
    public static JoystickButton climbRetract = new JoystickButton(gunner, 2); //B
    public static JoystickButton staticHookOn = new JoystickButton(gunner, 3); //X
    public static JoystickButton staticHookOff = new JoystickButton(gunner, 4); //Y
    public static JoystickButton arm_down = new JoystickButton(gunner, 7); //LB
    public static JoystickButton arm_up = new JoystickButton(gunner, 8); //RB


  
    public static JoystickButton bottomOpen = new JoystickButton(gunner, 5);
    public static JoystickButton bottomClose = new JoystickButton(gunner, 6); 
    /*public static JoystickButton topToggle = new JoystickButton(gunner, 69);
    public static JoystickButton bottomToggle = new JoystickButton(gunner, 420);
    public static JoystickButton rotateWristUp = new JoystickButton(gunner, 10);
    public static JoystickButton rotateWristDown = new JoystickButton(gunner, 11);
    public static JoystickButton wristToggle = new JoystickButton(gunner, 42);*/

    
}

