

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class HoodLower extends WaitCommand {
  private final Shooter m_shooter;

  public HoodLower(final Shooter shooter) {
    super(Constants.Shooter.Values.hoodRaiseDuration);
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  @Override
  public void initialize() {
    super.initialize();
    m_shooter.lowerHood();
  }
}
