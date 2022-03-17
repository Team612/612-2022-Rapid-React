// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  AddressableLEDBuffer m_ledBuffer;
  AddressableLED m_led;
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  //  PneumaticsControlModule pcm = new PneumaticsControlModule();
   //Compressor compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  @Override
  public void robotInit() {

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    // pcm.clearAllStickyFaults();
    // System.out.println("your mom is a: " + pcm.checkSolenoidChannel(0));
    CameraServer.startAutomaticCapture();
    m_robotContainer = new RobotContainer();

    m_led = new AddressableLED(Constants.LED_PORT);
    m_ledBuffer = new AddressableLEDBuffer(5460);

    //do it just once cause the setlength method sucks
    m_led.setLength(5460);

    //set data
    m_led.setData(m_ledBuffer);
    m_led.start();
    
    
  }

  public void setLED(int RGB_R, int RGB_G, int RGB_B) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, RGB_R, RGB_G, RGB_B);
   }


   
   m_led.setData(m_ledBuffer);
  }

  public void extendArm(){
    for (int i = 0; i <= 255; i++) {

      final var hue = (i);
      m_led.setLength(150); //testing purposes here.
      m_ledBuffer.setRGB(i, (int) 0, hue, hue); //starts as black, as climb progresses arms turn blue
      m_led.setData(m_ledBuffer);
      m_led.start();
    }
  }

  public void retractArm(){
    for (int i = 255; i >= 0; i--){
      final var hue = (i);
      m_ledBuffer.setRGB(i, (int) 0, hue, hue); //starts at blue, then slowly progesses to black
      m_led.setData(m_ledBuffer);
      m_led.start();
    }
  }

  public void OpenServo(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
    m_ledBuffer.setRGB(i,237,255,0);
    m_led.setData(m_ledBuffer);
    m_led.start();
  }
}

  public void CloseServo(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
    m_ledBuffer.setRGB(0,255,0,0 );
    m_led.setData(m_ledBuffer);
    m_led.start();
  }
}





  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
