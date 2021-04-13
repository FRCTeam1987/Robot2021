// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.lib.InterpolatingDouble;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootLimeLight extends CommandBase {

  private final Shooter m_shooter;
  private final LimeLight m_limelight;

  public ShootLimeLight(final Shooter shooter, final LimeLight limelight) {
    m_shooter = shooter;
    m_limelight = limelight;
  
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_limelight.turnOnLEDs();
    final double y = m_limelight.getYAxis();
    final double rpm = Constants.Shooter.Targeting.kDistanceToShooterSpeedClose.getInterpolated(new InterpolatingDouble(y)).value;
    final double rpmHangar = rpm + 50.0;
    m_shooter.setRPM(rpmHangar);
    SmartDashboard.putNumber("Shooting TY", y);
    SmartDashboard.putNumber("Shooting RPM", rpm);
  }

  @Override
  public boolean isFinished() {
    return m_shooter.isInRPMTolerance();
  }

  @Override
  public void end(boolean interrupted) {
    m_limelight.turnOffLEDs();
  }
}
