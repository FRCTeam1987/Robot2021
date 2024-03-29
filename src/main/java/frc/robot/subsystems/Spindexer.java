// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Spindexer extends SubsystemBase {

  private final WPI_TalonSRX m_bigBlock;
  private final WPI_TalonSRX m_omni;
  private final WPI_TalonSRX m_compliant;

  /** Creates a new Spindexer. */
  public Spindexer(final WPI_TalonSRX bigBlock, final WPI_TalonSRX omni, final WPI_TalonSRX compliant) {
    m_bigBlock = bigBlock;
    m_omni = omni;
    m_compliant = compliant;
    m_bigBlock.configFactoryDefault();
    m_bigBlock.setNeutralMode(NeutralMode.Coast);
    m_bigBlock.configOpenloopRamp(0.1);
    m_omni.configFactoryDefault();
    m_omni.setNeutralMode(NeutralMode.Coast);
    m_omni.configOpenloopRamp(0.1);
    m_compliant.configFactoryDefault();
    m_compliant.setNeutralMode(NeutralMode.Coast);
    m_compliant.configOpenloopRamp(0.1);

    addChild("big block", m_bigBlock);
    addChild("omni", m_omni);
    addChild("compliant", m_compliant);
  }

  public void setBigBlock(final double speed) {
    m_bigBlock.set(speed);
  }

  public double getBigBlockCurrent() {
    return m_bigBlock.getStatorCurrent();
  }

  public double getBigBlockVoltage() {
    return m_bigBlock.getMotorOutputVoltage();
  }

  public void setCompliant(final double speed) {
    m_compliant.set(speed);
  }

  public void setOmni(final double speed) {
    m_omni.set(speed);
  }

  public void stop() {
    setBigBlock(0);
    setCompliant(0);
    setOmni(0);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // if (getBigBlockCurrent() > Constants.Spindexer.bigBlockJamCurrent && Math.abs(getBigBlockVoltage()) > 2) {
    //   DriverStation.reportError("Spindexer Jam - Current: " + getBigBlockCurrent() + ", Voltage: " + getBigBlockVoltage(), false);
    //   setBigBlock(0);
    // }
  }
}
