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
import frc.robot.commands.drive.ClearPowerPortFirstRun;
import frc.robot.commands.drive.DetermineGalacticColor;
import frc.robot.commands.drive.RecordPath;
import frc.robot.commands.drive.TeleopDriveConfigurable;
import frc.robot.commands.drive.ZeroSensors;
import frc.robot.commands.hangargames.EightBallTrench;
import frc.robot.commands.hangargames.FarAuto;
import frc.robot.commands.hangargames.NewAuto;
import frc.robot.commands.shooter.ConfigClose;
import frc.robot.commands.shooter.ConfigFar;
import frc.robot.commands.shooter.HoodLock;
import frc.robot.commands.shooter.HoodLower;
import frc.robot.commands.shooter.HoodRaise;
import frc.robot.commands.shooter.HoodUnlock;
import frc.robot.commands.shooter.ShootLimeLight;
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
  private JoystickButton buttonCollector;
  private JoystickButton buttonShooter;
  private JoystickButton buttonFarShot;
  private JoystickButton buttonCloseShot;
  private JoystickButton buttonAgitate;

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

  public double powerPortTimer;

  private final SendableChooser<Command> chooser = new SendableChooser<>();
  private final SendableChooser<XboxController.Button> collectorChooser = new SendableChooser<>();
  private final SendableChooser<XboxController.Button> shooterChooser = new SendableChooser<>();
  private final SendableChooser<XboxController.Button> agitateChooser = new SendableChooser<>();
  private final SendableChooser<XboxController.Button> raiseHoodChooser = new SendableChooser<>();
  private final SendableChooser<XboxController.Button> lowerHoodChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    
    driver = new XboxController(Constants.OI.Xbox.driverID);

    configureShuffleboard();
    configureButtonBindings();
    configureDefaultCommands();
    
    this.powerPortTimer = Timer.getFPGATimestamp();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    CommandScheduler.getInstance().clearButtons();
    buttonCollector = new JoystickButton(driver, collectorChooser.getSelected().value);
    buttonShooter = new JoystickButton(driver, shooterChooser.getSelected().value);
    buttonFarShot = new JoystickButton(driver, lowerHoodChooser.getSelected().value);
    buttonCloseShot = new JoystickButton(driver, raiseHoodChooser.getSelected().value);
    buttonAgitate = new JoystickButton(driver, agitateChooser.getSelected().value);
    buttonAgitate
      .whileHeld(new Agitate(m_spindexer));
    buttonCollector
      .whenPressed((new StartCollect(m_collector).andThen(new Agitate(m_spindexer))))
      .whenReleased(
        new StopCollect(m_collector).andThen(new StopAll(m_collector, m_spindexer, m_shooter)));
    buttonShooter
      .whenPressed(new TeleopShoot(m_drive, limeLight, m_spindexer, m_shooter))
      .whenReleased(ConfigFar.configFarConditional(m_shooter));
    buttonFarShot.whenPressed(ConfigFar.configFarConditional(m_shooter));
    buttonCloseShot.whenPressed(ConfigClose.configCloseConditional(m_shooter));
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
    m_spindexer.setDefaultCommand(new Agitate(m_spindexer));
  }
  
  private void configureShuffleboard() {

    final ShuffleboardTab tabShooter = Shuffleboard.getTab("Shooter");

    collectorChooser.setDefaultOption(Constants.OI.Buttons.Driver.collectorBtnId.name(), Constants.OI.Buttons.Driver.collectorBtnId);
    collectorChooser.addOption(XboxController.Button.kA.name(), XboxController.Button.kA);
    collectorChooser.addOption(XboxController.Button.kB.name(), XboxController.Button.kB);
    collectorChooser.addOption(XboxController.Button.kX.name(), XboxController.Button.kX);
    collectorChooser.addOption(XboxController.Button.kBumperLeft.name(), XboxController.Button.kBumperLeft);
    collectorChooser.addOption(XboxController.Button.kBumperRight.name(), XboxController.Button.kBumperRight);
    SmartDashboard.putData("CollectorChooser", collectorChooser);

    shooterChooser.setDefaultOption(Constants.OI.Buttons.Driver.shooterBtnId.name(), Constants.OI.Buttons.Driver.shooterBtnId);
    shooterChooser.addOption(XboxController.Button.kY.name(), XboxController.Button.kY);
    shooterChooser.addOption(XboxController.Button.kA.name(), XboxController.Button.kA);
    shooterChooser.addOption(XboxController.Button.kB.name(), XboxController.Button.kB); 
    shooterChooser.addOption(XboxController.Button.kBumperLeft.name(), XboxController.Button.kBumperLeft);
    shooterChooser.addOption(XboxController.Button.kBumperRight.name(), XboxController.Button.kBumperRight);
    SmartDashboard.putData("ShooterChooser", shooterChooser);

    agitateChooser.setDefaultOption(Constants.OI.Buttons.Driver.agitateBtnId.name(), Constants.OI.Buttons.Driver.agitateBtnId);
    agitateChooser.addOption(XboxController.Button.kY.name(), XboxController.Button.kY);
    agitateChooser.addOption(XboxController.Button.kX.name(), XboxController.Button.kX);
    agitateChooser.addOption(XboxController.Button.kB.name(), XboxController.Button.kB);
    agitateChooser.addOption(XboxController.Button.kBumperLeft.name(), XboxController.Button.kBumperLeft);
    agitateChooser.addOption(XboxController.Button.kBumperRight.name(), XboxController.Button.kBumperRight);
    SmartDashboard.putData("AgitateChooser", agitateChooser);

    lowerHoodChooser.setDefaultOption(Constants.OI.Buttons.Driver.farShotBtnId.name(), Constants.OI.Buttons.Driver.farShotBtnId);
    lowerHoodChooser.addOption(XboxController.Button.kY.name(), XboxController.Button.kY);
    lowerHoodChooser.addOption(XboxController.Button.kX.name(), XboxController.Button.kX);
    lowerHoodChooser.addOption(XboxController.Button.kA.name(), XboxController.Button.kA);
    lowerHoodChooser.addOption(XboxController.Button.kB.name(), XboxController.Button.kB);
    lowerHoodChooser.addOption(XboxController.Button.kBumperRight.name(), XboxController.Button.kBumperRight);
    SmartDashboard.putData("LowerHoodChooser", lowerHoodChooser);

    raiseHoodChooser.setDefaultOption(Constants.OI.Buttons.Driver.closeShotBtnId.name(), Constants.OI.Buttons.Driver.closeShotBtnId);
    raiseHoodChooser.addOption(XboxController.Button.kY.name(), XboxController.Button.kY);
    raiseHoodChooser.addOption(XboxController.Button.kX.name(), XboxController.Button.kX);
    raiseHoodChooser.addOption(XboxController.Button.kA.name(), XboxController.Button.kA);
    raiseHoodChooser.addOption(XboxController.Button.kB.name(), XboxController.Button.kB);
    raiseHoodChooser.addOption(XboxController.Button.kBumperLeft.name(), XboxController.Button.kBumperLeft);
    SmartDashboard.putData("RaiseHoodChooser", raiseHoodChooser);

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
    tabShooter.add("Config Far", new ConfigFar(m_shooter));
    tabShooter.add("Config Close", new ConfigClose(m_shooter));
    tabShooter.add("Raise Hood", new HoodRaise(m_shooter));
    tabShooter.add("Lower Hood", new HoodLower(m_shooter));
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

    chooser.addOption("Barrel Run", AutoNav.barrelRun(m_drive));
    chooser.addOption("Slalom", AutoNav.slalom(m_drive));
    chooser.addOption("Slalom Test", AutoNav.slalomTest(m_drive));
    chooser.addOption("Bounce", AutoNav.bounce(m_drive));
    chooser.addOption("Path A Red", GalacticSearch.PathARed(m_drive, m_collector, m_spindexer));
    chooser.addOption("Path A Blue", GalacticSearch.PathABlue(m_drive, m_collector, m_spindexer));
    chooser.addOption("Path B Red", GalacticSearch.PathBRed(m_drive, m_collector, m_spindexer));
    chooser.addOption("Path B Blue", GalacticSearch.PathBBlue(m_drive, m_collector, m_spindexer));
    chooser.addOption("FarAuto", new FarAuto(m_drive, m_collector, m_spindexer, m_shooter, limeLight));
    chooser.addOption("NewAuto", new NewAuto(m_drive, m_collector, m_spindexer, m_shooter, limeLight));
    chooser.addOption("8 Ball Trench", new EightBallTrench(m_drive, m_collector, m_spindexer, m_shooter, limeLight));

    ConditionalCommand powerPortRunner = new ConditionalCommand(
      new SequentialCommandGroup(
        new ClearPowerPortFirstRun(m_drive),
        PowerPort.cycleFirst(m_drive, m_spindexer, m_shooter, m_collector, limeLight)
      ),
      PowerPort.cycle(m_drive, m_spindexer, m_shooter, m_collector, limeLight),
      () -> m_drive.isPowerPortFirstRun()
    );
    SmartDashboard.putData("Power Power Cycle First", new SequentialCommandGroup(
      new InstantCommand(() -> { powerPortTimer = Timer.getFPGATimestamp(); }),
      PowerPort.cycleFirst(m_drive, m_spindexer, m_shooter, m_collector, limeLight))
    );
    SmartDashboard.putData("Power Power Cycle Not First", PowerPort.cycle(m_drive, m_spindexer, m_shooter, m_collector, limeLight));

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
    configureButtonBindings();
  }

  public void teleopPeriodic(final Runnable onDisable) {
    // if (Timer.getFPGATimestamp() > powerPortTimer + 60.0) {
    //   onDisable.run();
    // }
  }

}
