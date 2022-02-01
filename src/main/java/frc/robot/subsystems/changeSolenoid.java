package frc.robot.subsystems;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;  
public class changeSolenoid extends SubsystemBase{
    private final DoubleSolenoid climb = new DoubleSolenoid(Constants.PCM_2, Constants.solenoidType, Constants.secondSolenoid[0], Constants.secondSolenoid[1]);
    private final Compressor comp = new Compressor(Constants.solenoidType);
    public void turnComp(){
        comp.enableAnalog(90, 120);
    }
    public void extendArm(){
        climb.set(Value.kForward);
    }

    public void retractArm(){
        climb.set(Value.kReverse);
    }
}