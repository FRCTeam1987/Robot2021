

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class HoodUnlock extends WaitCommand {
  private final Shooter m_shooter;

  public HoodUnlock(final Shooter shooter) {
    super(Constants.Shooter.Values.hoodLockDuration);
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  @Override
  public void initialize() {
    m_shooter.unlockHood();
    super.initialize();
  }

}
