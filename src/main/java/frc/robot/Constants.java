// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    //Spark constants
    public final static int SPARK_FL = 1;
    public final static int SPARK_FR = 2;
    public final static int SPARK_BL = 3;
    public final static int SPARK_BR = 4;

    //wheel diameter
    public static final double kWheelDiameterMeters = 0.1524;
    
    //Distance between centers of right and left wheels on robot
    public static final double kTrackWidth = 0.5969; 

    //Distance between centers of front and back wheels on robot
    public static final double kWheelBase = 0.676275; 
 
    //Feedforward gains for system dynamics 
    public static final double kS = 0.091382; 
    public static final double kV = 4.1617;  
    public static final double kA = 0.26658;
    
    //Angular gains
    public static final double kV_Angular = 0;
    public static final double kA_Angular = 0;

    public static int[] secondSolenoid = {0, 1};
    public static PneumaticsModuleType solenoidType = PneumaticsModuleType.CTREPCM;
    public static int PCM_2 = 0;
    //Converting chassis velocity into individual wheel velocities
    public static final MecanumDriveKinematics kDriveKinematics =
        new MecanumDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2)
        );           
}
