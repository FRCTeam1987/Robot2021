// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import frc.robot.commands.collector.CollectorInOut;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.drive.TeleopDrive;
import frc.robot.subsystems.Collector;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;

import java.util.List;

import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private Trajectory slalomTrajectory;
  private Trajectory bounceLeg1, bounceLeg2;

  private final XboxController driver;

  // Allocate Subsystems
  private final Drive m_drive = new Drive(
    new WPI_TalonFX(Constants.Drive.Can.leftMaster),
    new TalonFX(Constants.Drive.Can.leftSlave),
    new WPI_TalonFX(Constants.Drive.Can.rightMaster),
    new TalonFX(Constants.Drive.Can.rightSlave),
    new AHRS()
  );
  private final Collector m_collector = new Collector();

  private final SendableChooser<Command> chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    driver = new XboxController(Constants.OI.Xbox.driverID);

    // Configure the button bindings
    configureButtonBindings();

    configureDefaultCommands();
    configureShuffleboard();

    slalomTrajectory = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Slalom.wpilib.json");
    bounceLeg1 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Leg1.wpilib.json");
    bounceLeg2 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Leg2.wpilib.json");
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }

  private void configureDefaultCommands() {
    m_drive.setDefaultCommand(new TeleopDrive(m_drive, driver));
  }
  
  private void configureShuffleboard() {
    SmartDashboard.putData("Extend Collector", new CollectorInOut(m_collector, Constants.Collector.Values.cylinderExtend));
    SmartDashboard.putData("Retract Collector", new CollectorInOut(m_collector, Constants.Collector.Values.cylinderRetract));

    chooser.addOption("Manual", DrivePathHelpers.createOnBoardDrivePathCommand(
      m_drive,
      new Pose2d(0, 0, new Rotation2d(0)), // Start Pose
      List.of(
        new Translation2d(1.0, 1.0),
        new Translation2d(2.0, 1.0),
        new Translation2d(3.0, 0.0)
      ),
      new Pose2d(4.0, 1.0, new Rotation2d(Math.PI/2.0)), // End Pose
      false));
    chooser.addOption("Slalom", DrivePathHelpers.createDrivePathCommand(m_drive, slalomTrajectory));
    chooser.addOption("Bounce", new SequentialCommandGroup(
      DrivePathHelpers.createDrivePathCommand(m_drive, bounceLeg1),
      DrivePathHelpers.createDrivePathCommand(m_drive, bounceLeg2)
    ));
    SmartDashboard.putData("Auto", chooser);
  }

}
