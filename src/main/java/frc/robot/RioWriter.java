// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RioWriter{
    public final static File file = new File("/tmp/test.json");
    // public final static File file = new File("C:\\Users\\Srinvas\\Desktop\\GitHub\\612-2022-Rapid-React\\src\\main\\java\\frc\\robot\\commands\\test.json");
    
    public static BufferedWriter writer;

    public static void write(String text){
        try{
          if(writer == null){
            writer = new BufferedWriter(new FileWriter(file, false)); //buffered writer is more efficient
          }
          writer.write(text);      
          writer.flush(); //flushes the buffer
        }
        catch(IOException e){
          e.printStackTrace();
        }
      }
    
      public static void closeFile(){
        try{
          if(writer != null){
            writer.close();
          }
        }
        catch(IOException e){
          e.printStackTrace();
        }
        writer = null;
      }
    
      public static void deleteFile() throws Exception{
        if(writer != null){
            file.delete();
        }
        else{
            throw new Exception("File does not exist, cannot delete non-existant file");
        }
      }

}   
