

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;


public class ConfigClose extends SequentialCommandGroup {
  
  public ConfigClose(final Shooter shooter) {
    
    addCommands(
      new HoodUnlock(shooter),
      new HoodRaise(shooter)
    );
  }
}
