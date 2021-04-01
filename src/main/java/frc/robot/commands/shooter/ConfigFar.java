
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.NoopCommand;
import frc.robot.subsystems.Shooter;


public class ConfigFar extends SequentialCommandGroup {

  public ConfigFar(final Shooter shooter) {

    addCommands(
      new HoodUnlock(shooter),
      new HoodLower(shooter),
      new HoodLock(shooter)
    );
  }

  public static Command configFarConditional(final Shooter shooter) {
    return new ConditionalCommand(new NoopCommand(), new ConfigFar(shooter), () -> {
      return shooter.isHoodConfigedFar();
    });
  }
}
