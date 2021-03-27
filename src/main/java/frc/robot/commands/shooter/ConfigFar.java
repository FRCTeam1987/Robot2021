
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;


public class ConfigFar extends SequentialCommandGroup {

  public ConfigFar(final Shooter shooter) {

    addCommands(
      new HoodUnlock(shooter),
      new HoodLower(shooter),
      new HoodLock(shooter)
    );
  }
}
