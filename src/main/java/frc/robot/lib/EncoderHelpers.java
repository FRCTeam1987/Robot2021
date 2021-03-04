// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.lib;

import frc.robot.Constants;

/** Add your docs here. */
public class EncoderHelpers {

  public static double ctreVelocityToLinearVelocity(final double ctreVelocity, final double ticksPerRevolution, final double circumference) {
    return ctreVelocityToLinearVelocity(ctreVelocity, ticksPerRevolution, circumference, 1.0);
  }

  public static double ctreVelocityToLinearVelocity(final double ctreVelocity, final double ticksPerRevolution, final double circumference, final double postEncoderGearing) {
    return ctreVelocityToRps(ctreVelocity, ticksPerRevolution, postEncoderGearing) * circumference;
  }

  public static double ctreVelocityToRps(final double ctreVelocity, final double ticksPerRevolution) {
    return ctreVelocityToRps(ctreVelocity, ticksPerRevolution, 1.0);
  }

  public static double ctreVelocityToRps(final double ctreVelocity, final double ticksPerRevolution, final double postEncoderGearing) {
    return (ctreVelocity * 10.0) / (ticksPerRevolution * postEncoderGearing);
  }

  public static double rpmToCtreVelocity(final double rpm, final double ticksPerRevolution) {
    return rpmToCtreVelocity(rpm, ticksPerRevolution, 1);
  }

  public static double rpmToCtreVelocity(final double rpm, final double ticksPerRevolution, final double postEncoderGearing) {
    return rpm * ticksPerRevolution / Constants.milliPerMin; //1,000ms per sec, but robot cares about per 100ms, so then 60 sec/min
  }

  public static int wheelRotationsToTicks(final double wheelRotations, final int ticksPerRevolution, final double reduction) {
    return (int)(wheelRotations * ticksPerRevolution * reduction);
  }

  public static double ticksToDistance(final double ticks, final double ticksPerRevolution, final double circumference) {
    return ticksToDistance(ticks, ticksPerRevolution, circumference, 1.0);
  }

  public static double ticksToDistance(final double ticks, final double ticksPerRevolution, final double circumference, final double postEncoderGearing) {
    return ticks / (ticksPerRevolution * postEncoderGearing) * circumference;
  }

}
