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

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Util;
import frc.robot.lib.InterpolatingDouble;

public class Shooter extends SubsystemBase {

  private final WPI_TalonFX m_master;
  private final TalonFX m_slave1;
  private final TalonFX m_slave2;
  private final TalonFX m_slave3;
  private double m_rpmSetPoint;
  private DoubleSolenoid m_solenoidLock;
  private DoubleSolenoid m_solenoidPto;

  /** Creates a new Shooter. */
  public Shooter(final WPI_TalonFX master, final TalonFX slave1, final TalonFX slave2, final TalonFX slave3) {
    m_master = master;
    m_slave1 = slave1;
    m_slave2 = slave2;
    m_slave3 = slave3;
    m_solenoidLock = new DoubleSolenoid(Constants.Shooter.SolenoidLock.extend, Constants.Shooter.SolenoidLock.retract);
    m_solenoidPto = new DoubleSolenoid(Constants.Shooter.SolenoidPto.extend, Constants.Shooter.SolenoidPto.retract);

    m_master.configFactoryDefault();
    m_master.configOpenloopRamp(1.5);
    m_master.setNeutralMode(NeutralMode.Coast);
    m_master.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 10);
    m_master.configClosedloopRamp(0.5);
    m_master.config_kF(0, 0.052); //get started umf (increases the actual base rpm exponentially or something) was.052 old BAT, new bat .0498, then was .055 for 3550 rpm, then .052
    m_master.config_kP(0, 0.3); //p = push and oscillating once it gets there
    m_master.config_kI(0, 0.0);
    m_master.config_kD(0, 0.0); //d =  dampening for the oscillation

    m_slave1.configFactoryDefault();
    m_slave1.setNeutralMode(NeutralMode.Coast);
    m_slave1.follow(master);
    m_slave1.setInverted(TalonFXInvertType.OpposeMaster);
    m_slave2.configFactoryDefault();
    m_slave2.setNeutralMode(NeutralMode.Coast);
    m_slave2.follow(master);
    m_slave2.setInverted(TalonFXInvertType.FollowMaster);
    m_slave3.configFactoryDefault();
    m_slave3.setNeutralMode(NeutralMode.Coast);
    m_slave3.follow(master);
    m_slave3.setInverted(TalonFXInvertType.OpposeMaster);

    stop();

    lockHood();
    disengagePto();

    addChild("master", master);
    addChild("Lock", m_solenoidLock);
    addChild("PTO", m_solenoidPto);
  }

  public void lockHood() {
    m_solenoidLock.set(Constants.Shooter.Values.cylinderExtend);
  }
  
  public void unlockHood() {
    m_solenoidLock.set(Constants.Shooter.Values.cylinderRetract);

  }

  public void engagePto() {
    m_solenoidPto.set(Constants.Shooter.Values.cylinderRetract);
  }

  public void disengagePto() {
    m_solenoidPto.set(Constants.Shooter.Values.cylinderExtend);
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

  public boolean isHoodConfigedFar() {
    return true;
  }

  public double getRPMFromLimelight() {
    final double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    return Constants.Shooter.Targeting.kDistanceToShooterSpeedClose.getInterpolated(new InterpolatingDouble(ty)).value;
  }

  public void setRPM(final double rpm) {
    double velocity = rpm * Constants.Shooter.ticksPerRotation / Constants.Shooter.milliPerMin; //1,000ms per sec, but robot cares about per 100ms, so then 60 sec/min 
    if (Util.isWithinTolerance(velocity, 0, 10)) {
      m_master.set(TalonFXControlMode.PercentOutput, 0);
    } else {
      m_master.set(TalonFXControlMode.Velocity, velocity);
    }
    m_rpmSetPoint = rpm;
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
    // if (SmartDashboard.getNumber("Log Level", 0) > 1) {
      SmartDashboard.putNumber("Shooter Actual RPM", getRPM());
    // }
  }

  // Has the shooter shot all known balls?
  public boolean hasShotAllBalls() {
    return true;
  }

  // Called when a ball is detected to have been shot
  public void shotBall() {

  }

  // Called to set the number of balls known to be in the robot
  public void setBallsInShooter(int balls) {

  }

  // Returns true when a ball is detecting to be shot
  public boolean isBallShooting() {
    return m_master.getSupplyCurrent() > 10.0;  // Magic number - needs to be determined
  }

  public void setPercentRamp(final double duration) {
    m_master.configOpenloopRamp(duration);
  }
}
