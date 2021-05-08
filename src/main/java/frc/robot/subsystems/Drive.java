// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.system.LinearSystem;
import edu.wpi.first.wpilibj.system.plant.DCMotor;
import edu.wpi.first.wpilibj.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.numbers.N2;
import frc.robot.Constants;
import frc.robot.Util;
import frc.robot.Constants.Drive.Controls;
import frc.robot.lib.ConfigurableDriveModes;
import frc.robot.lib.EncoderHelpers;

public class Drive extends SubsystemBase {

  private final WPI_TalonFX m_leftMaster;
  private final TalonFX m_leftSlave;
  private final CANCoder m_leftEncoder;
  private final WPI_TalonFX m_rightMaster;
  private final TalonFX m_rightSlave;
  private final CANCoder m_rightEncoder;
  private final AHRS m_gyro;
  private final DifferentialDrive m_drive;
  private final DifferentialDriveOdometry m_odometry;
  private Constants.Drive.Galactic.Color m_galacticColor;
  private int accuracyChallengeStep;
  private boolean powerPortFirstRun;

  private final ConfigurableDriveModes m_configurableDriveModes;

  private final ShuffleboardTab m_tabOdometry;
  private final NetworkTableEntry m_tabOdometryLeftDistance;
  private final NetworkTableEntry m_tabOdometryRightDistance;
  private final NetworkTableEntry m_tabOdometryRightVelocity;
  private final NetworkTableEntry m_tabOdometryLeftVelocity;
  private final NetworkTableEntry m_tabOdometryAngle;
  private final NetworkTableEntry m_tabOdometryPoseX;
  private final NetworkTableEntry m_tabOdometryPoseY;
  private final NetworkTableEntry m_tabOdometryPoseRotation;


  // Simulation
  private final LinearSystem<N2, N2, N2> m_drivetrainSystem = LinearSystemId.identifyDrivetrainSystem(
    1.98, 0.2, 1.5, 0.3
  );
  private final DifferentialDrivetrainSim m_drivetrainSimulator = new DifferentialDrivetrainSim(
    m_drivetrainSystem,
    DCMotor.getFalcon500(4),
    8,
    Constants.Drive.Values.trackWidth,
    Constants.Drive.Values.wheelDiameter / 2.0,
    null
  );


