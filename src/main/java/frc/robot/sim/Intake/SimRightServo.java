// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sim.Intake;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Servo;

public class SimRightServo extends Servo {
  private double lastAngle = 0.0;;
  /** Creates a new SimServo. */
  public SimRightServo(int channel) {
    super(channel);
  }

  @Override
  public double getAngle(){
    if(RobotBase.isSimulation()){
      return this.lastAngle;
    }
    else{
      return super.getAngle();
    }
  }

  @Override
  public void setAngle(double ang){
    if(RobotBase.isSimulation()){
      this.lastAngle = ang;
    }
    else{
      super.set(ang);
    }
  }
  

}
