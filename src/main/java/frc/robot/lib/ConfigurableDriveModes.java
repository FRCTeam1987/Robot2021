// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.lib;

import java.util.function.Consumer;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

/**
 * Enables on-the-fly configuration of various drive behaviors.
 */
public class ConfigurableDriveModes {

  public static final String driveModeLabel = "Drive Mode";
  public static final String speedLimitLabel = "Speed Limit";
  public static enum DriveMode {
    ArcadeTriggers,
    ArcadeSticks,
    CurvatureTriggers,
    CurvatureSticks,
    TankSticks
  }

  private final DifferentialDrive m_differentialDrive;
  private final ShuffleboardTab m_tab;
  private final SendableChooser<DriveMode> m_driveModeChooser;
  private final SendableChooser<NeutralMode> m_neutralModeChooser;
  private final NetworkTableEntry m_speedLimitChooser;
  private final Consumer<NeutralMode> m_neutralModeSetter;
  private DriveMode m_selectedDriveMode;

  /**
   * Sets up configurable drive modes for a differential drive.  Makes some assumptions...
   * @param differentialDrive - The drive representing the robot's drivetrain.
   * @param neutralModeSetter - A method which will be called when setting the drive motors' neutral mode.
   */
  public ConfigurableDriveModes(final DifferentialDrive differentialDrive, final Consumer<NeutralMode> neutralModeSetter) {
    m_differentialDrive = differentialDrive;
    m_driveModeChooser = new SendableChooser<DriveMode>();
    m_neutralModeChooser = new SendableChooser<NeutralMode>();
    m_neutralModeSetter = neutralModeSetter;

    m_tab = Shuffleboard.getTab("Configure Drive");

    SendableRegistry.setName(m_driveModeChooser, "Drive Mode Selector");
    m_driveModeChooser.setDefaultOption("Arcade - Triggers", DriveMode.ArcadeTriggers);
    m_driveModeChooser.addOption("Arcade - Sticks", DriveMode.ArcadeSticks);
    m_driveModeChooser.addOption("Curvature - Triggers", DriveMode.CurvatureTriggers);
    m_driveModeChooser.addOption("Curvature - Sticks", DriveMode.CurvatureSticks);
    m_driveModeChooser.addOption("Tank - Sticks", DriveMode.TankSticks);
    m_tab.add(m_driveModeChooser);
    m_selectedDriveMode = m_driveModeChooser.getSelected();
    m_speedLimitChooser = m_tab.add("Speed Limit", 1.0).getEntry();
    SendableRegistry.setName(m_neutralModeChooser, "Neutral Mode Selector");
    m_neutralModeChooser.setDefaultOption("Brake", NeutralMode.Brake);
    m_neutralModeChooser.addOption("Coast", NeutralMode.Coast);
    m_tab.add(m_neutralModeChooser);
  }

  /**
   * The method to be called periodically which drives the robot.
   * @param xbox - The driver's XBox controller.
   */
  public void drive(final XboxController xbox) {
    switch(m_selectedDriveMode) {
      case ArcadeTriggers:
        m_differentialDrive.arcadeDrive(
          -(xbox.getTriggerAxis(Hand.kRight) - xbox.getTriggerAxis(Hand.kLeft)),
          -xbox.getX(Hand.kLeft)
        );
        return;
      case ArcadeSticks:
        m_differentialDrive.arcadeDrive(
          xbox.getY(Hand.kLeft),
          -xbox.getX(Hand.kRight)
        );
        return;
      case CurvatureTriggers:
        m_differentialDrive.curvatureDrive(
          -(xbox.getTriggerAxis(Hand.kRight) - xbox.getTriggerAxis(Hand.kLeft)),
          xbox.getX(Hand.kLeft),
          xbox.getStickButton(Hand.kLeft)
        );
        return;
      case CurvatureSticks:
        m_differentialDrive.curvatureDrive(
          xbox.getY(Hand.kLeft),
          -xbox.getX(Hand.kRight),
          xbox.getStickButton(Hand.kRight)
        );
        return;
      case TankSticks:
        m_differentialDrive.tankDrive(
          xbox.getY(Hand.kLeft),
          xbox.getY(Hand.kRight),
          true
        );
        return;
      default:
        m_differentialDrive.tankDrive(0, 0);
        return;
    }
  }

  /**
   * Get the latest value for the desired drive mode from Shuffleboard.
   * @return The desired drive mode.
   */
  public DriveMode fetchDriveMode() {
    return (DriveMode) m_driveModeChooser.getSelected();
  }

  /**
   * Get the latest value for the desired neutral mode from Shuffleboard.
   * @return The desired drive mode.
   */
  public NeutralMode fetchNeutralMode() {
    return (NeutralMode) m_neutralModeChooser.getSelected();
  }

  /**
   * Get the latest value for the desired speed limit from Shuffleboard.
   * @return The desired speed limit.
   */
  public double fetchSpeedLimit() {
    return m_speedLimitChooser.getDouble(1.0);
  }

  /**
   * Sets the desired drive mode.
   * @param driveMode - The desired drive mode.
   */
  public void setDriveMode(final DriveMode driveMode) {
    m_selectedDriveMode = driveMode;
  }

  /**
   * Sets the desired neutral mode.
   * @param neutralMode - The desired neutral mode.
   */
  public void setNeutralMode(final NeutralMode neutralMode) {
    m_neutralModeSetter.accept(neutralMode);
  }

  /**
   * Sets the desired speed limit.
   * @param limit - The desired speed limit.
   */
  public void setSpeedLimit(final double limit) {
    m_differentialDrive.setMaxOutput(limit);
  }

  /**
   * Updates the drive with all selected values.
   * Note: Call this sparingly as it will make requests to Shuffleboard. ex. once on teleop init
   */
  public void update() {
    setDriveMode(fetchDriveMode());
    setNeutralMode(fetchNeutralMode());
    setSpeedLimit(fetchSpeedLimit());
  }

}
