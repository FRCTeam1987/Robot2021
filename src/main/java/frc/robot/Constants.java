// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.lib.InterpolatingDouble;
import frc.robot.lib.InterpolatingTreeMap;

// TODO Update these constants!!!

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static double milliPerMin = 600;
  public static double falconTicksPerRevolution = 2048;

  public static final class Collector {
    public static final class Can {
      public static final int motorMaster = 10;
      public static final int motorSlave = 11;
    }
    public static final class Solenoid {
      public static final int extend = 0;
      public static final int retract = 7;
    }
    public static final class Values {
      public static final DoubleSolenoid.Value cylinderExtend = DoubleSolenoid.Value.kForward;
      public static final DoubleSolenoid.Value cylinderRetract = DoubleSolenoid.Value.kReverse;
      public static final double motorIn = 0.75;
      public static final double motorOut = -0.75;
      public static final double motorStop = 0.0;
    }

  }

  public static final class Drive {
    public static final class Can {

      public static final int rightEncoder = 10;
      public static final int leftEncoder = 11;

      public static final int leftMaster = 3;
      public static final int leftSlave = 5;
      public static final int rightMaster = 2;
      public static final int rightSlave = 4;
    }
    public static final class Controls {
      public static final double sVolts = 0.672; //0.672 // kS
      public static final double vVoltsSecondsPerMeter = 1.99; //1.99 // kV
      public static final double aVoltsSecondsSquaredPerMeter = 0.0922; //0.226 // kA
      public static final double maxVolts = 10;
      public static final double maxSpeedMetersPerSecond = 2.9; // 2.7 //=-2.5; //Galactic 1.75 - Barrel - 2.5 is constant - 2.75 is max - start slightly left of center //2.75 slalom speed, bounce: 2.75, barrel: 2.7, slalom: 2.25
      public static final double maxAccelerationMetersPerSecondSquared = 2.5; // 2.515 //=-2.25; // Bounce Path speed - 5.0; //2.5; bounce: 2.95, barrel: 2.7, slalom: 2.0
      public static final double ramseteB = 2.0;
      public static final double ramseteZeta = 0.7;  // 0.15
      public static final double pVelocity = 0.175; // 0.15, 0.075
      public static final double maxCentripetalMetersPerSecondSquared = 2.5; // 2.515 //=-2.0; bounce: 2.95, barrel: 2.7, slalom: 2.0
    }
    public static final class Values {
      public static final int encoderTicksPerRevolution = 2048;
      public static final double postEncoderGearing = 9.091;
      public static final double trackWidth = 0.71; // 0.693; // 12.9798;
      public static final double wheelDiameter = 0.156972; // 0.1524;  // 6 in wheel in m
      public static final double wheelCircumference = Math.PI * wheelDiameter;
      public static final double minMovePercent = 0.27; //=~ 0.29
      public static final double maxMovePercent = 0.39;
    }

    public static final class Galactic {
      public static final double blueHeading = -20.0;
      public static final double headingTolerance = 5.0;

      public enum Color {
        DontKnow,
        Red,
        Blue
      }
    }
  }

  public static final class OI {
    public static final class Xbox {
      public static final int driverID = 0;
      public static final double deadBandThreshold = 0.25;
    }

    public static final class Buttons {
      public static final class Driver {
        public static final int collectorBtnId = XboxController.Button.kY.value;
        public static final int shooterBtnId = XboxController.Button.kX.value;
        public static final int farShotBtnId = XboxController.Button.kBumperLeft.value;
        public static final int closeShotBtnId = XboxController.Button.kBumperRight.value;
      }

      public static final class CoDriver {

      }
    }
  }

  public static final class Spindexer {
    public static final class Can {
      public static final int bigBlock = 1;
      public static final int omni = 9;
      public static final int compliant = 8;
    }
    public static final double compliantSpeed = 0.95;
    public static final double bigBlockJamCurrent = 40; // TODO test
    public static final double agitateSpeed = 0.4;
    public static final double agitateDuration = 1;
    public static final double shootSpeed = 0.95; // 0.95
  }

  public static final class Shooter {
    public static final class Can {
      public static final int flywheelMaster = 6;   // FAL-401
      public static final int flywheelSlave1 = 7;   // FAL-402
      public static final int flywheelSlave2 = 10;  // FAL-403
      public static final int flywheelSlave3 = 11;  // FAL-404
    }

    public static final class SolenoidRaise {
      public static final int extend = 6;
      public static final int retract = 1;
    }
    public static final class SolenoidLock {
      public static final int extend = 2;
      public static final int retract = 5;
    }

    public static final class Values {
      public static final DoubleSolenoid.Value cylinderExtend = DoubleSolenoid.Value.kForward;
      public static final DoubleSolenoid.Value cylinderRetract = DoubleSolenoid.Value.kReverse;
      public static final double hoodRaiseDuration = 0.7;
      public static final double hoodLockDuration = 0.5;
    }

    public static final class Targeting {

      public static class TreeMapValues{
        public static class Close {
            // public final static double ty1 = 19.30;
            // public final static double ty2 = 12.75;
            // public final static double ty3 = 0.28;
            // public final static double ty4 = 0.00;
            // public final static double ty5 = -4.63;
            // public final static double ty6 = -7.26;
            // public final static double ty7 = -9.31;
            // public final static double ty8 = -10.6;

            public final static double ty1 = -9.42;
            public final static double ty2 = -7.9;
            public final static double ty3 = -6.8;
            public final static double ty4 = -5.3;
            public final static double ty5 = -3.3;
            public final static double ty6 = -1.55;
            public final static double ty7 = 0.1;
            // public final static double ty8 = 7.15;
            public final static double ty9 = 2.0;
            // public final static double ty10 = 5.1; //
            // public final static double ty11 = 6.0;
            // public final static double ty12 = 6.5; 
            public final static double ty13 = 7.2;
            // public final static double ty14 = 12.5;
            // public final static double ty15 = 17.8;
            // public final static double ty16 = 20.9;
            public final static double ty17 = 22.0;

            // public final static double rpm1 = 2300;
            // public final static double rpm2 = 2425;
            // public final static double rpm3 = 4000;
            // public final static double rpm4 = 3800;
            // public final static double rpm5 = 3850;
            // public final static double rpm6 = 3850;
            // public final static double rpm7 = 3925;
            // public final static double rpm8 = 4135;

            public final static double rpm1 = 3800;
            public final static double rpm2 = 3700;
            public final static double rpm3 = 3700;
            public final static double rpm4 = 3700;
            public final static double rpm5 = 3750;
            public final static double rpm6 = 3800;
            public final static double rpm7 = 4750;
            // public final static double rpm8 = 2450;
            public final static double rpm9 = 2427;
            // public final static double rpm10 = 2550;
            // public final static double rpm11 = 2520;
            // public final static double rpm12 = 2500;
            public final static double rpm13 = 2427;
            // public final static double rpm14 = 2400;
            // public final static double rpm15 = 2400;
            // public final static double rpm16 = 2400;
            public final static double rpm17 = 2400;
        }
    }

      public static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> kDistanceToShooterSpeedClose = new InterpolatingTreeMap<>();
      static {
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty1), new InterpolatingDouble(TreeMapValues.Close.rpm1));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty2), new InterpolatingDouble(TreeMapValues.Close.rpm2));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty3), new InterpolatingDouble(TreeMapValues.Close.rpm3));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty4), new InterpolatingDouble(TreeMapValues.Close.rpm4));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty5), new InterpolatingDouble(TreeMapValues.Close.rpm5));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty6), new InterpolatingDouble(TreeMapValues.Close.rpm6));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty7), new InterpolatingDouble(TreeMapValues.Close.rpm7));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty8), new InterpolatingDouble(TreeMapValues.Close.rpm8));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty9), new InterpolatingDouble(TreeMapValues.Close.rpm9));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty10), new InterpolatingDouble(TreeMapValues.Close.rpm10));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty11), new InterpolatingDouble(TreeMapValues.Close.rpm11));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty12), new InterpolatingDouble(TreeMapValues.Close.rpm12));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty13), new InterpolatingDouble(TreeMapValues.Close.rpm13));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty14), new InterpolatingDouble(TreeMapValues.Close.rpm14));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty14), new InterpolatingDouble(TreeMapValues.Close.rpm14));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty15), new InterpolatingDouble(TreeMapValues.Close.rpm15));
        // kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty16), new InterpolatingDouble(TreeMapValues.Close.rpm16));
        kDistanceToShooterSpeedClose.put(new InterpolatingDouble(TreeMapValues.Close.ty17), new InterpolatingDouble(TreeMapValues.Close.rpm17));
      }
    }


    public final static double angleTolerance = 1.0;
    public final static double angleErrorToSpinUp = 5.0;
    public final static double rpmTolerance = 100.0;
    public final static double ticksPerRotation = 2048; //for falcons
    public final static double milliPerMin = 600;
    public final static double reduction = 1.0;
  }

  public static final class LimeLight {
    public final static double pipeline = 9;
    public final static double tyConfigLowThreshold = 0.5;
  }

  public static final class Challenges {
    public final static double PowerPortDistance = 2.75;
    public final static double PowerPortShootDuration = 1.25;
    public final static double AccuracyGreenDistance = 5.5;
    public final static double AccuracyYellowDistance = 4.0;
    public final static double AccuracyBlueDistance = 2.5;
    public final static double AccuracyRedDistance = 1.0;
  }
  
}
