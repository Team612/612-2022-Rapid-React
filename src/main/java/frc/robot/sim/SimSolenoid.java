// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sim;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotBase;


/** Add your docs here. */
public class SimSolenoid extends DoubleSolenoid{
    private Value lastSet = Value.kOff;
    
    public SimSolenoid(int port, PneumaticsModuleType solenoidType, int forward, int reverse){
        super(port, solenoidType, forward, reverse);
    }

    @Override
    public Value get(){
        if(RobotBase.isSimulation()){
            return this.lastSet;
        }
        else{
            return super.get();
        }
    }

    @Override
    public void set(Value val){
        if(RobotBase.isSimulation()){
            this.lastSet = val;
        }
        else{
            super.set(val);
        }
    }


}
