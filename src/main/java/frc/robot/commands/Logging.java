// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Logging extends CommandBase {
  /** Creates a new Logging. */
  public Drivetrain m_drivetrain;
  public Logging(Drivetrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drivetrain = drivetrain;
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Creates the main structure of the json file
    JSONObject seconds = new JSONObject();
    seconds.put("Encoder","running");
    seconds.put("Arm","running");

    JSONObject Time = new JSONObject();
    Time.put("TimeSensititve",seconds);

    JSONObject encoderValues = new JSONObject();
    encoderValues.put("Time","Values");
    
    JSONArray encoder = new JSONArray();
    encoder.add("Encoder", encoderValues);

    JSONObject NoTime = new JSONObject();
    NoTime.put("NonTimeSensitive",encoder);

    JSONArray fulltime = new JSONArray();
    fulltime.add(Time);
    fulltime.add(NoTime);

    ObjectMapper mapper = new ObjectMapper();

    try {
        FileWriter info = new FileWriter("infotest.json");
        info.write(json);
        info.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    try {
    FileWriter info1 = new FileWriter("info.json");
    JSONArray startinfo = new JSONArray();
    info1.write(startinfo);
    info1.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

 
  public void logNumericalData(String device, float data, float time){
    //try to read and append the data gathered
    JSONParser jsonParser = new JSONParser();
    try{
      Object obj = jsonParser.parse(new FileReader("info.json"));
      JSONArray jsonArray = (JSONArray)obj;
      JSONObject info = new JSONObject();
      info.put(device, time + " - " + data);
      FileWriter file = new FileWriter("info.json");
      file.write(jsonArray.toJSONString());
      file.flush();
      file.close();
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
  }

  public void logBoolData(String device, boolean data, float time){
    //try to read and append the data gathered
    JSONParser jsonParser = new JSONParser();
    try{
      Object obj = jsonParser.parse(new FileReader("info.json"));
      JSONArray jsonArray = (JSONArray)obj;
      JSONObject info = new JSONObject();
      info.put(device, time + " - " + data);
      FileWriter file = new FileWriter("info.json");
      file.write(jsonArray.toJSONString());
      file.flush();
      file.close();
    } catch (ParseException | IOException e) {
      e.printStackTrace();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
