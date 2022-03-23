// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


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
    //Creates a blank json file to write on.
    JSONArray fulltime = new JSONArray();

    ObjectMapper mapper = new ObjectMapper();

    try {
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fulltime);
        FileWriter info = new FileWriter("info.json");
        info.write(json);
        info.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

 //logs the name of the device, the time and what data you would like to store in a float type.
  public void logNumericalData(String device, float data){
    JSONParser jsonParser = new JSONParser();
		try {
      //grab the runtime.
      Runtime rt = Runtime.getRuntime();
      Object obj;
      //parse the already created json file.
			obj = jsonParser.parse(new FileReader("info.json"));
            //Creates the array to be appended.
            JSONArray jsonArray = (JSONArray) obj;            
            //System.out.println(jsonArray);
            //Creates all the log data into one string.
            String jsonData = "Time: " + rt + " Data: " + data;
            HashMap<String,Object> jsonHash = new HashMap<String,Object>();
            //Adds the data to a hashmap for better visualization.
            jsonHash.put(device,jsonData);
            //creates a json object and fills it with the data in the hashmap, then that data is added to the main array in the json file.
            JSONObject info = new JSONObject(jsonHash);
            jsonArray.add(info);

            try{
              //writes into the file and appends the data.
                FileWriter file = new FileWriter("info.json");
                    file.write(jsonArray.toJSONString());
                    file.flush();
                    file.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }

            //all the exceptions required.
      catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (org.json.simple.parser.ParseException e1) {
      e1.printStackTrace();
    }
  }
  //Logs the device name, time and the data in a bool type. Same code as above.
  public void logBoolData(String device, boolean data){
    JSONParser jsonParser = new JSONParser();
		try {
      Runtime rt = Runtime.getRuntime();
      Object obj;
			obj = jsonParser.parse(new FileReader("info.json"));
            JSONArray jsonArray = (JSONArray) obj;            
            //System.out.println(jsonArray);

            String jsonData = "Time: " + rt + " Data: " + data;
            HashMap<String,Object> jsonHash = new HashMap<String,Object>();
            jsonHash.put(device,jsonData);
            JSONObject info = new JSONObject(jsonHash);
            jsonArray.add(info);

            try{
                FileWriter file = new FileWriter("info.json");
                    file.write(jsonArray.toJSONString());
                    file.flush();
                    file.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }

      catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (org.json.simple.parser.ParseException e1) {
      e1.printStackTrace();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
