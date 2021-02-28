// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.XboxController;

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
      public static final double motorIn = 0.35;
      public static final double motorOut = -0.5;
      public static final double motorStop = 0.0;
    }

  }

  public static final class Drive {
    public static final class Can {
      public static final int leftMaster = 3;
      public static final int leftSlave = 5;
      public static final int rightMaster = 2;
      public static final int rightSlave = 4;
    }
    public static final class Controls {
      public static final double sVolts = 0.713;  // kS
      public static final double vVoltsSecondsPerMeter = 2.03;  // kV
      public static final double aVoltsSecondsSquaredPerMeter = 0.219;  // kA
      public static final double maxVolts = 10;
      public static final double maxSpeedMetersPerSecond = 2.0;
      public static final double maxAccelerationMetersPerSecondSquared = 2.0;
      public static final double ramseteB = 2.0;
      public static final double ramseteZeta = 0.15;
      public static final double pVelocity = 0.15;
      public static final double maxCentripetalMetersPerSecondSquared = 1.0;
    }
    public static final class Values {
      public static final int encoderTicksPerRevolution = 2048;
      public static final double postEncoderGearing = 9.091;
      public static final double trackWidth = 0.68; // 12.9798;
      public static final double wheelDiameter = 0.156972; // 0.1524;  // 6 in wheel in m
      public static final double wheelCircumference = Math.PI * wheelDiameter;
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
  }

  public static final class Shooter {
    public static final class Can {
      public static final int flywheelMaster = 6;
      public static final int flywheelSlave = 7;
    }
    public final static double angleTolerance = 1.0;
    public final static double angleErrorToSpinUp = 5.0;
    public final static double rpmTolerance = 25.0;
    public final static double ticksPerRotation = 2048; //for falcons
    public final static double milliPerMin = 600;
    public final static double reduction = 1.0;
  }

}
