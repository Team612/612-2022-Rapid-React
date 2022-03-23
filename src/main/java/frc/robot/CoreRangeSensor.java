// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.*;

/** Add your docs here. */
public class CoreRangeSensor {
    
    private final int deadband = 0; 
    private final int uselessAddress = 0x03, ultra_port = 0x3a, ultraReg = 0x04, irReg = 0x05;
    private I2C ultra;

    public CoreRangeSensor(){
        ultra = new I2C(Port.kOnboard, ultra_port >> 1 | 0x00);
        ultra.write(uselessAddress, 1); 
    }

    public byte getUltraValue(){
        byte[] ultraVal = new byte[1];
        if(ultra.read(ultraReg, 1, ultraVal)){
            return ultraVal[0];
        }
        else{
            return -1;
        }
    }
    public byte getIRValue(){
        byte[] iRVal = new byte[1];
        if(ultra.read(irReg, 1, iRVal)){
            return iRVal[0];
        }
        else{
            return -1;
        }
    }
    public int getDistanceVal() { // switches between ultrasonic and ir values, depending on distance        
        if (getUltraValue() != -1) {
            if(getUltraValue() > 5){
                return getUltraValue();
            }
            else{
                return getIRValue();
            }

        }
        return -1;
    }
}