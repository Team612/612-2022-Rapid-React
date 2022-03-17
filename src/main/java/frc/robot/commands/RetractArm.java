package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class RetractArm extends CommandBase {
  /** Creates a new RetractArm. */
  private final Climb m_climb;
  public RetractArm(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    addRequirements(climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climb.retractArm();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}