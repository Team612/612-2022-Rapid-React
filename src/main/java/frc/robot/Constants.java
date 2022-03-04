// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
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
    public final static int SPARK_FL = 2;
    public final static int SPARK_FR = 1;
    public final static int SPARK_BL = 4;
    public final static int SPARK_BR = 3;

    //wheel diameter
    public static final double kWheelDiameterMeters = 0.1524;
    
    //Distance between centers of right and left wheels on robot
    public static final double kTrackWidth = 0.5969; 

    //Distance between centers of front and back wheels on robot
    public static final double kWheelBase = 0.676275; 


    public static final double kEncoderCPR = 1; 
    public static final double kGearReduction = 16;
    
    //Finding Distance per pulse
    public static final double kEncoderDistancePerPulse =
        ((kWheelDiameterMeters * Math.PI)) / (kGearReduction);
 
    //Feedforward gains for system dynamics 
    public static final double kS = 0.14638; 
    public static final double kV = 4.2124;  
    public static final double kA = 0.36348;
    
    //Angular gains
    public static final double kV_Angular = 1; // do not touch
    public static final double kA_Angular = 1; // do not touch

    //position controllers

    //have to tune manually
    public static final double kPXController = .85;
    public static final double kPYController = .5;
    public static final double kPThetaController = .5; //TODO

    
    //Velocity controllers
    public static final double kPFrontLeftVel = 4.8327; 
    public static final double kPRearLeftVel = 4.8327;
    public static final double kPFrontRightVel = 4.8327;
    public static final double kPRearRightVel = 4.8327;

    //Converting chassis velocity into individual wheel velocities
    public static final MecanumDriveKinematics kDriveKinematics =
        new MecanumDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2)
    );
    
    //trajectory constraints
    public static final int kMaxVelocityMetersPerSecond = 3;
    public static final int maxAccelerationMetersPerSecondSq = 1;
    public static final double kMaxAngularVelocity = Math.PI;
    public static final double kMaxAngularAcceleration = Math.PI;

    //angular constraints
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = 
        new TrapezoidProfile.Constraints(kMaxAngularVelocity, kMaxAngularAcceleration);

    //Feedforward 
    public static final SimpleMotorFeedforward kFeedforward =
        new SimpleMotorFeedforward(Constants.kS, Constants.kV, Constants.kA);
    
    //climb
    public static int Talon_arm = 5;
    public static double intakeArmSpeed = .5;//.38;

    public static int talon_pivot = 6;
    public static int PCM_2 = 7;
    public static int[] firstSolenoid = {1, 0}; // rev, fwd
    public static int[] secondSolenoid = {3, 2};
    public static PneumaticsModuleType solenoidType = PneumaticsModuleType.CTREPCM;
    //public static PneumaticsModuleType solenoidType = PneumaticsModuleType.REVPH;
    
    public static double setMotorSpeed = 0.69;
    public static int swings = 0;

    public static int left_servo = 0;
    public static int right_servo = 1;

    public static int[] bottom_servos = {2,3};

    public static int[] ULTRASONIC_INTAKE =  {1,2};
    public static int[] ULTRASONIC_OUTAKE =  {3,4};

    public static final double ULTRASONIC_INTAKE_THRESH = 2.0;
    public static final double ULTRASONIC_OUTTAKE_THRESH = 2.0;




    public static double height = 15.375;
    public static double length = 24.0;
    public static double pi = Math.PI;
    public static double ticks = 2048*Math.atan(length/height) * (180/pi)/360;
}

