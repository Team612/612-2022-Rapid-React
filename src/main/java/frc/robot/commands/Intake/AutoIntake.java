package frc.robot.commands.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class AutoIntake extends CommandBase {
  /** Creates a new AutoIntake. */
  private final Intake m_intake;
  public AutoIntake(Intake intake) {
    m_intake = intake;
    addRequirements(intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_intake.BottomServoOpen();
    m_intake.setSearchingInput(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("waiting for button pressed");
    m_intake.setSearchingInput(true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.setSearchingInput(false);
    m_intake.BottomServoClose();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_intake.getButtonVal() && m_intake.getBoreEncoder() > 0.93;
  }
}