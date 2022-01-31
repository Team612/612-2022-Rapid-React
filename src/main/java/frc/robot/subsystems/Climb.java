package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.commands.ServoOpen;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;    
import edu.wpi.first.wpilibj.Servo;
public class Climb extends SubsystemBase {
    //defines both Talons and Solenoids
    private WPI_TalonSRX pivotMotor = new WPI_TalonSRX(Constants.Talon);
    private Servo Servos1 = new Servo(Constants.Servos);
    private Servo Servo2 = new Servo(Constants.Servos);
    private final DoubleSolenoid secondClimb = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.firstSolenoid[0], Constants.firstSolenoid[1]);
    private final DoubleSolenoid climb = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.secondSolenoid[0], Constants.secondSolenoid[1]);
    private final WPI_TalonFX pivotMotor2 = new WPI_TalonFX(0, "rio");
    //Pushes the piston out
    public void extendArm(){
        climb.set(Value.kForward);
        secondClimb.set(Value.kForward);

    }

    //partial extention
    public void partialExtention(){
        climb.set(Value.kForward);
        secondClimb.set(Value.kReverse);
    }
    
    //Brings the piston in
    public void retractArm(){
        climb.set(Value.kReverse);
        secondClimb.set(Value.kOff);
        pivotMotor2.setNeutralMode(NeutralMode.Brake);
    }


    //Pivots the pivot arms
    public void pivot(){
        pivotMotor2.set(TalonFXControlMode.Position, Constants.ticks);
    }
    //Opens servos
    public void ServoOpen(){
        Servos1.setAngle(45);
        Servo2.setAngle(45);
        
    }
    //Closes servos
    public void ServoClose(){
        Servos1.setAngle(0);
        Servo2.setAngle(0);
        

    }


    @Override
    public void periodic(){
        
    }
}
