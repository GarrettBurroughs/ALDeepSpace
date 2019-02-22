/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.core;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controllers.IntakeController;
import frc.robot.controllers.LiftController;
import frc.robot.mappers.LiftMapper;
import org.montclairrobotics.alloy.components.Component;
import org.montclairrobotics.alloy.control.ToggleButton;
import org.montclairrobotics.alloy.core.LimitedMotor;
import org.montclairrobotics.alloy.drive.DriveModule;
import org.montclairrobotics.alloy.drive.DriveTrain;
import org.montclairrobotics.alloy.drive.MecanumDrive;
import org.montclairrobotics.alloy.frc.*;
import org.montclairrobotics.alloy.motor.DefaultMapper;
import org.montclairrobotics.alloy.motor.MotorGroup;
import org.montclairrobotics.alloy.motor.MotorModule;
import org.montclairrobotics.alloy.steps.*;
import org.montclairrobotics.alloy.utils.GyroCorrection;
import org.montclairrobotics.alloy.utils.PID;
import org.montclairrobotics.alloy.vector.Angle;
import org.montclairrobotics.alloy.vector.Polar;
import org.montclairrobotics.alloy.vector.Vector;
import org.montclairrobotics.alloy.vector.XY;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends FRCAlloy {
    private DriveTrain driveTrain;
    private MotorGroup<Double> lift;
    private MotorGroup<Double> intake;

    private VisionCorrection hatchIntakeCorrection;

    private GyroLock lock;
    private FieldCentric fieldCentric;
    private Sensitivity sensitivity;
    private VisionCorrection visionCorrection;

    private FRCLimitSwitch mainLimit;
    private FRCLimitSwitch secondLimit;

    private NavxGyro navxGyro;

    private Compressor compressor;
    private Solenoid solenoid;

    @Override
    public void initialization() {
        Hardware.init();
        Control.init();

        driveTrain = new MecanumDrive(
                new DriveModule(
                        new XY(1, 1), new Polar(-1, new Angle(-45)),
                        new FRCEncoder(Hardware.dt_rightFront), new PID(0, 0, 0),
                        new FRCMotor(Hardware.dt_rightFront)
                ),
                new DriveModule(
                        new XY(1, -1), new Polar(-1, new Angle(-45)),
                        new FRCEncoder(Hardware.dt_rightBack), new PID(0, 0, 0),
                        new FRCMotor(Hardware.dt_rightBack)
                ),
                new DriveModule(
                        new XY(-1, 1), new Polar(1, new Angle(45)),
                        new FRCEncoder(Hardware.dt_leftFront), new PID(0, 0, 0),
                        new FRCMotor(Hardware.dt_leftFront)
                ),
                new DriveModule(
                        new XY(-1, -1), new Polar(1, new Angle(45)),
                        new FRCEncoder(Hardware.dt_leftBack), new PID(0, 0, 0),
                        new FRCMotor(Hardware.dt_leftBack)
                )
        );

        lift = new MotorGroup<>(
                Control.liftInput, new LiftMapper(),
                new MotorModule(Vector.ZERO, Vector.ZERO, new LimitedMotor(new FRCMotor(Hardware.lift_1), mainLimit, () -> Hardware.lift_encoder.get() > 10000000)),
                new MotorModule(Vector.ZERO, Vector.ZERO, new LimitedMotor(new FRCMotor(Hardware.lift_2), mainLimit, () -> Hardware.lift_encoder.get() > 10000000)),
                new MotorModule(Vector.ZERO, Vector.ZERO, new LimitedMotor(new FRCMotor(Hardware.lift_3), secondLimit, () -> Hardware.lift_encoder.get() > 330000.0))
        );

        intake = new MotorGroup<>(
                Control.intakeInput,
                new DefaultMapper(),
                new MotorModule(new XY(1, 0), new XY(0, 1), new FRCMotor(Hardware.intake_right)),
                new MotorModule(new XY(-1, 0), new XY(0, 1), new FRCMotor(Hardware.intake_left))
        );

        compressor = new Compressor(0);
        solenoid = new Solenoid(new edu.wpi.first.wpilibj.Solenoid(3));
        navxGyro = new NavxGyro(Hardware.navx);

        mainLimit = new FRCLimitSwitch(9);
        secondLimit = new FRCLimitSwitch(8);

        GyroCorrection.setGeneralCorrection(new GyroCorrection(navxGyro, new PID(-0.3, 0, -0.00035)));
    }

    @Override
    public void periodic() {
        Component.debugger.test("Pressure Switch Valve", compressor.getPressureSwitchValue());
        Component.debugger.test("Compressor Current", compressor.getCompressorCurrent());
        Component.debugger.test("second lift encoder", Hardware.second_lift_encoder.get());
        Component.debugger.test("Lift Encoder", Hardware.lift_encoder.get());

        if(secondLimit.get()){
            Hardware.second_lift_encoder.reset();
        }
        if(mainLimit.get()){
            Hardware.lift_encoder.reset();
        }
    }

    @Override
    public void robotSetup() {
        lock = new GyroLock();
        fieldCentric = new FieldCentric();
        sensitivity = new Sensitivity(.3);
        hatchIntakeCorrection = new VisionCorrection(() -> SmartDashboard.getNumber("HatchX", 0), new PID(1, 0, 0));

        // Add modifiers to the controls
        Control.dt_input.addStep(new Deadzone());
        Control.dt_input.addStep(lock);
        Control.dt_input.addStep(fieldCentric);
        Control.dt_input.addStep(sensitivity);

        new ToggleButton(Control.gyroLock, lock);

        PressureRegulator p = new PressureRegulator(compressor);

        // Set the Drivetrain Input
        driveTrain.setInput(Control.dt_input);
        setDriveTrain(driveTrain);

        Control.intakeInput.addStep(new IntakeController(Control.ballFire));
        Control.liftInput.addStep(new LiftController(new PID(0, 0, 0), Control.liftTop, Control.liftBot));

        ToggleButton fieldCentricButton = new ToggleButton(Control.fieldCentric, fieldCentric);
        ToggleButton gyroLockButton = new ToggleButton(Control.gyroLock, lock);
        ToggleButton solenoidButton = new ToggleButton(Control.solenoid, lock);

    }
}
