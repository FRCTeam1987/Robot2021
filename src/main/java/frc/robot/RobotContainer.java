// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import frc.robot.commands.AimBot;
import frc.robot.commands.LoggerCommand;
import frc.robot.commands.StopAll;
import frc.robot.commands.collector.StartCollect;
import frc.robot.commands.collector.StopCollect;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.drive.DriveTank;
import frc.robot.commands.drive.TeleopDrive;
import frc.robot.commands.shooter.ShootLimeLight;
import frc.robot.commands.shooter.ShootRPM;
// import frc.robot.commands.shooter.Shoot;
// import frc.robot.commands.shooter.ShootRPM;
import frc.robot.commands.shooter.ShootTest;
import frc.robot.commands.spindexer.Agitate;
import frc.robot.commands.spindexer.FeedShooter;
import frc.robot.commands.spindexer.Spindex;
import frc.robot.subsystems.Collector;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;

import java.util.List;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private Trajectory slalomTrajectory;
  private Trajectory bounce1, bounce2, slalom1;

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
    slalom1 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Slalom1.wpilib.json");


 
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
    // buttonShooter
    //   .whenHeld(new Shoot(limeLight, m_shooter, m_spindexer, 1000))
    //   .whenReleased(new Shoot(limeLight, m_shooter, m_spindexer, 0));
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
    // SmartDashboard.putData("Extend Collector", new CollectorInOut(m_collector, Constants.Collector.Values.cylinderExtend));
    // SmartDashboard.putData("Retract Collector", new CollectorInOut(m_collector, Constants.Collector.Values.cylinderRetract));
    SmartDashboard.putData("Start Collect", new StartCollect(m_collector));
    SmartDashboard.putData("Stop Collect", new StopCollect(m_collector));
    // SmartDashboard.putData("Start Spin", new Spindex(m_spindexer, 0.5));
    // SmartDashboard.putData("Stop Spin", new Spindex(m_spindexer, 0));
    // SmartDashboard.putData("Start Shoot", new Shoot(limeLight, m_shooter, m_spindexer, 0.95));
    // SmartDashboard.putData("Stop Shoot", new Shoot(limeLight, m_shooter, m_spindexer, 0.0));
    // SmartDashboard.putData("Shoot Test", new ShootTest(m_spindexer, m_shooter, 0.75)); //percentage for starting autonomous is .75
    SmartDashboard.putData("Stop All", new StopAll(m_collector, m_spindexer, m_shooter));
    SmartDashboard.putData("Feed Shooter", new FeedShooter(m_spindexer));
    SmartDashboard.putData("Shoot RPM Dashboard", new ShootRPM(m_shooter));
    SmartDashboard.putData("Shoot RPM 100", new ShootRPM(m_shooter, 100));
    SmartDashboard.putData("Shoot RPM 1000", new ShootRPM(m_shooter, 1000));
    SmartDashboard.putData("Shoot RPM 2000", new ShootRPM(m_shooter, 2000));
    SmartDashboard.putData("Shoot RPM 3000", new ShootRPM(m_shooter, 3000));
    SmartDashboard.putData("Shoot RPM 4000", new ShootRPM(m_shooter, 4000));
    SmartDashboard.putData("Shoot RPM 4250", new ShootRPM(m_shooter, 4250));
    SmartDashboard.putData("Shoot RPM 4500", new ShootRPM(m_shooter, 4500));
    SmartDashboard.putData("Shoot RPM 4750", new ShootRPM(m_shooter, 4750));
    SmartDashboard.putData("Shoot RPM 5000", new ShootRPM(m_shooter, 5000));
    SmartDashboard.putData("Shoot Limelight", new ShootLimeLight(m_shooter, limeLight));
    SmartDashboard.putData("aimbot", new AimBot(m_drive, limeLight));
    SmartDashboard.putData("Agitate", new Agitate(m_spindexer, Constants.Spindexer.agitateSpeed, Constants.Spindexer.agitateDuration));
    SmartDashboard.putData("Drive Straight 1", new SequentialCommandGroup(DrivePathHelpers.driveStraightCommand(m_drive, 1.0)));
    SmartDashboard.putData("Drive Straight -1", new SequentialCommandGroup(DrivePathHelpers.driveStraightCommand(m_drive, -1.0)));
    SmartDashboard.putNumber("Log Level", 0);

    Command part1 = DrivePathHelpers.createOnBoardDrivePathCommand(
      m_drive,
      new Pose2d(0, 0, new Rotation2d(0)),
      List.of(
        new Translation2d(2.05, 0.0),
        new Translation2d(2.05, -1.7),
        // new Translation2d(4.05, -1.7),
        new Translation2d(4.05, -2.2)
        // new Translation2d(1.53, 1.94)
      ),
      new Pose2d(6.2, -2.2, new Rotation2d(0)), false);
    Command part2 = DrivePathHelpers.createOnBoardDrivePathCommand(
        m_drive,
        new Pose2d(0.0, 0.0, new Rotation2d(0)),
        List.of(
          // new Translation2d(-6.2, 2.2),
          // new Translation2d(-4.05, 2.2),
          // new Translation2d(-4.05, 1.7),
          // new Translation2d(-2.05, 1.7),
          // new Translation2d(-2.05, 0.0),
          new Translation2d(-6.2, 1.0)
          // new Translation2d(6.2, -2.2)
          // new Translation2d(1.53, 1.94)
        ),
        new Pose2d(-6.2, 2.2, new Rotation2d(0)),
        true);
      
    chooser.addOption("Manual", new SequentialCommandGroup(
      new ShootTest(m_spindexer, m_shooter, 0.75),
      new WaitCommand(2.0),
      new Spindex(m_spindexer, 0.5),
      new WaitCommand(3.0),
      new ShootTest(m_spindexer, m_shooter, 0.0),
      new Spindex(m_spindexer, 0.0),
      new StartCollect(m_collector),
      // part1,
      new StopCollect(m_collector),
      // part2,
      new ShootTest(m_spindexer, m_shooter, 0.75),
      new WaitCommand(2.0),
      new Spindex(m_spindexer, 0.5),
      new WaitCommand(3.0),
      new ShootTest(m_spindexer, m_shooter, 0.0),
      new Spindex(m_spindexer, 0.0)
    ));

    chooser.addOption("Shop Test", new SequentialCommandGroup(
      new ParallelRaceGroup(
        new SequentialCommandGroup(
          new ShootLimeLight(m_shooter, limeLight),
          new FeedShooter(m_spindexer),
          new WaitCommand(2.0), // wait for balls to get shot
          new StopAll(m_collector, m_spindexer, m_shooter),
          new StartCollect(m_collector)
        ),
        new DriveTank(m_drive, 0.0, 0.0)
      ),
      new ParallelRaceGroup(
        new Agitate(m_spindexer, Constants.Spindexer.agitateSpeed, Constants.Spindexer.agitateDuration),
        new SequentialCommandGroup(
          part1,
          // DrivePathHelpers.driveStraightCommand(m_drive, 2.5),
          new StopCollect(m_collector),
          part2,
          // DrivePathHelpers.driveStraightCommand(m_drive, -1.0),
          new AimBot(m_drive, limeLight)
        )
      ),
      new ParallelRaceGroup(
        new SequentialCommandGroup(
          new ShootLimeLight(m_shooter, limeLight),
          new FeedShooter(m_spindexer),
          new WaitCommand(2.0) // wait for balls to get shot
        ),
        new DriveTank(m_drive, 0.0, 0.0)
      ),
      new StopAll(m_collector, m_spindexer, m_shooter),
      new LoggerCommand("END")
    ));

    chooser.addOption("Slalom", DrivePathHelpers.createDrivePathCommand(m_drive, slalom1));

    chooser.addOption("Bounce", new SequentialCommandGroup(
      DrivePathHelpers.createDrivePathCommand(m_drive, bounce1),
      DrivePathHelpers.createDrivePathCommand(m_drive, bounce2)
    ));

    SmartDashboard.putData("Auto", chooser);
  }

  public void disabledInit() {
    m_drive.setCoast();
  }

}
