package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class AutoIntake extends CommandBase {
  /** Creates a new AutoIntake. */
  private final Ultrasonic m_ultrasonicIntake = new Ultrasonic(Constants.ULTRASONIC_INTAKE[0], Constants.ULTRASONIC_INTAKE[1]);

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
    Ultrasonic.setAutomaticMode(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("Intake ultrasonic in Inches: " + m_ultrasonicIntake.getRangeInches());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.BottomServoClose();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_ultrasonicIntake.getRangeInches() <= Constants.ULTRASONIC_INTAKE_THRESH;
  }
}