// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.shooter.ShootLimeLight;
import frc.robot.commands.spindexer.FeedShooter;
import frc.robot.commands.spindexer.PrepShoot;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TeleopShoot extends SequentialCommandGroup {

  private final LimeLight m_limeLight;
  private final Shooter m_shooter;
  private final Spindexer m_spindexer;

  /** Creates a new TeleopShoot. */
  public TeleopShoot(final Drive drive, final LimeLight limeLight, final Spindexer spindexer, final Shooter shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    // Assumes long shot shooter hood config
    // TODO implement turning LimeLight LEDs on and off
    addCommands(
      new InstantCommand(() -> { limeLight.turnOnLEDs(); }),
      new PrepShoot(spindexer),
      new WaitUntilCommand(limeLight::canSeeTarget),
      new AimBot(drive, limeLight),
      new ShootLimeLight(shooter, limeLight),
      new FeedShooter(spindexer).perpetually()
    );
    m_limeLight = limeLight;
    m_shooter = shooter;
    m_spindexer = spindexer;
  }

  // @Override
  // public void initialize() {
  //   super.initialize();
  //   m_limeLight.turnOnLEDs();
  // }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    m_limeLight.turnOffLEDs();
    m_shooter.stop();
    m_spindexer.stop();
  }
}
