// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RioWriter;
import frc.robot.subsystems.Drivetrain;
import com.google.gson.Gson;

public class GetValues2 extends CommandBase {
  private Drivetrain m_drivetrain;
  private Map<String, String[]> m_vector_values = Collections.synchronizedMap(new LinkedHashMap<String, String[]>());
  private Gson gson = new Gson();
  Runtime rt = Runtime.getRuntime();

  public GetValues2(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    m_drivetrain.calibrate();
  }

  @Override
  public void execute() {
    m_drivetrain.driveMecanum(0.5, 0.5, 0.5, 0.5);
    m_vector_values.put(Double.toString(WPIUtilJNI.now() * 1.0e-6),
        new String[] {
            Double.toString(m_drivetrain.getChassisRawVelocityX()),
            Double.toString(m_drivetrain.getChassisLinearVelocityX()),
            Double.toString(m_drivetrain.getEncoderVelocityX()),
            Double.toString(m_drivetrain.getRawAccelerationX()),
            Double.toString(m_drivetrain.getLinearAccelerationX())
        });

  }

  /**
   * PROCEDURE:
   * Add Data in the form of a hash map
   * Convert hash map into json file
   * Send json file to the Roborio
   * Need to close file to make sure nothing else comes in
   * clear the data in the hashmap
   */

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    String json = gson.toJson(m_vector_values, m_vector_values.getClass());
    // System.out.println(json); Manual testing

    RioWriter.write(json);
    RioWriter.closeFile();
    m_vector_values.clear();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
