// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableInstance;


public class LimeLight extends SubsystemBase {
  public static final String LIMELIGHT = "limelight";
  /** Creates a new LimeLight. */
  public LimeLight() {
    // NetworkTableInstance.getDefault().getTable(LIMELIGHT).getEntry("pipeline").setNumber(9);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getYAxis(){
    return NetworkTableInstance.getDefault().getTable(LIMELIGHT).getEntry("ty").getDouble(0);
  }

  public double getXAxis(){
    return NetworkTableInstance.getDefault().getTable(LIMELIGHT).getEntry("tx").getDouble(0);
  }

  public double getVisible(){
    return NetworkTableInstance.getDefault().getTable(LIMELIGHT).getEntry("tv").getDouble(0);
  }

  public boolean canSeeTarget() {
    return getVisible() == 1;
  }

  public void turnOnLEDs(){
    NetworkTableInstance.getDefault().getTable(LIMELIGHT).getEntry("ledMode").setNumber(3);
  }

  public void turnOffLEDs(){
    NetworkTableInstance.getDefault().getTable(LIMELIGHT).getEntry("ledMode").setNumber(1);
  }
}
