
package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.NoopCommand;
import frc.robot.subsystems.Shooter;


public class ConfigClose extends SequentialCommandGroup {
  
  public ConfigClose(final Shooter shooter) {
    
    addCommands(
      new HoodUnlock(shooter),
      new HoodRaise(shooter)
    );
  }

  public static Command configCloseConditional(final Shooter shooter) {
    return new ConditionalCommand(new NoopCommand(), new ConfigClose(shooter), () -> {
      return !shooter.isHoodConfigedFar();
    });
  }
}
