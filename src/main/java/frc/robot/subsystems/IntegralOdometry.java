// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
/** Add your docs here. */

import javax.lang.model.util.ElementScanner6;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class IntegralOdometry{
    private BuiltInAccelerometer m_accelerometer;
    private Pose2d m_currentPosition;
    private double m_previousTime = -1;

    private Rotation2d m_gyroOffset;
    private Rotation2d m_previousAngle;

    private double accumulated_velocity;

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

    public double getVelocity(double currentTime, double getAccelX){
        double dt = m_previousTime >= 0? currentTime - m_previousTime : 0.0;
        m_previousTime = currentTime;
        double threshold = 0.02;
        boolean ismoving = getAccelX > threshold ? true : false;
        if (ismoving) {
            accumulated_velocity += (getAccelX * 9.81)*dt;
        }
        else {
            accumulated_velocity = 0;
        }

        getAccelX  = getAccelX >= 0.02 ? getAccelX : 0.0;
        return accumulated_velocity;
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

    public Pose2d getPositionWithTime(Rotation2d gyroAngle, double getVelX){
        return getPosition(WPIUtilJNI.now() * 1.0e-6, gyroAngle, getVelX);
    }
}
