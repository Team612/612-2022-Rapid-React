// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
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
     * @param currentPosition takes in current position
     * @param gyroAngle takes in the gyro angle
     */
    public void resetPosition(Pose2d currentPosition, Rotation2d gyroAngle){
        m_currentPosition = currentPosition;
        m_previousAngle = currentPosition.getRotation();
        m_gyroOffset = currentPosition.getRotation().minus(gyroAngle);
    }


    private double calibrateAccelerometer(double currentTime, double sample_time, double accel){
        double dt = m_previousTime >= 0 ? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;
        
        double number_of_samples = (sample_time);
        if(currentTime < sample_time){
            calib_accum_vel += accel * dt;
            return 0.0;
        }
        else{
            return calib_accum_vel / number_of_samples;
        }
    }

    /**
     * Calibrates the Accelerometer
     * @param sample_time sample time inputed
     * @param accel inputted acceleration
     * @return calibrated value
     */

     public double calibrateAccelerometerWithTime(double sample_time, double accel){
        return calibrateAccelerometer(WPIUtilJNI.now() * 1.0e-6, sample_time, accel);
    }

    
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
            accumulated_velocity += (9.81 * (acceleration + 0.015983020694700003)) * dt; //TODO automate inputting the calibration value
        }
        else{
            accumulated_velocity = 0;
        }
        return -(accumulated_velocity * 39.36);
    }

    /**
     * Find the Chassis velocity X component given acceleration
     * @param acceleration 
     * @param leftEncoder
     * @param rightEncoder
     * @return velocity in inches / second
     */
    public double getChassisVelocityWithTime(double acceleration, Encoder leftEncoder, Encoder rightEncoder){
        return getChassisVelocity(WPIUtilJNI.now() * 1.0e-6, acceleration, leftEncoder, rightEncoder);
    }

    private Pose2d getPosition(double currentTime, Rotation2d currentAngle, double chassis_speed){
        double delta_t = m_previousTime >= 0? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;

        var angle = currentAngle.plus(m_gyroOffset);
        var newPose = 
            m_currentPosition.exp(
                new Twist2d(
                    chassis_speed * delta_t,
                    0, //TODO change for mecanum drive 
                    angle.minus(m_previousAngle).getRadians()
                )
            );
        m_previousAngle = angle;
        m_currentPosition = new Pose2d(newPose.getTranslation(), angle);
        return m_currentPosition;
    }

    /**
     * calculates displacement 
     * @param gyroAngle
     * @param chassis_speed
     * @return displacement in inches
     */
    public Pose2d getPositionWithTime(Rotation2d gyroAngle, double chassis_speed){
        return getPosition(WPIUtilJNI.now() * 1.0e-6, gyroAngle, chassis_speed);
    }
}
