// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.util.WPIUtilJNI;
import frc.robot.Constants;

public class IntegralOdometry{
    private Pose2d m_currentPosition;
    private double m_previousTime = -1;

    private Rotation2d m_gyroOffset;
    private Rotation2d m_previousAngle;

    private double accumulated_velocity;
    private double calib_accum_vel;


    public IntegralOdometry(Rotation2d gyroAngle, Pose2d initialPosition){
        m_currentPosition = initialPosition;
        m_gyroOffset = m_currentPosition.getRotation().minus(gyroAngle);
        m_previousAngle = initialPosition.getRotation();
    }

    public void resetPosition(Pose2d currentPosition, Rotation2d gyroAngle){
        m_currentPosition = currentPosition;
        m_previousAngle = currentPosition.getRotation();
        m_gyroOffset = currentPosition.getRotation().minus(gyroAngle);
    }

    public Pose2d getPoseMeters(){
        return m_currentPosition;
    } 
    
    
    private double getChassisVelocity(double currentTime, double getAccel, double EncoderRate){
        double dt = m_previousTime >= 0? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;
        getAccel = getAccel - (-0.017482346496199994);
        if ( (Math.floor(Math.abs(EncoderRate) * 1000) / 1000) > 0) accumulated_velocity += (getAccel * 9.81) * dt * Constants.encoder_ratio;  
        else accumulated_velocity = 0;  
        return accumulated_velocity;
    }

    public DifferentialDriveWheelSpeeds getWheelVelocity(double vxMetersPerSecond, double omegaRadiansPerSecond){
        var chassisSpeeds = new ChassisSpeeds(vxMetersPerSecond, 0, omegaRadiansPerSecond);
        return Constants.kKinematics.toWheelSpeeds(chassisSpeeds);
    }    

    private double calibrateAccelerometer(double currentTime, double sample_time, double Accel){
        double dt = m_previousTime >= 0 ? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;
        double number_of_samples = 1.0/(sample_time);
        if(currentTime < sample_time){
            calib_accum_vel += Accel * dt;
        }
        return calib_accum_vel * number_of_samples;
    }

    
    private Pose2d getPosition(double currentTime, Rotation2d currentAngle, double getVelX){
        double delta_t = m_previousTime >= 0? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;

        var angle = currentAngle.plus(m_gyroOffset);
        Translation2d accel_vector = new Translation2d(getVelX, 0);
        var newPose = 
            m_currentPosition.exp(
                new Twist2d(
                    accel_vector.getX() * delta_t,
                    0, //must set to 0 for non holonomic drivetrains
                    angle.minus(m_previousAngle).getRadians()
                )
            );
        m_previousAngle = angle;

        m_currentPosition = new Pose2d(newPose.getTranslation(), angle);

        return m_currentPosition;
    }

    public Pose2d getPositionWithTime(Rotation2d gyroAngle, double getVelX){
        return getPosition(WPIUtilJNI.now() * 1.0e-6, gyroAngle, getVelX);
    }

    public double getChassisVelocityWithTime(double getAccel, double EncoderRate){
        return  getChassisVelocity(WPIUtilJNI.now() * 1.0e-6, getAccel, EncoderRate);
    }

    public double calibrateAccelerometerWithTime(double sample_time, double Accel){
        return calibrateAccelerometer(WPIUtilJNI.now() * 1.0e-6, sample_time, Accel);
    }
}
