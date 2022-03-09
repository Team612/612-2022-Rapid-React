// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sim.Climb;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.RobotBase;

/** Add your docs here. */
public class SimClimbTalon extends WPI_TalonSRX{
    ControlMode m_mode = ControlMode.PercentOutput;
    double val = 0.0;
    public SimClimbTalon(int num){
        super(num);
    }

    @Override
    public void set(ControlMode mode, double value){
        if(RobotBase.isSimulation()){
            this.val = value;
            this.m_mode = mode;
          }
          else{
            super.set(mode, value);
          }
    }

    @Override
    public double get(){
        if(RobotBase.isSimulation()){
            return this.val;
          }
          else{
            return super.get();
          }
    }

}