  /** Creates a new Drive. */
  public Drive(
    final WPI_TalonFX leftMaster, final TalonFX leftSlave, final CANCoder leftEncoder,
    final WPI_TalonFX rightMaster, final TalonFX rightSlave, final CANCoder rightEncoder,
    final AHRS gyro
  ) {

    m_leftMaster = leftMaster;
    m_leftSlave = leftSlave;
    m_leftEncoder = leftEncoder;
    m_rightMaster = rightMaster;
    m_rightSlave = rightSlave;
    m_rightEncoder = rightEncoder;
    m_gyro = gyro;
    m_galacticColor = Constants.Drive.Galactic.Color.DontKnow;
    powerPortFirstRun = true;

    accuracyChallengeStep = 0;

    m_leftMaster.configFactoryDefault();
    m_leftMaster.configOpenloopRamp(0.25);
    m_leftSlave.configFactoryDefault();
    m_leftSlave.follow(m_leftMaster);
    m_rightMaster.configFactoryDefault();
    m_rightMaster.configOpenloopRamp(0.25);
    m_rightSlave.configFactoryDefault();
    m_rightSlave.follow(m_rightMaster);

    m_drive = new DifferentialDrive(m_leftMaster, m_rightMaster);

    final double navxMaxWaitTime = 5.0;
    Timer navxTimer = new Timer();
    navxTimer.start();
    while(m_gyro.getByteCount() == 0 && navxTimer.get() <= navxMaxWaitTime) {
      Timer.delay(0.01);
    }

    zeroSensors();
    setCoast();
    // setBrake();

    m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getHeading()));

    addChild("left master", m_leftMaster);
    addChild("right master", m_rightMaster);
    addChild("gyro", m_gyro);
    addChild("diff drive", m_drive);

    m_configurableDriveModes = new ConfigurableDriveModes(m_drive, this::setNeutralMode);

    m_tabOdometry = Shuffleboard.getTab("Odometry");
    m_tabOdometryLeftDistance = m_tabOdometry.add("Left Distance", 0.0).getEntry();
    m_tabOdometryRightDistance = m_tabOdometry.add("Right Distance", 0.0).getEntry();
    m_tabOdometryRightVelocity = m_tabOdometry.add("Right Velocity", 0.0).getEntry();
    m_tabOdometryLeftVelocity = m_tabOdometry.add("Left Velocity", 0.0).getEntry();
    m_tabOdometryAngle = m_tabOdometry.add("Angle", 0.0).getEntry();
    m_tabOdometryPoseX = m_tabOdometry.add("Pose X", 0.0).getEntry();
    m_tabOdometryPoseY = m_tabOdometry.add("Pose Y", 0.0).getEntry();
    m_tabOdometryPoseRotation = m_tabOdometry.add("Pose Rotation", 0.0).getEntry();
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

  public void driveCurvature(final double speed, final double rotate, final boolean isQuickTurn) {
    m_drive.curvatureDrive(speed, rotate, isQuickTurn);
  }

  /**
   * Drives the robot using arcade controls.
   * 
   * @param speed The commanded forward speed.
   * @param rotate The commanded rotation.
   * @param squaredInputs True to scale input, false otherwise.
   */
  public void driveArcade(final double speed, final double rotate, final boolean squaredInputs) {
    m_drive.arcadeDrive(speed, rotate, squaredInputs);
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

  public void driveTeleopConfigurable(final XboxController xbox) {
    m_configurableDriveModes.drive(xbox);
  }

  /**
   * Gets the average distance of the two encoders.
   * 
   * @return The average of the two encoder readings.
   */
  public double getDistanceAverage() {
    return (getDistanceLeft() + getDistanceRight()) / 2.0;
  }

  public double getDistanceCANCoder(final double position) {
    return EncoderHelpers.ticksToDistance(position, 360.0, Constants.Drive.Values.wheelCircumference);
  }

  public double getDistanceLeftCANCoder() {
    return -getDistanceCANCoder(m_leftEncoder.getPosition());
  }

  /**
   * Gets the left wheel distance traveled.
   * 
   * @return Gets the distance traveled by the left wheel.
   */
  public double getDistanceLeft() {
    return -getEncoderDistance(m_leftMaster.getSensorCollection());
  }

  public double getDistanceRightCANCoder() {
    return getDistanceCANCoder(m_rightEncoder.getPosition());
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

  public double getAngle() {
    return m_gyro.getAngle();
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
      getVelocityLeftCANCoder(),
      getVelocityRightCANCoder()
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

  private double getWheelVelocityCANCoder(final double velocity) {
    return velocity / 360.0 * Constants.Drive.Values.wheelCircumference;
  }

  /** Gets the linear velocity of the left wheel. */
  public double getVelocityLeft() {
    return -getWheelVelocity(m_leftMaster.getSensorCollection());
  }

  public double getVelocityLeftCANCoder() {
    return -getWheelVelocityCANCoder(m_leftEncoder.getVelocity());
  }

  /** Gets the linear velocity of the right wheel. */
  public double getVelocityRight() {
    return getWheelVelocity(m_rightMaster.getSensorCollection());
  }

  public double getVelocityRightCANCoder() {
    return getWheelVelocityCANCoder(m_rightEncoder.getVelocity());
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
    m_leftEncoder.setPosition(0);
    m_rightMaster.setSelectedSensorPosition(0);
    m_rightEncoder.setPosition(0);
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
    final double leftDistance = getDistanceLeftCANCoder();
    final double rightDistance = getDistanceRightCANCoder();
    m_odometry.update(
      Rotation2d.fromDegrees(m_gyro.getAngle()),
      leftDistance,
      rightDistance
    );

    Pose2d currentPose = m_odometry.getPoseMeters();
    // m_tabOdometryLeftDistance.setNumber(getDistanceLeftCANCoder());
    // m_tabOdometryRightDistance.setNumber(getDistanceRightCANCoder());
    // m_tabOdometryRightVelocity.setNumber(getVelocityRightCANCoder());
    // m_tabOdometryLeftVelocity.setNumber(getVelocityLeftCANCoder());
    m_tabOdometryAngle.setNumber(getAngle());
    m_tabOdometryPoseX.setNumber(currentPose.getX());
    m_tabOdometryPoseY.setNumber(currentPose.getY());
    m_tabOdometryPoseRotation.setNumber(currentPose.getRotation().getDegrees());
  }

  public void setAccuracyChallengeStep(Integer index) {
    this.accuracyChallengeStep = index;
  }
  public int getAccuracyChallengeStep() {
    return this.accuracyChallengeStep;
  }

  public void teleopInit() {
    m_configurableDriveModes.update();
    powerPortFirstRun = true;
    setBrake();
  }

  // Galactic Search Path Stuff

  public boolean hasGalacticBeenDone() {
    return m_galacticColor != Constants.Drive.Galactic.Color.DontKnow;
  }

  public boolean isGalacticRed() {
    return m_galacticColor == Constants.Drive.Galactic.Color.Red;
  }

  public boolean isGalacticBlue() {
    return m_galacticColor == Constants.Drive.Galactic.Color.Blue;
  }

  public void setGalacticColor(Constants.Drive.Galactic.Color color) {
    m_galacticColor = color;
  }

  public void determineGalacticColor() {
    boolean isBlue = Util.isWithinTolerance(m_gyro.getAngle(), Constants.Drive.Galactic.blueHeading, Constants.Drive.Galactic.headingTolerance);
    setGalacticColor(isBlue ? Constants.Drive.Galactic.Color.Blue : Constants.Drive.Galactic.Color.Red);
  }

  public boolean isPowerPortFirstRun() {
    return powerPortFirstRun;
  }

  public void setPowerPortFirstRun(boolean firstRun) {
    powerPortFirstRun = firstRun;
  }
}
