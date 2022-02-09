// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.Encoder;

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

    /**
     * Resets the position of the robot
     */

    public void resetPosition(Pose2d currentPosition, Rotation2d gyroAngle){
        m_currentPosition = currentPosition;
        m_previousAngle = currentPosition.getRotation();
        m_gyroOffset = currentPosition.getRotation().minus(gyroAngle);
    }


    /**
     * Returns position in meters of the robot
     */
    public Pose2d getPoseMeters(){
        return m_currentPosition;
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

    /**
     * Calibrates the Accelerometer over time by sampling data from the IMU and then finding the average from that
     */
    public double calibrateAccelerometerWithTime(double sample_time, double Accel){
        return calibrateAccelerometer(WPIUtilJNI.now() * 1.0e-6, sample_time, Accel);
    }


    /**
     * Checks if the robot is moving by looking at the wheel speeds.
     */
    private boolean moving(Encoder leftEncoder, Encoder rightEncoder){
        if(Math.abs(Math.floor(leftEncoder.getRate() * 1000) / 1000) > 0 || Math.abs(Math.floor(rightEncoder.getRate() * 1000) / 1000) > 0){
             return true;
        }
        else return false;
    }

    private double getChassisVelocity(double currentTime, double acceleration, Encoder leftEncoder, Encoder rightEncoder){
        double dt = m_previousTime >= 0 ? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;
        
        if(moving(leftEncoder, rightEncoder)){
            accumulated_velocity += (acceleration + 0.004700970386299997) * 9.81 * dt; //replace zero with the offset
        }
        else{
            accumulated_velocity = 0;
        }
        return accumulated_velocity;
    }

    /**
     * Find the chassis velocity over time via integrating the x component of the given acceleration
     */
    public double getChassisVelocityWithTime(double acceleration, Encoder leftEncoder, Encoder rightEncoder){
        return getChassisVelocity(WPIUtilJNI.now() * 1.0e-6, acceleration, leftEncoder, rightEncoder);
    }

    public Pose2d getPosition(double currentTime, Rotation2d currentAngle, double getVelX){
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

    /**
     * gets the position based off of wheel velocities.
     */
    public Pose2d getPositionWithTime(Rotation2d gyroAngle, double getVelX){
        return getPosition(WPIUtilJNI.now() * 1.0e-6, gyroAngle, getVelX);
    }
}
