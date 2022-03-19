package frc.robot.controls;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

public class ControlMap{
    //driver ports
    private static int DRIVER_PORT = 0;
    private static int GUNNER_PORT = 1;
    
    //Controller objects
    public static Joystick driver = new Joystick(DRIVER_PORT);
    public static Joystick gunner = new Joystick(GUNNER_PORT);
   
    public static JoystickButton GUNNER_A = new JoystickButton(gunner, 1); //A
    public static JoystickButton GUNNER_B = new JoystickButton(gunner, 2); //B
    public static JoystickButton GUNNER_X = new JoystickButton(gunner, 3); //X
    public static JoystickButton GUNNER_Y = new JoystickButton(gunner, 4); //Y
    public static JoystickButton GUNNER_LB = new JoystickButton(gunner, 5); //LB
    public static JoystickButton GUNNER_RB = new JoystickButton(gunner, 6); //RB
    public static JoystickButton GUNNER_BACK = new JoystickButton(gunner, 7); 
    public static JoystickButton GUNNER_START = new JoystickButton(gunner, 8); 
    public static JoystickButton GUNNER_LJ_BUTTON = new JoystickButton(gunner, 9); //
    public static JoystickButton GUNNER_RJ_BUTTON = new JoystickButton(gunner, 10); //
    public static POVButton GUNNER_DUP = new POVButton(gunner, 0);

    
}