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
import frc.robot.commands.AccuraceChallengeGroup;
import frc.robot.commands.AimBot;
import frc.robot.commands.LoggerCommand;
import frc.robot.commands.StopAll;
import frc.robot.commands.challenges.AutoNav;
import frc.robot.commands.collector.StartCollect;
import frc.robot.commands.collector.StopCollect;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.drive.DriveTank;
import frc.robot.commands.drive.FindTrackWidth;
import frc.robot.commands.drive.RecordPath;
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
    new TalonFX(Constants.Shooter.Can.flywheelSlave)

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
    SmartDashboard.putData("Accurace Next", new AccuraceChallengeGroup(m_drive, limeLight, m_shooter));
    NetworkTableEntry rpmTestSetpoint = tabShooter.add("RPM Test Setpoint", 0.0).getEntry();
    tabShooter.add("Shoot RPM Dashboard", new ShootRPM(m_shooter, rpmTestSetpoint));
    // SmartDashboard.putData("Shoot RPM Dashboard", new ShootRPM(m_shooter));
    tabShooter.add("Shoot RPM 100", new ShootRPM(m_shooter, 100));
    tabShooter.add("Shoot RPM 1000", new ShootRPM(m_shooter, 1000));
    tabShooter.add("Shoot RPM 2000", new ShootRPM(m_shooter, 2000));
    tabShooter.add("Shoot RPM 3000", new ShootRPM(m_shooter, 3000));
    tabShooter.add("Shoot RPM 4000", new ShootRPM(m_shooter, 4000));
    tabShooter.add("Shoot RPM 4250", new ShootRPM(m_shooter, 4250));
    tabShooter.add("Shoot RPM 4500", new ShootRPM(m_shooter, 4500));
    tabShooter.add("Shoot RPM 4750", new ShootRPM(m_shooter, 4750));
    tabShooter.add("Shoot RPM 5000", new ShootRPM(m_shooter, 5000));
    tabShooter.add("Shoot Limelight", new ShootLimeLight(m_shooter, limeLight));
    SmartDashboard.putData("aimbot", new AimBot(m_drive, limeLight));
    SmartDashboard.putData("Agitate", new Agitate(m_spindexer, Constants.Spindexer.agitateSpeed, Constants.Spindexer.agitateDuration));
    SmartDashboard.putData("Drive Straight 1", new SequentialCommandGroup(DrivePathHelpers.driveStraightCommand(m_drive, 1.0)));
    SmartDashboard.putData("Drive Straight -1", new SequentialCommandGroup(DrivePathHelpers.driveStraightCommand(m_drive, -1.0)));
    SmartDashboard.putNumber("Log Level", 0);
    SmartDashboard.putData("Find Track Width", new FindTrackWidth(m_drive));
    SmartDashboard.putData("Prep Shoot", new PrepShoot(m_spindexer));
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

  /*
    Command barrelManual = DrivePathHelpers.createOnBoardDrivePathCommand(
      m_drive,
      new Pose2d(0, 0, new Rotation2d(0)),
      List.of(
        new Translation2d(3.4, -0.4),
        new Translation2d(2.6, -1.6),
        new Translation2d(2.4, 0.0),
        new Translation2d(5.1, 0.0),
        new Translation2d(5.0, 1.6),
        new Translation2d(3.8, 1.3),
        new Translation2d(4.0, 0),
        new Translation2d(5.0, -1.5)
        // new Translation2d(6.5, -1.5)
        // new Translation2d(6.5, 0.0)
      ),
      new Pose2d(6.5, -1.5, new Rotation2d(0)), false);
    Command part2 = DrivePathHelpers.createOnBoardDrivePathCommand(
        m_drive,
        new Pose2d(0.0, 0.0, new Rotation2d(0)),
        List.of(
          // new Translation2d(-6.2, 2.2),
          // new Translation2d(-4.05, 2.2),
          // new Translation2d(-4.05, 1.7),
          // new Translation2d(-2.05, 1.7),
          // new Translation2d(-2.05, 0.0),
          // new Translation2d(-6.2, 1.0)
          // new Translation2d(6.2, -2.2)
          // new Translation2d(1.53, 1.94)

          new Translation2d(-6.2, 1.0)


        ),
        new Pose2d(-6.2, 2.2, new Rotation2d(0)),
        true);
      */

    // chooser.addOption("Small S", DrivePathHelpers.createOnBoardDrivePathCommand(m_drive,
    //   new Pose2d(0.0, 0.0, new Rotation2d(0)),
    //   List.of(
    //     new Translation2d(1, 0.5),
    //     new Translation2d(2, -0.5)
    //   ),
    //   new Pose2d(3, 0, new Rotation2d(0)),
    //   false));
    
    // chooser.addOption("Curve From Docs", DrivePathHelpers.createOnBoardDrivePathCommand(m_drive,
    //   new Pose2d(0.0, 0.0, new Rotation2d(0)),
    //   List.of(
    //     new Translation2d(1, 1),
    //     new Translation2d(2, -1)
    //   ),
    //   new Pose2d(3, 0, new Rotation2d(0)),
    //   false));
    
    // chooser.addOption("Manual", new SequentialCommandGroup(
    //   new ShootTest(m_spindexer, m_shooter, 0.75),
    //   new WaitCommand(2.0),
    //   new Spindex(m_spindexer, 0.5),
    //   new WaitCommand(3.0),
    //   new ShootTest(m_spindexer, m_shooter, 0.0),
    //   new Spindex(m_spindexer, 0.0),
    //   new StartCollect(m_collector),
    //   part1,
    //   new StopCollect(m_collector),
    //   // part2,
    //   new ShootTest(m_spindexer, m_shooter, 0.75),
    //   new WaitCommand(2.0),
    //   new Spindex(m_spindexer, 0.5),
    //   new WaitCommand(3.0),
    //   new ShootTest(m_spindexer, m_shooter, 0.0),
    //   new Spindex(m_spindexer, 0.0)
    // ));

    // chooser.addOption("Shop Test", new SequentialCommandGroup(
    //   new ParallelRaceGroup(
    //     new SequentialCommandGroup(
    //       new ShootLimeLight(m_shooter, limeLight),
    //       new FeedShooter(m_spindexer),
    //       new WaitCommand(2.0), // wait for balls to get shot
    //       new StopAll(m_collector, m_spindexer, m_shooter),
    //       new StartCollect(m_collector)
    //     ),
    //     new DriveTank(m_drive, 0.0, 0.0)
    //   ),
    //   new ParallelRaceGroup(
    //     new Agitate(m_spindexer, Constants.Spindexer.agitateSpeed, Constants.Spindexer.agitateDuration),
    //     new SequentialCommandGroup(
    //       part1,
    //       // DrivePathHelpers.driveStraightCommand(m_drive, 2.5),
    //       new StopCollect(m_collector),
    //       part2,
    //       // DrivePathHelpers.driveStraightCommand(m_drive, -1.0),
    //       new AimBot(m_drive, limeLight)
    //     )
    //   ),
    //   new ParallelRaceGroup(
    //     new SequentialCommandGroup(
    //       new ShootLimeLight(m_shooter, limeLight),
    //       new FeedShooter(m_spindexer),
    //       new WaitCommand(2.0) // wait for balls to get shot
    //     ),
    //     new DriveTank(m_drive, 0.0, 0.0)
    //   ),
    //   new StopAll(m_collector, m_spindexer, m_shooter),
    //   new LoggerCommand("END")
    // ));

    // chooser.addOption("Slalom", DrivePathHelpers.createDrivePathCommand(m_drive, slalom1));

    // chooser.addOption("Barrel Run", DrivePathHelpers.createDrivePathCommand(m_drive, barrelRun_2));

    // chooser.addOption("Bounce", new SequentialCommandGroup(
    //   DrivePathHelpers.createDrivePathCommand(m_drive, bounce1),
    //   DrivePathHelpers.createDrivePathCommand(m_drive, bounce2)
    // ));
    
    // chooser.addOption("barrelRun_2", new SequentialCommandGroup(
    //   DrivePathHelpers.createDrivePathCommand(m_drive, barrelRun_0)
    //   // DrivePathHelpers.createDrivePathCommand(m_drive, barrelRun_1)
    // ));

    // chooser.addOption("barrelManual", barrelManual);

    chooser.addOption("barrel-record", DrivePathHelpers.createOnBoardDrivePathCommand(
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

    chooser.addOption("slalom-record", DrivePathHelpers.createOnBoardDrivePathCommand(
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

    // chooser.addOption("Shop Record", DrivePathHelpers.createOnBoardDrivePathCommand(
    //   m_drive,
    //   new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
    //   List.of(
    //     new Translation2d(0.04815839868350268, -2.0094109896299883E-5),
    //     new Translation2d(0.19778934068711249, -0.0019560029691824805),
    //     new Translation2d(0.3988069082604023, -0.020707040313847645),
    //     new Translation2d(0.6601155653643023, -0.08423250710903193),
    //     new Translation2d(0.9459160428098666, -0.22390918312964211),
    //     new Translation2d(1.1933533826705345, -0.44907311149441637),
    //     new Translation2d(1.425409785974115, -0.6652832007584295),
    //     new Translation2d(1.7250878042065503, -0.7752330825768285),
    //     new Translation2d(2.0313961735485853, -0.7152434620962551),
    //     new Translation2d(2.2281571827644004, -0.5361027414468086),
    //     new Translation2d(2.347028242194495, -0.26888556624241133),
    //     new Translation2d(2.416878210528717, 0.005812043561833307),
    //     new Translation2d(2.5583018653223024, 0.25412582195103245),
    //     new Translation2d(2.7902953111274633, 0.3998857675174201),
    //     new Translation2d(3.041437012744251, 0.40846913683538266),
    //     new Translation2d(3.2657274613709744, 0.29885059637838873),
    //     new Translation2d(3.4102206589491124, 0.10886611923774657),
    //     new Translation2d(3.473543867871484, -0.1788224603905391),
    //     new Translation2d(3.467244525515047, -0.5167940932642798),
    //     new Translation2d(3.3788692081068805, -0.8229820156853019),
    //     new Translation2d(3.177744157733275, -1.0760942338442),
    //     new Translation2d(2.8737866477185956, -1.2149148492340842),
    //     new Translation2d(2.5328572093283115, -1.1942296836435706),
    //     new Translation2d(2.258370909568833, -1.0265340293624254),
    //     new Translation2d(2.1060708308453857, -0.7610010597672363),
    //     new Translation2d(2.061089290070757, -0.46328542156618935),
    //     new Translation2d(2.0679350634491462, -0.15677835278743427),
    //     new Translation2d(2.0660489208786177, 0.18833894761402084),
    //     new Translation2d(1.970070579089863, 0.5241283919047302),
    //     new Translation2d(1.766604509062227, 0.7373551193214215),
    //     new Translation2d(1.5119910588437815, 0.7868313607460692),
    //     new Translation2d(1.3116548139862787, 0.7018243895270451),
    //     new Translation2d(1.172847607977412, 0.5402763298468841),
    //     new Translation2d(1.0397867401261314, 0.3218493523131141),
    //     new Translation2d(0.821383274351063, 0.11076929026468148),
    //     new Translation2d(0.5259627008956097, 0.05),
    //     new Translation2d(0.23055714391918644, 0.0)
    //   ),
    //   new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(180)),
    //   false
    // ));


    chooser.addOption("Barrel Run", AutoNav.barrelRun(m_drive));
    chooser.addOption("Bounce", AutoNav.bounce(m_drive));
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

    SmartDashboard.putData("Auto", chooser);
  }

  public void disabledInit() {
    // m_drive.setCoast();
  }

  public void teleopInit() {
    m_drive.teleopInit();
  }

}
