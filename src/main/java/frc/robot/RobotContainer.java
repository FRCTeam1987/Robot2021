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
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
import frc.robot.commands.drive.AimBot2;
import frc.robot.commands.drive.AimBot3;
import frc.robot.commands.drive.ClearPowerPortFirstRun;
import frc.robot.commands.drive.DetermineGalacticColor;
import frc.robot.commands.drive.RecordPath;
import frc.robot.commands.drive.TeleopDrive;
import frc.robot.commands.drive.TeleopDriveConfigurable;
import frc.robot.commands.drive.ZeroSensors;
import frc.robot.commands.hangargames.EightBallTrench;
import frc.robot.commands.hangargames.FarAuto;
import frc.robot.commands.hangargames.SixBallTrench;
import frc.robot.commands.hangargames.NoCollector;
import frc.robot.commands.shooter.EngagePto;
import frc.robot.commands.shooter.HoodLock;
import frc.robot.commands.shooter.HoodUnlock;
import frc.robot.commands.shooter.ShootLimeLight;
import frc.robot.commands.shooter.ShootPercent;
import frc.robot.commands.shooter.ShootRPM;
import frc.robot.commands.spindexer.Agitate;
import frc.robot.commands.spindexer.FeedShooter;
import frc.robot.commands.spindexer.PrepShoot;
import frc.robot.subsystems.Collector;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
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

  private final XboxController driver;
  private final XboxController coDriver;
  private final JoystickButton buttonCollector;
  private final JoystickButton buttonShooter;
  private final JoystickButton buttonAgitate;
  // private final JoystickButton buttonClimberUp;
  // private final JoystickButton buttonClimberDn;
  // private final JoystickButton buttonClimberPto;
  private final JoystickButton buttonClimberRelease;
  private final JoystickButton buttonClimb;

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
    coDriver = new XboxController(Constants.OI.Xbox.coDriverID);

    buttonCollector = new JoystickButton(driver, Constants.OI.Buttons.Driver.collectorBtnId.value);
    buttonShooter = new JoystickButton(driver, Constants.OI.Buttons.Driver.shooterBtnId.value);
    buttonAgitate = new JoystickButton(coDriver, Constants.OI.Buttons.CoDriver.agitateBtnId.value);
    // buttonClimberUp = new JoystickButton(coDriver, Constants.OI.Buttons.CoDriver.climberUp.value);
    // buttonClimberDn = new JoystickButton(coDriver, Constants.OI.Buttons.CoDriver.climberDn.value);
    // buttonClimberPto = new JoystickButton(coDriver, Constants.OI.Buttons.CoDriver.climberPto.value);
    buttonClimberRelease = new JoystickButton(coDriver, Constants.OI.Buttons.CoDriver.climberRelease.value);
    buttonClimb = new JoystickButton(coDriver, Constants.OI.Buttons.CoDriver.climb.value);

    configureShuffleboard();
    configureButtonBindings();
    configureDefaultCommands();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    buttonCollector
      .whenPressed((new StartCollect(m_collector).andThen(new Agitate(m_spindexer))))
      .whenReleased(
        new StopCollect(m_collector).andThen(new StopAll(m_collector, m_spindexer, m_shooter)));
    buttonShooter
      .whenPressed(new TeleopShoot(m_drive, limeLight, m_spindexer, m_shooter))
      .whenReleased(new StopAll(m_collector, m_spindexer, m_shooter));
    buttonAgitate.whileHeld(new Agitate(m_spindexer));

    SmartDashboard.putData("t-lock", new InstantCommand(() -> { m_shooter.lockHood(); }, m_shooter));
    SmartDashboard.putData("t-unlock", new InstantCommand(() -> { m_shooter.unlockHood(); }, m_shooter));
    SmartDashboard.putData("t-en-pto", new InstantCommand(() -> { m_shooter.engagePto(); }, m_shooter));
    SmartDashboard.putData("t-disen-pto", new InstantCommand(() -> { m_shooter.disengagePto(); }, m_shooter));
    SmartDashboard.putData("t-percent+", new InstantCommand(() -> { m_shooter.setPercent(0.5); }, m_shooter));
    SmartDashboard.putData("t-percent-", new InstantCommand(() -> { m_shooter.setPercent(-0.25); }, m_shooter));
    buttonClimberRelease.whenPressed(new InstantCommand(() -> { m_shooter.unlockHood(); }, m_shooter));
    buttonClimb
      .whenPressed(
        new SequentialCommandGroup(
          new InstantCommand(() -> { m_shooter.unlockHood(); }, m_shooter),
          new InstantCommand(() -> { m_shooter.engagePto(); }, m_shooter),
          new InstantCommand(() -> { m_shooter.setPercentRamp(0.25); }, m_shooter),
          new WaitCommand(0.2),
          new InstantCommand(() -> { m_shooter.setPercent(0.75); }, m_shooter)
        )
      )
      .whenReleased(
        new SequentialCommandGroup(
          new ShootPercent(m_shooter, 0.0),
          new InstantCommand(() -> { m_shooter.lockHood(); }, m_shooter),
          new InstantCommand(() -> { m_shooter.disengagePto(); }, m_shooter)
        )
      );
    // buttonClimberPto.whenPressed(new EngagePto(m_shooter));
    // buttonClimberUp
    //   .whenPressed(
    //     new SequentialCommandGroup(
    //       new InstantCommand(() -> { m_shooter.unlockHood(); }, m_shooter),
    //       new EngagePto(m_shooter),
    //       new ShootPercent(m_shooter, Constants.Shooter.climberUpPercent)
    //     )
    //   )
    //   .whenReleased(new ShootPercent(m_shooter, 0.0));
    // buttonClimberDn
    //   .whenPressed(new ShootPercent(m_shooter, Constants.Shooter.climberDnPercent))
    //   .whenReleased(new ShootPercent(m_shooter, 0.0));
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
    // m_spindexer.setDefaultCommand(new Agitate(m_spindexer));
  }
  
  private void configureShuffleboard() {

    final ShuffleboardTab tabShooter = Shuffleboard.getTab("Shooter");

    SmartDashboard.putData("Start Collect", new StartCollect(m_collector));
    SmartDashboard.putData("Stop Collect", new StopCollect(m_collector));
    SmartDashboard.putData("Stop All", new StopAll(m_collector, m_spindexer, m_shooter));
    SmartDashboard.putData("Feed Shooter", new FeedShooter(m_spindexer));
    NetworkTableEntry rpmTestSetpoint = tabShooter.add("RPM Test Setpoint", 0.0).getEntry();
    tabShooter.add("Stop", new ShootRPM(m_shooter, 0));
    tabShooter.add("Shoot RPM Dashboard", new ShootRPM(m_shooter, rpmTestSetpoint));
    tabShooter.add("Shoot RPM 3000", new ShootRPM(m_shooter, 3000));
    tabShooter.add("Shoot RPM 3500", new ShootRPM(m_shooter, 3500));
    tabShooter.add("Shoot RPM 4000", new ShootRPM(m_shooter, 4000));
    tabShooter.add("Shoot RPM 4500", new ShootRPM(m_shooter, 4500));
    tabShooter.add("Shoot RPM 5000", new ShootRPM(m_shooter, 5000));
    tabShooter.add("Shoot Limelight", new ShootLimeLight(m_shooter, limeLight));
    tabShooter.add("Unlock Hood", new HoodUnlock(m_shooter));
    tabShooter.add("Lock Hood", new HoodLock(m_shooter));
    SmartDashboard.putData("aimbot", new AimBot(m_drive, limeLight));
    SmartDashboard.putData("Agitate", new Agitate(m_spindexer, Constants.Spindexer.agitateSpeed, Constants.Spindexer.agitateDuration));
    SmartDashboard.putNumber("Log Level", 0);
    SmartDashboard.putData("Prep Shoot", new PrepShoot(m_spindexer));
    SmartDashboard.putData("Accuracy Challenge", new Accuracy(m_drive, m_spindexer, m_shooter, m_collector, driver, limeLight));
    SmartDashboard.putData("Set Brake", new InstantCommand(() -> m_drive.setBrake(), m_drive));
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
    SmartDashboard.putData("Collector Extend", new InstantCommand(() -> { m_collector.extend(); }, m_collector));
    SmartDashboard.putData("Collector Retract", new InstantCommand(() -> { m_collector.retract(); }, m_collector));
    SmartDashboard.putData("Collector Run", new InstantCommand(() -> { m_collector.setRollerPercent(Constants.Collector.Values.motorIn); }, m_collector));
    SmartDashboard.putData("Collector Reverse", new InstantCommand(() -> { m_collector.setRollerPercent(-Constants.Collector.Values.motorIn); }, m_collector));
    SmartDashboard.putData("Collector Stop", new InstantCommand(() -> { m_collector.setRollerPercent(0); }, m_collector));
    SmartDashboard.putData("AimBot2", new AimBot2(m_drive, limeLight));
    SmartDashboard.putData("AimBot3", new AimBot3(m_drive, limeLight));


    chooser.addOption("Barrel Run", AutoNav.barrelRun(m_drive));
    chooser.addOption("Slalom", AutoNav.slalom(m_drive));
    chooser.addOption("Slalom Test", AutoNav.slalomTest(m_drive));
    chooser.addOption("Bounce", AutoNav.bounce(m_drive));
    chooser.addOption("Path A Red", GalacticSearch.PathARed(m_drive, m_collector, m_spindexer));
    chooser.addOption("Path A Blue", GalacticSearch.PathABlue(m_drive, m_collector, m_spindexer));
    chooser.addOption("Path B Red", GalacticSearch.PathBRed(m_drive, m_collector, m_spindexer));
    chooser.addOption("Path B Blue", GalacticSearch.PathBBlue(m_drive, m_collector, m_spindexer));
    chooser.addOption("FarAuto", new FarAuto(m_drive, m_collector, m_spindexer, m_shooter, limeLight));
    chooser.addOption("6 Ball Trench", new SixBallTrench(m_drive, m_collector, m_spindexer, m_shooter, limeLight));
    chooser.addOption("8 Ball Trench", new EightBallTrench(m_drive, m_collector, m_spindexer, m_shooter, limeLight));
    chooser.addOption("No Collector", new NoCollector(m_drive, m_collector, m_spindexer, m_shooter, limeLight));

    // ConditionalCommand powerPortRunner = new ConditionalCommand(
    //   new SequentialCommandGroup(
    //     new ClearPowerPortFirstRun(m_drive),
    //     PowerPort.cycleFirst(m_drive, m_spindexer, m_shooter, m_collector, limeLight)
    //   ),
    //   PowerPort.cycle(m_drive, m_spindexer, m_shooter, m_collector, limeLight),
    //   () -> m_drive.isPowerPortFirstRun()
    // );
    // SmartDashboard.putData("Power Power Cycle First", new SequentialCommandGroup(
    //   new InstantCommand(() -> { powerPortTimer = Timer.getFPGATimestamp(); }),
    //   PowerPort.cycleFirst(m_drive, m_spindexer, m_shooter, m_collector, limeLight))
    // );
    // SmartDashboard.putData("Power Power Cycle Not First", PowerPort.cycle(m_drive, m_spindexer, m_shooter, m_collector, limeLight));

    //=~
    SmartDashboard.putData("Far Auto", new FarAuto(m_drive, m_collector, m_spindexer, m_shooter, limeLight));
    //=~

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

    SequentialCommandGroup galacticFirstRun = new SequentialCommandGroup(
      new DetermineGalacticColor(m_drive),
      new ConditionalCommand(
        new SequentialCommandGroup(  // true
          new LoggerCommand("Driving Blue A"),
          GalacticSearch.PathABlue(m_drive, m_collector, m_spindexer)
        ),
        new SequentialCommandGroup(  // false
          new LoggerCommand("Driving Red A"),
          GalacticSearch.PathARed(m_drive, m_collector, m_spindexer)
        ),
        () -> m_drive.isGalacticBlue()
      )
    );
   
    ConditionalCommand galacticSecondRun = new ConditionalCommand(
      new SequentialCommandGroup(   // true
        new LoggerCommand("Driving Blue B"),
        GalacticSearch.PathBBlue(m_drive, m_collector, m_spindexer)
      ),
      new SequentialCommandGroup(  // false
        new LoggerCommand("Driving Red B"),
        GalacticSearch.PathBRed(m_drive, m_collector, m_spindexer)
      ),
      () -> m_drive.isGalacticBlue()
    );

    // This is run when the driver clicks the Galactic Search button.
    // It determines if path A has already run.  If it has, it runs a
    // different command that 
    // If it has been run, it runs path B for the previously determined color.
    // If not, it makes a determination then runs path A.
    ConditionalCommand galacticEntryPoint = new ConditionalCommand(
      new SequentialCommandGroup(new LoggerCommand("Galactic Second Run"), galacticSecondRun),  // true
      new SequentialCommandGroup(new LoggerCommand("Galactic First Run"), galacticFirstRun),  // false
      () -> m_drive.hasGalacticBeenDone()
    );

    chooser.addOption("Galactic Search", galacticEntryPoint);

    SmartDashboard.putData("Auto", chooser);
  }

  public void autoInit() {
    limeLight.init();
  }

  public void disabledInit() {
    // m_drive.setCoast();
  }

  public void teleopInit() {
    m_drive.teleopInit();
    limeLight.init();
  }

}
