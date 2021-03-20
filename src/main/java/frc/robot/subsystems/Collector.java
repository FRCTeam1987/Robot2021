// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Collector extends SubsystemBase {
  /** Creates a new Collector. */

  private final DoubleSolenoid solenoid;
  private final CANSparkMax rollerMaster;
  private final CANSparkMax rollerSlave;

  public Collector() {
    solenoid = new DoubleSolenoid(Constants.Collector.Solenoid.extend, Constants.Collector.Solenoid.retract);
    rollerMaster = new CANSparkMax(Constants.Collector.Can.motorMaster, MotorType.kBrushless);
    rollerSlave = new CANSparkMax(Constants.Collector.Can.motorSlave, MotorType.kBrushless);

    rollerMaster.restoreFactoryDefaults();
    rollerMaster.setSecondaryCurrentLimit(25);
    rollerSlave.restoreFactoryDefaults();
    rollerSlave.setSecondaryCurrentLimit(25);

    // rollerSlave.follow(rollerMaster, true);  // TODO test this
    addChild("solenoid", solenoid);
  }

  public void extend() {
    solenoid.set(Constants.Collector.Values.cylinderExtend);
  }

  public void retract() {
    solenoid.set(Constants.Collector.Values.cylinderRetract);
  }

  public void setInOut(DoubleSolenoid.Value direction) {
    this.solenoid.set(direction);
  }

  public void setRollerPercent(final double speed) {
    rollerMaster.set(speed);
    rollerSlave.set(-speed);
  }

  public void stop() {
    setRollerPercent(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // SmartDashboard.putNumber("roller current", rollerMaster.getOutputCurrent());
  }
}
