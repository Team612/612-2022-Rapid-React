// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.SimDevice.Direction;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class RomiGyro implements Gyro{
    //double data type for simulation for the angles and rates
    private SimDouble simRateX;
    private SimDouble simRateY;
    private SimDouble simRateZ;
    private SimDouble simAngleX;
    private SimDouble simAngleY;
    private SimDouble simAngleZ;
    
    //Angle offsets 
    private double angleXOffset;
    private double angleYOffset;
    private double angleZOffset;


    SimDevice gyroSim;
    //Constructor Gyro
    public RomiGyro(){
        //creating a SimDevice which is to simulate other devices that are not used by Wpilib
        //Instantiating SimDevice object that sets that object to be created (you must put in the device key in the key argument below) 
        gyroSim = SimDevice.create("Gyro:RomiGyro"); 
        if(gyroSim != null){
            gyroSim.createBoolean("init", Direction.kOutput, true);
            simRateX = gyroSim.createDouble("rate_x", Direction.kInput, 0.0);
            simRateY = gyroSim.createDouble("rate_y", Direction.kInput, 0.0);
            simRateZ = gyroSim.createDouble("rate_z", Direction.kInput, 0.0);

            simAngleX = gyroSim.createDouble("angle_x", Direction.kInput, 0.0);
            simAngleY = gyroSim.createDouble("angle_y", Direction.kInput, 0.0);
            simAngleZ = gyroSim.createDouble("angle_z", Direction.kInput, 0.0);
        }
    }

    //returning rates
    public double getRateX(){return simRateX != null ? simRateX.get() : 0.0;}
    public double getRateY(){return simRateY != null ? simRateY.get() : 0.0;}
    public double getRateZ(){return simRateZ != null ? simRateZ.get() : 0.0;}

    //returning angle rates
    public double getAngleX(){return simAngleX != null ? simAngleX.get() - angleXOffset : 0.0;}
    public double getAngleY(){return simAngleY != null ? simAngleY.get() - angleYOffset : 0.0;}
    public double getAngleZ(){return simAngleZ != null ? simAngleZ.get() - angleZOffset : 0.0;}

    //reset method
    @Override
    public void reset(){
        if(simAngleX != null && simAngleY != null && simAngleZ != null){
            angleXOffset = simAngleX.get();
            angleYOffset = simAngleY.get();
            angleZOffset = simAngleZ.get();
        }
    }

    @Override
    public void close() throws Exception {
        if (gyroSim != null) {
            gyroSim.close();
          }
    }

    @Override
    public void calibrate() {
    }

    @Override
    public double getAngle() {
        return getAngleZ();
    }

    @Override
    public double getRate() {
        return getRateZ();
    }
}
