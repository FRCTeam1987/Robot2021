// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;

public class FindTrackWidth extends CommandBase {

  private final Drive m_drive;

  /** Creates a new FindTrackWidth. */
  public FindTrackWidth(final Drive drive) {
    m_drive = drive;
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.setBrake();
    m_drive.zeroSensors();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.driveTank(-0.35, 0.35);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.driveTank(0, 0);
    final double angle = m_drive.getAngle();
    final double leftDistance = m_drive.getDistanceLeft();
    final double rightDistance = m_drive.getDistanceRight();
    final double headingRotations = angle / 360.0;
    final double leftCircumference = leftDistance / headingRotations;
    final double rightCircumference = rightDistance / headingRotations;
    final double averageCircumference = (Math.abs(leftCircumference) + Math.abs(rightCircumference)) / 2.0;
    final double leftTrackWidth = leftCircumference / Math.PI;
    final double rightTrackWidth = rightCircumference / Math.PI;
    final double avgTrackWidth = averageCircumference / Math.PI;

    SmartDashboard.putNumber("Track Angle", angle);
    SmartDashboard.putNumber("Track Rotations", headingRotations);
    SmartDashboard.putNumber("Left Track Width", leftTrackWidth);
    SmartDashboard.putNumber("Right Track Width", rightTrackWidth);
    SmartDashboard.putNumber("Avg Track Width", avgTrackWidth);
    m_drive.setCoast();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
