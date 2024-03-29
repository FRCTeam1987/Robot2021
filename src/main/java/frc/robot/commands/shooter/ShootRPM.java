// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootRPM extends CommandBase {
  
  final Shooter m_shooter;
  DoubleSupplier m_getRpm = null;
  NetworkTableEntry m_rpmSetpoint = null;
  double m_rpm;

  public ShootRPM(final Shooter shooter, final double rpm) {
    m_shooter = shooter;
    m_rpm = rpm;
    addRequirements(m_shooter);
  }

  public ShootRPM(final Shooter shooter, final NetworkTableEntry rpmSetpoint) {
    m_shooter = shooter;
    m_rpmSetpoint = rpmSetpoint;
    addRequirements(m_shooter);
  }

  public ShootRPM(final Shooter shooter, final DoubleSupplier getRpm) {
    m_shooter = shooter;
    m_getRpm = getRpm;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // if (m_getRpm != null) {
    //   m_shooter.setRPM(m_getRpm.getAsDouble());
    //   return;
    // }
    if (m_rpmSetpoint == null) {
      m_shooter.setRPM(m_rpm);
      return;
    }
    m_shooter.setRPM((double) m_rpmSetpoint.getNumber(0.0));
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
