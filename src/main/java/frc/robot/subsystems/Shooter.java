// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Util;

public class Shooter extends SubsystemBase {

  private final WPI_TalonFX m_master;
  private final TalonFX m_slave;
  private double m_rpmSetPoint;

  /** Creates a new Shooter. */
  public Shooter(final WPI_TalonFX master, final TalonFX slave) {
    m_master = master;
    m_slave = slave;

    m_master.configFactoryDefault();
    m_master.configOpenloopRamp(0.5);
    // master.setInverted(true);
    m_master.setNeutralMode(NeutralMode.Coast);
    m_master.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
    m_master.configClosedloopRamp(0.5);
    m_master.configOpenloopRamp(0.1);
    m_master.config_kF(0, 0.0513); //get started umf (increases the actual base rpm exponentially or something) was.052 old BAT, new bat .0498, then was .055 for 3550 rpm, then .052
    m_master.config_kP(0, 0.25); //p = push and oscillating once it gets there
    m_master.config_kI(0, 0.0);
    m_master.config_kD(0, 0.0); //d =  dampening for the oscillation

    m_slave.configFactoryDefault();
    m_slave.follow(master);
    m_slave.setInverted(TalonFXInvertType.OpposeMaster);

    stop();

    addChild("master", master);
  }

  public boolean canSeeTarget(double visibility) {
    return visibility == 1;
  }

  public double getAngleError(double angleError, double visibility){
    if (!canSeeTarget(visibility)) {
      return 100;
    }
    return angleError;
  }

  public boolean isOnTarget(double angleError, double visibility) {
    return Util.isWithinTolerance(getAngleError(angleError, visibility), 0, Constants.Shooter.angleTolerance);
  }

  /**
   * Determines whether or not the shooter should start spinning up to the desired RPM.
   * @return True if the shooter is aimed near the target. False otherwise.
   */
  public boolean shouldSpinUp(double angleError, double visibility) {
    return Util.isWithinTolerance(getAngleError(angleError, visibility), 0, Constants.Shooter.angleErrorToSpinUp);
  }

  /**
   * Determines whether or not the shooter RPM is within tolerance of the setpoint.
   * @return True if the flywheel is running at a RPM close to the setpoint. False otherwise.
   */
  public boolean isInRPMTolerance() {
    return Util.isWithinTolerance(getRPM(), m_rpmSetPoint, Constants.Shooter.rpmTolerance);
  }

  public double getRPM(){
    return m_master.getSensorCollection().getIntegratedSensorVelocity() / Constants.Shooter.ticksPerRotation * Constants.Shooter.milliPerMin * Constants.Shooter.reduction;
  }

  public double getRPMSetPoint() {
    return m_rpmSetPoint;
  }

  public double getRPMFromLimelight() {
    return 0;
  }

  public void setRPM(final double rpm) {
    //SmartDashboard.putNumber("(attemped) shooter rpm setpoint", rpm);
    //if (Util.isWithinTolerance(rpm, m_rpmSetPoint, Constants.Shooter.rpmTolerance)){
      //return;
    //}
    //double velocity = EncoderHelpers.rpmToCtreVelocity(rpm, Constants.falconTicksPerRevolution);
    double velocity = rpm * Constants.Shooter.ticksPerRotation / Constants.Shooter.milliPerMin; //1,000ms per sec, but robot cares about per 100ms, so then 60 sec/min 
    m_master.set(TalonFXControlMode.Velocity, velocity);
    m_rpmSetPoint = rpm;
    //SmartDashboard.putNumber("(actual) shooter rpm setpoint", rpm);
  }

  public void setPercent(double percent) {
    m_master.set(percent);
    m_rpmSetPoint = -9999;
  }

  public void stop() {
    setPercent(0);
  }

  @Override
  public void periodic() {
    if (SmartDashboard.getNumber("Log Level", 0) > 1) {
      SmartDashboard.putNumber("Shooter Actual RPM", getRPM());
    }
  }
}
