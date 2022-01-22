package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class Climb extends SubsystemBase {
    //defines both Talons and Solenoids
    private WPI_TalonSRX pivotMotor = new WPI_TalonSRX(Constants.Talon);
    private final DoubleSolenoid secondClimb = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.secondForward, Constants.secondReverse);
    private final DoubleSolenoid climb = new DoubleSolenoid(PneumaticsModuleType.REVPH, Constants.forward, Constants.reverse);

    //Pushes the piston out
    public void extendArm(){
        climb.set(Value.kForward);
        secondClimb.set(Value.kForward);

    }
    
    //Brings the piston in
    public void retractArm(){
        climb.set(Value.kReverse);
    }

    //Pivots the pivot arms
    public void pivot(double speed){
        pivotMotor.set(speed);
    }

    @Override
    public void periodic(){
        
    }
}
