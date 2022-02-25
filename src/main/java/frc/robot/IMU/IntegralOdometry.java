// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.IMU;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.util.WPIUtilJNI;

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
 
    private boolean moving(RelativeEncoder leftEncoder, RelativeEncoder rightEncoder){
        if(Math.abs(Math.floor(leftEncoder.getVelocity() * 1000) / 1000) > 0 || Math.abs(Math.floor(rightEncoder.getVelocity() * 1000) / 1000) > 0){
             return true;
        }
        else return false;
    }

    private double getChassisVelocityX(double currentTime, double accelerationX, RelativeEncoder leftEncoder, RelativeEncoder rightEncoder){
        double dt = m_previousTime >= 0 ? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;
        
        if(moving(leftEncoder, rightEncoder)){
            accumulated_velocity += (9.8 * (accelerationX)) * dt; //TODO automate inputting the calibration value
        }
        else{
            accumulated_velocity = 0;
        }
        // return -(accumulated_velocity * 39.36);
        return accumulated_velocity;
    }
    

    /**
     * Find the Chassis velocity X component given acceleration
     * @param acceleration 
     * @param leftEncoder
     * @param rightEncoder
     * @return velocity in inches / second
     */
    public double getChassisVelocityXWithTime(double accelerationX, RelativeEncoder leftEncoder, RelativeEncoder rightEncoder){
        return getChassisVelocityX(WPIUtilJNI.now() * 1.0e-6, accelerationX, leftEncoder, rightEncoder);
    }

    

    private Pose2d getPosition(double currentTime, Rotation2d currentAngle, double chassis_speedX){
        double delta_t = m_previousTime >= 0? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;

        var angle = currentAngle.plus(m_gyroOffset);
        var newPose = 
            m_currentPosition.exp(
                new Twist2d(
                    chassis_speedX * delta_t,
                    0,
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
    public Pose2d getPositionWithTime(Rotation2d gyroAngle, double chassis_speedX){
        return getPosition(WPIUtilJNI.now() * 1.0e-6, gyroAngle, chassis_speedX);
    }
}
