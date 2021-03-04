// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.EncoderHelpers;

public class Drive extends SubsystemBase {

  private final WPI_TalonFX m_leftMaster;
  private final TalonFX m_leftSlave;
  private final WPI_TalonFX m_rightMaster;
  private final TalonFX m_rightSlave;
  // private final AHRS m_gyro;
  private final ADXRS450_Gyro m_gyro;
  private final DifferentialDrive m_drive;
  private final DifferentialDriveOdometry m_odometry;

  /** Creates a new Drive. */
  public Drive(final WPI_TalonFX leftMaster, final TalonFX leftSlave, final WPI_TalonFX rightMaster, final TalonFX rightSlave, final ADXRS450_Gyro gyro) {
    m_leftMaster = leftMaster;
    m_leftSlave = leftSlave;
    m_rightMaster = rightMaster;
    m_rightSlave = rightSlave;
    m_gyro = gyro;

    m_leftMaster.configFactoryDefault();
    m_leftMaster.configOpenloopRamp(0.1);
    m_leftSlave.configFactoryDefault();
    m_leftSlave.follow(m_leftMaster);
    m_rightMaster.configFactoryDefault();
    m_rightMaster.configOpenloopRamp(0.1);
    m_rightSlave.configFactoryDefault();
    m_rightSlave.follow(m_rightMaster);

    m_drive = new DifferentialDrive(m_leftMaster, m_rightMaster);

    zeroSensors();
    setCoast();

    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

    addChild("left master", m_leftMaster);
    addChild("right master", m_rightMaster);
    addChild("gyro", m_gyro);
    addChild("diff drive", m_drive);
  }

  /**
   * Drives the robot using arcade controls.
   * 
   * @param speed The commanded forward speed.
   * @param rotate The commanded rotation.
   */
  public void driveArcade(final double speed, final double rotate) {
    m_drive.arcadeDrive(speed, rotate);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   * 
   * @param left The commanded left output voltage.
   * @param right The commanded right output voltage.
   */
  public void driveTankVolts(final double left, final double right) {
    m_leftMaster.setVoltage(-left);
    m_rightMaster.setVoltage(right);
    m_drive.feed();
  }

  public void driveTank(final double left, final double right) {
    m_drive.tankDrive(left, right);
  }

  public void driveTank(final double left, final double right, final boolean squareInputs) {
    m_drive.tankDrive(left, right, squareInputs);
  }

  /**
   * Gets the average distance of the two encoders.
   * 
   * @return The average of the two encoder readings.
   */
  public double getDistanceAverage() {
    return (getDistanceLeft() + getDistanceRight()) / 2.0;
  }

  /**
   * Gets the left wheel distance traveled.
   * 
   * @return Gets the distance traveled by the left wheel.
   */
  public double getDistanceLeft() {
    return -getEncoderDistance(m_leftMaster.getSensorCollection());
  }

  /**
   * Gets the right wheel distance traveled.
   * 
   * @return Gets the distance traveled by the right wheel.
   */
  public double getDistanceRight() {
    return getEncoderDistance(m_rightMaster.getSensorCollection());
  }

  /**
   * Gets the distance traveled for a wheel.
   * 
   * @param sensorCollection The sensor collection for the motor driving the desired wheel.
   * @return The linear distance traveled for the desired wheel.
   */
  private double getEncoderDistance(final TalonFXSensorCollection sensorCollection) {
    return EncoderHelpers.ticksToDistance(
      sensorCollection.getIntegratedSensorPosition(),
      Constants.Drive.Values.encoderTicksPerRevolution,
      Constants.Drive.Values.wheelCircumference,
      Constants.Drive.Values.postEncoderGearing
    );
  }

  /**
   * Returns the heading of the robot.
   * 
   * @return The robot's heading in degrees, from -180 to 180;
   */
  public double getHeading() {
    return -m_gyro.getRotation2d().getDegrees();
  }

  /**
   * Gets the current estimated pose of the robot.
   * 
   * @return The current estimated pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the turn rate of the robot.
   * 
   * @return The turn rate of the robot, in degrees per second.
   */
  public double getTurnRate() {
    return -m_gyro.getRate();
  }

  /**
   * Gets the current wheel speeds of the robot.
   * 
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
      getVelocityLeft(),
      getVelocityRight()
    );
  }

  /**
   * Gets the linear velocity of a wheel.
   * 
   * @param sensorCollection The sensor collector for the desired motor.
   * @return The linear velocity of the wheel driven by the motor.
   */
  private double getWheelVelocity(final TalonFXSensorCollection sensorCollection) {
    return EncoderHelpers.ctreVelocityToLinearVelocity(
      sensorCollection.getIntegratedSensorVelocity(),
      Constants.Drive.Values.encoderTicksPerRevolution,
      Constants.Drive.Values.wheelCircumference,
      Constants.Drive.Values.postEncoderGearing
    );
  }

  /** Gets the linear velocity of the left wheel. */
  public double getVelocityLeft() {
    return -getWheelVelocity(m_leftMaster.getSensorCollection());
  }

  /** Gets the linear velocity of the right wheel. */
  public double getVelocityRight() {
    return getWheelVelocity(m_rightMaster.getSensorCollection());
  }

  /**
   * Resets the odometry to the specified pose.
   * 
   * @param pose The pose of which to set the odometry.
   */
  public void resetOdometry(final Pose2d pose) {
    zeroEncoders();
    m_odometry.resetPosition(pose, Rotation2d.fromDegrees(m_gyro.getAngle()));
  }

  /**
   * Sets the neutral mode of the drive motors.
   * 
   * @param neutralMode The desired neutral mode. (ex. brake or coast)
   */
  private void setNeutralMode(final NeutralMode neutralMode) {
    m_leftMaster.setNeutralMode(neutralMode);
    m_leftSlave.setNeutralMode(neutralMode);
    m_rightMaster.setNeutralMode(neutralMode);
    m_rightSlave.setNeutralMode(neutralMode);
  }

  /** Sets the neutral mode of the drive motors to brake. */
  public void setBrake() {
    setNeutralMode(NeutralMode.Brake);
  }

  /** Sets the neutral mode of the drive motors to coast. */
  public void setCoast() {
    setNeutralMode(NeutralMode.Coast);
  }

  /** Zeroes the left and right encoders. */
  public void zeroEncoders() {
    m_leftMaster.setSelectedSensorPosition(0);
    m_rightMaster.setSelectedSensorPosition(0);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /** Zeroes all sensors on the drive. */
  public void zeroSensors() {
    zeroEncoders();
    zeroHeading();
  }

  @Override
  public void periodic() {
    m_odometry.update(
      Rotation2d.fromDegrees(m_gyro.getAngle()),
      getDistanceLeft(),
      getDistanceRight()
    );

    if (SmartDashboard.getNumber("Log Level", 0) > 1) {
      SmartDashboard.putNumber("Left Distance", getDistanceLeft());
      SmartDashboard.putNumber("Right Distance", getDistanceRight());
      SmartDashboard.putNumber("Rotation 2D", Rotation2d.fromDegrees(getHeading()).getDegrees());
      SmartDashboard.putNumber("angle", Math.IEEEremainder(m_gyro.getAngle(), 360));
      SmartDashboard.putNumber("Pose X", m_odometry.getPoseMeters().getX());
      SmartDashboard.putNumber("Pose Y", m_odometry.getPoseMeters().getY());
      SmartDashboard.putNumber("Pose Rotation", m_odometry.getPoseMeters().getRotation().getDegrees());
    }
  }
}
