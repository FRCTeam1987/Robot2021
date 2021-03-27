// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import frc.robot.commands.AimBot;
import frc.robot.commands.LoggerCommand;
import frc.robot.commands.StopAll;
import frc.robot.commands.TeleopShoot;
import frc.robot.commands.challenges.Accuracy;
import frc.robot.commands.challenges.AutoNav;
import frc.robot.commands.challenges.GalacticSearch;
import frc.robot.commands.challenges.PowerPort;
import frc.robot.commands.collector.StartCollect;
import frc.robot.commands.collector.StopCollect;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.drive.DriveTank;
import frc.robot.commands.drive.FindTrackWidth;
import frc.robot.commands.drive.RecordPath;
import frc.robot.commands.drive.SetGalacticRedOrBlue;
import frc.robot.commands.drive.TeleopDrive;
import frc.robot.commands.drive.TeleopDriveConfigurable;
import frc.robot.commands.drive.ZeroSensors;
import frc.robot.commands.shooter.ShootLimeLight;
import frc.robot.commands.shooter.ShootRPM;
// import frc.robot.commands.shooter.Shoot;
// import frc.robot.commands.shooter.ShootRPM;
import frc.robot.commands.shooter.ShootTest;
import frc.robot.commands.spindexer.Agitate;
import frc.robot.commands.spindexer.FeedShooter;
import frc.robot.commands.spindexer.PrepShoot;
import frc.robot.commands.spindexer.Spindex;
import frc.robot.subsystems.Collector;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import java.util.List;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
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

  // private Trajectory slalomTrajectory;
  // private Trajectory bounce1, bounce2, slalom1, barrelRun_0, barrelRun_1;

  private final XboxController driver;
  private final JoystickButton buttonCollector;
  private final JoystickButton buttonShooter;

  // Allocate Subsystems
  private final Drive m_drive = new Drive(
    new WPI_TalonFX(Constants.Drive.Can.leftMaster), new TalonFX(Constants.Drive.Can.leftSlave), new CANCoder(Constants.Drive.Can.leftEncoder),
    new WPI_TalonFX(Constants.Drive.Can.rightMaster), new TalonFX(Constants.Drive.Can.rightSlave), new CANCoder(Constants.Drive.Can.rightEncoder),
    new AHRS()
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
    new TalonFX(Constants.Shooter.Can.flywheelSlave1),
    new TalonFX(Constants.Shooter.Can.flywheelSlave2),
    new TalonFX(Constants.Shooter.Can.flywheelSlave3)

  );

  private final SendableChooser<Command> chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    driver = new XboxController(Constants.OI.Xbox.driverID);

    // bounce1 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Bounce1.wpilib.json");
    // bounce2 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Bounce4.wpilib.json");
    // slalom1 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/Slalom1.wpilib.json");
    // barrelRun_0 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/BarrelRun_0.wpilib.json");
    // barrelRun_1 = DrivePathHelpers.createTrajectoryFromPathWeaverJsonFile("output/BarrelRun_1.wpilib.json");

 
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
      .whenPressed(new StartCollect(m_collector))
      .whenReleased(new StopCollect(m_collector));
    buttonShooter.whileHeld(new TeleopShoot(m_drive, limeLight, m_spindexer, m_shooter));
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
    m_drive.setDefaultCommand(new TeleopDriveConfigurable(m_drive, driver));
  }
  
  private void configureShuffleboard() {

     final ShuffleboardTab tabShooter = Shuffleboard.getTab("Shooter");

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
    NetworkTableEntry rpmTestSetpoint = tabShooter.add("RPM Test Setpoint", 0.0).getEntry();
    tabShooter.add("Shoot RPM Dashboard", new ShootRPM(m_shooter, rpmTestSetpoint));
    // SmartDashboard.putData("Shoot RPM Dashboard", new ShootRPM(m_shooter));
    // tabShooter.add("Shoot RPM 100", new ShootRPM(m_shooter, 100));
    // tabShooter.add("Shoot RPM 1000", new ShootRPM(m_shooter, 1000));
    // tabShooter.add("Shoot RPM 2000", new ShootRPM(m_shooter, 2000));
    // tabShooter.add("Shoot RPM 3000", new ShootRPM(m_shooter, 3000));
    // tabShooter.add("Shoot RPM 4000", new ShootRPM(m_shooter, 4000));
    // tabShooter.add("Shoot RPM 4250", new ShootRPM(m_shooter, 4250));
    // tabShooter.add("Shoot RPM 4500", new ShootRPM(m_shooter, 4500));
    // tabShooter.add("Shoot RPM 4750", new ShootRPM(m_shooter, 4750));
    tabShooter.add("Shoot RPM 5000", new ShootRPM(m_shooter, 5000));
    tabShooter.add("Shoot Limelight", new ShootLimeLight(m_shooter, limeLight));
    SmartDashboard.putData("aimbot", new AimBot(m_drive, limeLight));
    SmartDashboard.putData("Agitate", new Agitate(m_spindexer, Constants.Spindexer.agitateSpeed, Constants.Spindexer.agitateDuration));
    SmartDashboard.putData("Drive Straight 1", new SequentialCommandGroup(DrivePathHelpers.driveStraightCommand(m_drive, 1.0)));
    SmartDashboard.putData("Drive Straight -1", new SequentialCommandGroup(DrivePathHelpers.driveStraightCommand(m_drive, -1.0)));
    SmartDashboard.putNumber("Log Level", 0);
    SmartDashboard.putData("Find Track Width", new FindTrackWidth(m_drive));
    SmartDashboard.putData("Prep Shoot", new PrepShoot(m_spindexer));
    SmartDashboard.putData("Power Power Cycle", PowerPort.cycle(m_drive, m_spindexer, m_shooter, m_collector));
    SmartDashboard.putData("Accuracy Challenge", new Accuracy(m_drive, m_spindexer, m_shooter, m_collector, driver));
    SmartDashboard.putData("Test Shoot", new SequentialCommandGroup(
      new PrepShoot(m_spindexer),
      new WaitCommand(1),
      new ShootRPM(m_shooter, 5000),
      new FeedShooter(m_spindexer),
      new WaitCommand(1),
      new StopAll(m_collector, m_spindexer, m_shooter)
    ));
    SmartDashboard.putData("Zero Drive", new ZeroSensors(m_drive));
    SmartDashboard.putData("Record Path", new RecordPath(m_drive));
    SmartDashboard.putData("Record Keep Odometry Path", new RecordPath(m_drive, false));

    chooser.addOption("barrel-old", DrivePathHelpers.createOnBoardDrivePathCommand(
      m_drive,
      new Pose2d(0.0, 0.0, new Rotation2d(0)),
        List.of(
          new Translation2d(3, 0.0),  // start barrel 1
          new Translation2d(3.9, -1.1),
          new Translation2d(2.5, -1.8),
          new Translation2d(2.5, 0),  // end barrel 1
          new Translation2d(5.4, 0.7),  // start barrel 2
          new Translation2d(5.7, 2.2),
          new Translation2d(4.6, 2.0),  // end barrel 2
          new Translation2d(5.1, 0.0),
          new Translation2d(5.8, -1.1), // start barrel 3
          new Translation2d(7.2, -1.65),
          // new Translation2d(7.3, -1.2),
          new Translation2d(7.4, -0.8), // end barrel 3
          new Translation2d(5.5, 0.0),
          new Translation2d(4.5, 0.0),  // try removing these intermediate points
          new Translation2d(3.5, 0.0),
          new Translation2d(2.5, 0.0),
          new Translation2d(1.5, 0.0)
          // new Translation2d(6.3, 0.0) // 
        ),
      new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(180.0)),
      false
    ));

    chooser.addOption("slalom-old", DrivePathHelpers.createOnBoardDrivePathCommand(
      m_drive,
      new Pose2d(0.0, 0.0, new Rotation2d(0)),
        List.of(
          new Translation2d(1.1, 0.45),  // start barrel 1
          new Translation2d(2.27, 1.45), // end switch
          new Translation2d(5.0, 1.3),  // start switch
          new Translation2d(6.2, 0.39), // end switch
          new Translation2d(7.6, 0.28), // looping
          new Translation2d(7.17, 1.5), // end loop
          new Translation2d(6.09, 0.97),// start switch
          new Translation2d(5.04, -0.07), // end switch
          new Translation2d(2.03, 0.26), // start switch
          new Translation2d(1.38, 1.01)
        ),
      new Pose2d(0.0, 2.0, Rotation2d.fromDegrees(180.0)),
      false
    ));

    chooser.addOption("TestPath", DrivePathHelpers.createOnBoardDrivePathCommand(
      m_drive,
      new Pose2d(0.0, 0.0, new Rotation2d(0)),
      List.of(
        new Translation2d(0.1807,0.0062 ),
        new Translation2d(0.3999,0.0136 ),
        new Translation2d(0.6990,0.0234 ),
        new Translation2d(1.0735,0.0348 ),
        new Translation2d(1.5189,0.0459 ),
        new Translation2d(2.0308,0.0517 ),
        new Translation2d(2.6044,0.0370 ),
        new Translation2d(3.2270,-0.0586 ),
        new Translation2d(3.7546,-0.4633 ),
        new Translation2d(3.7805,-1.1727 ),
        new Translation2d(3.1402,-1.5360 ),
        new Translation2d(2.4037,-1.2476 ),
        new Translation2d(2.4024,-0.4604 ),
        new Translation2d(3.1856,-0.1435 ),
        new Translation2d(4.0919,-0.1785 ),
        new Translation2d(5.0175,-0.1250 ),
        new Translation2d(5.8968,0.1987 ),
        new Translation2d(6.1084,1.0240 ),
        new Translation2d(5.2759,1.4346 ),
        new Translation2d(4.5536,0.8897 ),
        new Translation2d(4.8863,0.0123 ),
        new Translation2d(5.4239,-0.7854 ),
        new Translation2d(6.1174,-1.4298 ),
        new Translation2d(7.0110,-1.6102 ),
        new Translation2d(7.6317,-0.9880 ),
        new Translation2d(7.3433,-0.2076 ),
        new Translation2d(6.5067,-0.0375 ),
        new Translation2d(5.6747,-0.0917 ),
        new Translation2d(4.8785,-0.0899 ),
        new Translation2d(4.1280,-0.0072 ),
        new Translation2d(3.4245,0.0797 ),
        new Translation2d(2.7685,0.1342 ),
        new Translation2d(2.1658,0.1550 ),
        new Translation2d(1.6223,0.1506 ),
        new Translation2d(1.1435,0.1303 ),
        new Translation2d(0.7338,0.1020 ),
        new Translation2d(0.3976,0.0717 ),
        new Translation2d(0.1392,0.0447 ),
        new Translation2d(-0.0372,0.0243 )
      ), 
      new Pose2d(-0.1292,0.0132, Rotation2d.fromDegrees(180.0)), 
      false
    ));

    chooser.addOption("Barrel Run", AutoNav.barrelRun(m_drive));
    chooser.addOption("Barrel test", AutoNav.barrelRunTest(m_drive));
    chooser.addOption("Slalom", AutoNav.slalom(m_drive));
    chooser.addOption("Bounce", AutoNav.bounce(m_drive));
    // chooser.addOption("Path A Blue", GalacticSearch.PathABlue(m_drive, m_collector, m_spindexer));
    chooser.addOption("Path A Red", GalacticSearch.PathARed(m_drive, m_collector, m_spindexer));
    chooser.addOption("Path A Blue", GalacticSearch.PathABlue(m_drive, m_collector, m_spindexer));
    // chooser.addOption("Shop Barrel", DrivePathHelpers.createOnBoardDrivePathCommand(
    //   m_drive,
    //   new Pose2d(0.0, 0.0, new Rotation2d(0)),
    //   List.of(
    //     new Translation2d(1.7, 0),
    //     new Translation2d(2.2, 0.8)
    //   ),
    //   new Pose2d(0.0, 0.0, new Rotation2d(0)),
    //   false
    //   )
    // );

    // ********************************************************************
    // *****  Galactic Search Path Commands
    // ********************************************************************
    //
    //  We flip a coin to determine whether we drive the red paths
    //  or the blue paths.  If red, we drive red A then red B.
    //  If blue, we drive blue A then blue B.
    //
    // ********************************************************************
    // ********************************************************************
   
    // This command sequence is run when the galactic search path is blue.
    // It sets the determination to blue then runs path blue A.

    SequentialCommandGroup bluePathDetermined = new SequentialCommandGroup(
      new SetGalacticRedOrBlue(m_drive, Constants.Drive.Galatic.RedOrBlue.Blue),
      new LoggerCommand("Driving Blue A")
      // GalacticSearch.PathABlue(m_drive, m_collector, m_spindexer)
    );

    // This command sequence is run when the galactic search path is red.
    // It sets the determination to blue then runs path red A.

    SequentialCommandGroup redPathDetermined = new SequentialCommandGroup(
      new SetGalacticRedOrBlue(m_drive, Constants.Drive.Galatic.RedOrBlue.Red),
      new LoggerCommand("Driving Red A")
      // GalacticSearch.PathARed(m_drive, m_collector, m_spindexer)
    );

    // Question 2 is run when the coin flip needs to be determined.  It
    // looks at the gyro angle and runs Red A or Blue A.
    ConditionalCommand galacticQuestion2 = new ConditionalCommand(
      bluePathDetermined,
      redPathDetermined,
      () -> m_drive.getGalacticRedOrBlue() == Constants.Drive.Galatic.RedOrBlue.Blue
    );

    // Question 3 is run when the driver clicks the Galactic Search button
    // after having driven path A.
    ConditionalCommand galacticQuestion3 = new ConditionalCommand(
      new LoggerCommand("Driving Blue B"),
      // GalacticSearch.PathBBlue(m_drive, m_collector, m_spindexer),
      new LoggerCommand("Driving Red B"),
      // GalacticSearch.PathBRed(m_drive, m_collector, m_spindexer),
      () -> m_drive.getGalacticRedOrBlue() == Constants.Drive.Galatic.RedOrBlue.Blue
    );

    // Question 1 is run when the drive clicks the Galactic Search button.
    // If it has been run, it runs path B for the previously determined color.
    // If not, it makes a determination then runs path A.
    ConditionalCommand galacticQuestion1 = new ConditionalCommand(
      galacticQuestion3,
      galacticQuestion2,
      () -> m_drive.hasGalacticBeenDone()
    );

    chooser.addOption("Galactic Search", galacticQuestion1);

    SmartDashboard.putData("Auto", chooser);
  }

  public void disabledInit() {
    // m_drive.setCoast();
  }

  public void teleopInit() {
    m_drive.teleopInit();
  }

}
