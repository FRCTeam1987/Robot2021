// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import frc.robot.commands.StopAll;
import frc.robot.commands.collector.CollectorInOut;
import frc.robot.commands.collector.StartCollect;
import frc.robot.commands.collector.StopCollect;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.drive.TeleopDrive;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.spindexer.SpinTest;
import frc.robot.subsystems.Collector;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;

import java.util.List;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private Trajectory slalomTrajectory;
  private Trajectory bounce1, bounce2;

  private final XboxController driver;
  private final JoystickButton buttonCollector;
  private final JoystickButton buttonShooter;

  // Allocate Subsystems
  private final Drive m_drive = new Drive(
    new WPI_TalonFX(Constants.Drive.Can.leftMaster),
    new TalonFX(Constants.Drive.Can.leftSlave),
    new WPI_TalonFX(Constants.Drive.Can.rightMaster),
    new TalonFX(Constants.Drive.Can.rightSlave),
    // new AHRS()
    new ADXRS450_Gyro()
  );
  private final Collector m_collector = new Collector();
  private final Spindexer m_spindexer = new Spindexer(
    new WPI_TalonSRX(Constants.Spindexer.Can.bigBlock),
    new WPI_TalonSRX(Constants.Spindexer.Can.omni),
    new WPI_TalonSRX(Constants.Spindexer.Can.compliant)
  );
  private final LimeLight limeLight = new LimeLight();
  private final Shooter m_shooter = new Shooter(
    new WPI_TalonFX(Constants.Shooter.Can.flywheelMaster),
    new TalonFX(Constants.Shooter.Can.flywheelSlave)

  );

  private final SendableChooser<Command> chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    driver = new XboxController(Constants.OI.Xbox.driverID);

    bounce1 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Bounce1.wpilib.json");
    bounce2 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Bounce4.wpilib.json");

 
    // Configure the button bindings
    buttonCollector = new JoystickButton(driver, Constants.OI.Buttons.Driver.collectorBtnId);
    buttonShooter = new JoystickButton(driver, Constants.OI.Buttons.Driver.shooterBtnId);

    configureButtonBindings();

    configureDefaultCommands();
    configureShuffleboard();

    // slalomTrajectory = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Slalom.wpilib.json");
    // bounceLeg1 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Leg1.wpilib.json");
    // bounceLeg2 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Leg2.wpilib.json");
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    buttonCollector
      .whenHeld(new StartCollect(m_collector), true)
      .whenReleased(new StopCollect(m_collector), false);
    buttonShooter
      .whenHeld(new Shoot(limeLight, m_shooter, m_spindexer, 1000))
      .whenReleased(new Shoot(limeLight, m_shooter, m_spindexer, 0));
  }

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
    SmartDashboard.putData("Start Collect", new StartCollect(m_collector));
    SmartDashboard.putData("Stop Collect", new StopCollect(m_collector));
    SmartDashboard.putData("Start Spin", new SpinTest(m_spindexer, 0.75));
    SmartDashboard.putData("Stop Spin", new SpinTest(m_spindexer, 0));
    SmartDashboard.putData("Start Shoot", new Shoot(limeLight, m_shooter, m_spindexer, 0.95));
    SmartDashboard.putData("Stop Shoot", new Shoot(limeLight, m_shooter, m_spindexer, 0.0));
    SmartDashboard.putData("Stop All", new StopAll(m_collector, m_spindexer, m_shooter));

    chooser.addOption("Manual", DrivePathHelpers.createOnBoardDrivePathCommand(
      m_drive,
      new Pose2d(0, 0, new Rotation2d(0)),
      List.of(
        // new Translation2d(0.5, 0.0),
        // new Translation2d(1.0, 0.5),
        // new Translation2d(1.5, 1.0),
        // new Translation2d(3.5, 1.0),
        // new Translation2d(4.0, 0.5),
        // new Translation2d(4.5, 0.0),
        // new Translation2d(5.0, 0.0),
        new Translation2d(2.0, 0.0)
      ),
      new Pose2d(3.0, 0.0, new Rotation2d(0)),
      false));
    // chooser.addOption("Slalom", DrivePathHelpers.createDrivePathCommand(m_drive, slalomTrajectory));
    chooser.addOption("Bounce", new SequentialCommandGroup(
      DrivePathHelpers.createDrivePathCommand(m_drive, bounce1)
      // DrivePathHelpers.createDrivePathCommand(m_drive, bounce2)
    ));
    SmartDashboard.putData("Auto", chooser);
  }

}
