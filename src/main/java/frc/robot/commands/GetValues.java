// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
 
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.RomiDrivetrain;
 
public class GetValues extends CommandBase {
  /** Creates a new DefaultDrive. */
  private RomiDrivetrain m_drivetrain;
  private Map<Double, Double[]> m_vector_values = Collections.synchronizedMap(new LinkedHashMap<Double, Double[]>());
 
  public GetValues(RomiDrivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }
 
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }
 
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(-1.0, 0);
    /*m_vector_values.put(WPIUtilJNI.now() * 1.0e-6,
        new Double[] {
            m_drivetrain.getIntegral().getChassisVelocityWithTime(m_drivetrain.getAccelX(),m_drivetrain.getEncoderLeft(), m_drivetrain.getEncoderRight()),
            m_drivetrain.getAccelX(),
            m_drivetrain.getIntegral().toWheelSpeeds(m_drivetrain.getIntegral().getChassisVelocityWithTime(m_drivetrain.getAccelX(), m_drivetrain.getEncoderLeft(), m_drivetrain.getEncoderRight()), 0.141, m_drivetrain.getGyro())[0], //make method better
            m_drivetrain.getIntegral().toWheelSpeeds(m_drivetrain.getIntegral().getChassisVelocityWithTime(m_drivetrain.getAccelX(), m_drivetrain.getEncoderLeft(), m_drivetrain.getEncoderRight()), 0.141, m_drivetrain.getGyro())[1], //make method better
        });*/
    m_vector_values.put(WPIUtilJNI.now() * 1.0e-6,
        new Double[]{
          m_drivetrain.getChassisVelocityX(),
          m_drivetrain.getAccelX(),
          m_drivetrain.getWheelSpeeds()[0],
          m_drivetrain.getWheelSpeeds()[1],
        }
    );

  }
 
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    try {
      PrintWriter myWriter = new PrintWriter("C:\\Users\\Srinvas\\Desktop\\GitHub\\612-2022-Rapid-React\\src\\main\\java\\frc\\robot\\commands\\logger.txt");
      myWriter.append("****TIME**** \n");
      for(double key: m_vector_values.keySet()){
        myWriter.append(key + " \n");
      }
 
      myWriter.append("\n");
      myWriter.append("****VELOCITY***** \n"); 
      for(double key: m_vector_values.keySet()){
        myWriter.append(m_vector_values.get(key)[0] + " \n");
      }
 
      myWriter.append("\n");
      myWriter.append("****ACCELERATION**** \n");
      for(double key: m_vector_values.keySet()){
        myWriter.append(m_vector_values.get(key)[1] + " \n");
      }
 
      myWriter.append("\n");
      myWriter.append("****WHEEL VELOCITIES LEFT WHEEL VELOCITY**** \n");
      for(double key: m_vector_values.keySet()){
        myWriter.append(m_vector_values.get(key)[2] + " \n");
      }
  
      myWriter.append("\n");
      myWriter.append("****WHEEL VELOCITIES RIGHT WHEEL VELOCITY**** \n");
      for(double key: m_vector_values.keySet()){
        myWriter.append(m_vector_values.get(key)[3] + " \n");
      }
      myWriter.flush();
      myWriter.close();
    } 
    
    catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
 
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
