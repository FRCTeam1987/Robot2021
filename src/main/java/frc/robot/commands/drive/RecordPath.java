// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util;
import frc.robot.subsystems.Drive;

public class RecordPath extends CommandBase {

  private static final double TIME_STEP = 0.5;

  private final Drive m_drive;
  private final Boolean m_shouldResetOdometry;
  private double m_timestamp;
  private ArrayList<Translation2d> m_path;
  private Rotation2d m_beginRotation;
  private Rotation2d m_endRotation;

  public RecordPath(final Drive drive) {
    this(drive, true);
  }

  /** Creates a new RecordPath. */
  public RecordPath(final Drive drive, final Boolean shouldResetOdometry) {
    m_drive = drive;
    m_shouldResetOdometry = shouldResetOdometry;
    m_timestamp = 0.0;
    m_path = new ArrayList<Translation2d>();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (m_shouldResetOdometry) {
      m_drive.zeroSensors();
      m_drive.resetOdometry(new Pose2d(0.0, 0.0, new Rotation2d(0)));
    }
    m_timestamp = Timer.getFPGATimestamp();
    m_path.clear();
    m_path.add(m_drive.getPose().getTranslation());
    m_beginRotation = m_drive.getPose().getRotation();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    final double currentTime = Timer.getFPGATimestamp();
    if (currentTime < m_timestamp + TIME_STEP) {
      return;
    }
    if (Util.isWithinTolerance(m_drive.getVelocityLeftCANCoder(), 0, 0.25)
      && Util.isWithinTolerance(m_drive.getVelocityRightCANCoder(), 0, 0.25)) {
      return;
    }
    m_path.add(m_drive.getPose().getTranslation());
    m_timestamp = currentTime;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_path.add(m_drive.getPose().getTranslation());
    m_endRotation = m_drive.getPose().getRotation();
    final Translation2d begin = m_path.remove(0);
    final Translation2d end = m_path.remove(m_path.size() - 1);
    System.out.println("new Pose2d(" + begin.getX() + ", " + begin.getY() + ", Rotation2d.fromDegrees(" + m_beginRotation.getDegrees() + ")),");
    System.out.println("List.of(");
    m_path.stream().forEach(t -> System.out.println("  new Translation2d(" + t.getX() + ", " + t.getY() + "),"));
    System.out.println("),");
    System.out.println("new Pose2d(" + end.getX() + ", " + end.getY() + ", Rotation2d.fromDegrees(" + m_endRotation.getDegrees() + ")),");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {
    return true;
  }
}
