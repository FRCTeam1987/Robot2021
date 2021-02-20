// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Collector extends SubsystemBase {
  /** Creates a new Collector. */

private DoubleSolenoid solenoid; 

  public Collector() {
    // Default DoubleSolenoid PCM_ID is 0
    solenoid = new DoubleSolenoid(Constants.Collector.Solenoid.extend, Constants.Collector.Solenoid.retract);
  }

  public void setInOut(DoubleSolenoid.Value direction) {
    this.solenoid.set(direction);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
