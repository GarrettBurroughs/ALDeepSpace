/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.montclairrobotics.alloy.drive.DriveTrain;
import org.montclairrobotics.alloy.drive.TankDrive;
import org.montclairrobotics.alloy.frc.FRCAlloy;
import org.montclairrobotics.alloy.frc.FRCMotor;
import org.montclairrobotics.alloy.motor.MotorModule;
import org.montclairrobotics.alloy.steps.Deadzone;
import org.montclairrobotics.alloy.vector.XY;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends FRCAlloy {

  @Override
  public DriveTrain getDriveTrain() {
    return this.driveTrain;
  }

  @Override
  public void initialization() {
    
  }

  @Override
  public void periodic() {

  }

  @Override
  public void robotSetup() {
     // Create Drivetrain Modules (wheels)
        // By making it a tank drive, we use the default tank drive mapper
        DriveTrain dt =
                new TankDrive(
                        new MotorModule(new XY(0, 1), new XY(1, 0), 
                          new FRCMotor(Hardware.dt_rightFront),
                          new FRCMotor(Hardware.dt_leftBack)
                        ),
                        new MotorModule(new XY(0, 1), new XY(-1, 0), 
                          new FRCMotor(Hardware.dt_leftFront), 
                          new FRCMotor(Hardware.dt_leftBack)
                        )
                );


        // Add modifiers toe the controls
        Control.dt_input.addStep(new Deadzone());

        // Set the Drivetrain Input
        dt.setInput(Control.dt_input);

        setDriveTrain(dt);

  }

  @Override
  public void setDriveTrain(DriveTrain arg0) {
    this.driveTrain = arg0;
  }

}
