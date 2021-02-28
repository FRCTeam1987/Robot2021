// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spindexer;


public class Shoot extends CommandBase {
  private final LimeLight limeLight;
  private final Shooter shooter;
  private final Spindexer spindexer;
  private final double speed;

  /** Creates a new Shoot. */
  public Shoot(LimeLight limeLight, Shooter shooter, Spindexer spindexer, double speed) {
    this.limeLight = limeLight;
    this.shooter = shooter;
    this.spindexer = spindexer;
    this.speed = speed;

    // Use addRequirements() here to declare subsystem dependencies.
    // addRequirements(this.limeLight);
    addRequirements(this.spindexer, this.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setPercent(this.speed);
    if (this.speed == 0) {
      spindexer.setCompliant(0);
    } else {
      spindexer.setCompliant(Constants.Spindexer.compliantSpeed);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public boolean canSeeTarget() {
    return this.shooter.canSeeTarget(limeLight.getVisibility());
  }

  public double getAngleError(){
    return this.shooter.getAngleError(limeLight.getXAxis(), limeLight.getVisibility());
  }

  public boolean isOnTarget() {
    return this.shooter.isOnTarget(limeLight.getXAxis(), limeLight.getVisibility());
  }

  public void setRpmReal(double rpm) {
    this.shooter.setRPM(rpm);
  }
}
