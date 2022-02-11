// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
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
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //m_drivetrain.arcadeDrive(ControlMap.driver.getRawAxis(1), ControlMap.driver.getRawAxis(0));
    m_drivetrain.arcadeDrive(1.0, 0);
    //System.out.println(WPIUtilJNI.now() * 1.0e-6);
    m_vector_values.put(WPIUtilJNI.now() * 1.0e-6, new Double[]{m_drivetrain.getIntegral().getChassisVelocityWithTime(m_drivetrain.getAccelX(), m_drivetrain.getEncoderLeft(), m_drivetrain.getEncoderRight()), (m_drivetrain.getAccelX())});
    //System.out.println(m_drivetrain.getIntegral().getChassisVelocityWithTime(m_drivetrain.getAccelX(), m_drivetrain.getEncoderLeft(), m_drivetrain.getEncoderRight()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    try {
      PrintWriter myWriter = new PrintWriter("C:\\Users\\Srinvas\\Desktop\\GitHub\\612-2022-Rapid-React\\src\\main\\java\\frc\\robot\\commands\\logger.txt");
      //myWriter.write("Files in Java might be tricky, but it is fun enough!");
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
      myWriter.flush();
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  
    /*m_drivetrain.arcadeDrive(0, 0);
    System.out.println("****TIME****");
    for(double key: m_vector_values.keySet()){
      System.out.println(key + " ");
    }

    System.out.println();
    System.out.println("****VELOCITY*****"); 
    for(double key: m_vector_values.keySet()){
      System.out.println(m_vector_values.get(key)[0] + " ");
    }

    System.out.println();
    System.out.println("****ACCELERATION****");
    for(double key: m_vector_values.keySet()){
      System.out.println(m_vector_values.get(key)[1] + " ");
    }*/

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
