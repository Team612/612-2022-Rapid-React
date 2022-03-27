package frc.robot.commands.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
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
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Intake ultrasonic in Inches: " + m_intake.getUltrasonicIntakeInches());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.BottomServoClose();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_intake.getUltrasonicIntakeInches() <= Constants.ULTRASONIC_INTAKE_THRESH;
  }
}