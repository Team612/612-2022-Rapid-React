// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.sensors.RomiGyro;

public class IntegralOdometry{
    private Pose2d m_currentPosition;
    private double m_previousTime = -1;
    private Rotation2d m_gyroOffset;
    private Rotation2d m_previousAngle;

    private final DifferentialDriveKinematics m_kinematics;

    private double accumulated_velocity;
    private double calib_accum_vel;

    public IntegralOdometry(DifferentialDriveKinematics kinematics, Rotation2d gyroAngle, Pose2d initialPosition){
        m_kinematics = kinematics;
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
        
        double number_of_samples = (sample_time);
        if(currentTime < sample_time){
            calib_accum_vel += Accel * dt;
            return 0.0;
        }
        else{
            return calib_accum_vel / number_of_samples;
        }
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
            accumulated_velocity += (9.81 * (acceleration + 0.0175441691727)) *dt; //replace zero with the offset
        }
        else{
            accumulated_velocity = 0;
        }
        return accumulated_velocity * 39.36;
    }

    /**
     * Find the chassis velocity over time via integrating the x component of the given acceleration
     */
    public double getChassisVelocityWithTime(double acceleration, Encoder leftEncoder, Encoder rightEncoder){
        return getChassisVelocity(WPIUtilJNI.now() * 1.0e-6, acceleration, leftEncoder, rightEncoder);
    }

    
    public double[] toWheelSpeeds(double ChassisSpeedX, double trackwidth, RomiGyro gyro){
        return new double[] 
        {ChassisSpeedX - trackwidth / 2 * gyro.getRate(), 
        ChassisSpeedX + trackwidth / 2 * gyro.getRate()};
    }

    private Pose2d getPosition(double currentTime, Rotation2d currentAngle, DifferentialDriveWheelSpeeds wheelSpeeds){
        double delta_t = m_previousTime >= 0? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;

        var angle = currentAngle.plus(m_gyroOffset);
        var chassisState = m_kinematics.toChassisSpeeds(wheelSpeeds);
        var newPose = 
            m_currentPosition.exp(
                new Twist2d(
                    chassisState.vxMetersPerSecond * delta_t,
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
    public Pose2d getPositionWithTime(Rotation2d gyroAngle, DifferentialDriveWheelSpeeds wheelSpeeds){
        return getPosition(WPIUtilJNI.now() * 1.0e-6, gyroAngle, wheelSpeeds);
    }
}
