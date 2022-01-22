package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;
import frc.robot.Constants;
import frc.robot.controls.ControlMap;
/** An example command that uses an example subsystem. */
public class RunClimb extends CommandBase {
    private final Climb m_climb;

    //Constructor
    public RunClimb(Climb climb){
        m_climb = climb;
        addRequirements(climb);
    }
    //Extend the pivot arm
    @Override
    public void initialize(){
        m_climb.extendArm();
    }

    //Retract pivot arm to pull robot to the rung
    @Override
    public void execute(){
        
    }
    
    
    @Override
    public void end(boolean interrupted){
        m_climb.retractArm();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}

  