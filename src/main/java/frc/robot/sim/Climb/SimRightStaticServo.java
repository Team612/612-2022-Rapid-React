// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sim.Climb;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Servo;

public class SimRightStaticServo extends Servo {
  private double lastAngle = 0.0;;
  /** Creates a new SimServo. */
  public SimRightStaticServo(int channel) {
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
