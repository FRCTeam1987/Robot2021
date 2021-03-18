// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.drive.DrivePathHelpers;
import frc.robot.commands.drive.SetAccuraceChallengeStep;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AccuraceChallengeGroup extends SequentialCommandGroup {
  /** Creates a new AccuraceChallengeGroup. */
  public AccuraceChallengeGroup(Drive drive, LimeLight limeLight, Shooter shooter) {
    Integer step = drive.getAccuracyChallengeStep();
    List<Number> stepDistances = Arrays.asList(4.572, 3.048, 1.524, 0.308);
    

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
    DrivePathHelpers.driveStraightCommand(drive, -stepDistances.get(step).doubleValue()),
    new WaitCommand(2),
    DrivePathHelpers.driveStraightCommand(drive, stepDistances.get(step).doubleValue()),
    new SetAccuraceChallengeStep(drive, step+1)
    );
  }
}
