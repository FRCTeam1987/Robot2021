// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootRPM extends CommandBase {
  
  final Shooter m_shooter;
  final double m_rpm;

  public ShootRPM(final Shooter shooter, final double rpm) {
    m_shooter = shooter;
    m_rpm = rpm;
    addRequirements(m_shooter);
    SmartDashboard.putNumber("RPM Test Setpoint", 0.0);
  }

  public ShootRPM(final Shooter shooter) {
    this(shooter, 0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (Math.abs(m_rpm) < 10.0) { // for testing
      m_shooter.setRPM(SmartDashboard.getNumber("RPM Test Setpoint", 0));
      return;
    }
    m_shooter.setRPM(m_rpm);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_shooter.isInRPMTolerance();
  }
}
