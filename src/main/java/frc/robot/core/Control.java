package frc.robot.core;

import org.montclairrobotics.alloy.components.InputComponent;
import org.montclairrobotics.alloy.drive.ArcadeDrive;
import org.montclairrobotics.alloy.frc.FRCButton;
import org.montclairrobotics.alloy.frc.FRCJoystick;
import org.montclairrobotics.alloy.frc.POVButton;
import org.montclairrobotics.alloy.utils.Input;

/**
 * The Control class is in charge of storing all of the port
 * configurations and control elements for the 2019 DeepSpace robot.
 *
 * A control element is roughly defined as something that's only
 * use is as an input, that directly affects another part of the code
 * or the robot.
 *
 * This includes things like: Buttons, Joysticks, Sensors, etc.
 *
 * The only thing that goes against this rule, are motor encoders,
 * as they are seen to be a part of the motor hardware, and therefore
 * go into the hardware class
 *
 * @see Hardware
 *
 * Structure - This defines where and in what order to declare objects and port ID's
 *
 * Hierarchy - Always define in order from first to last
 *  - Drive Sticks
 *  - Drive Modifiers
 *  - Buttons
 *  - Misc
 *
 *  Sections - Order and sections of declaration and instantiation
 *  - Declaration of all ports
 *  - Creation of all control objects
 *  - Instantiation of all control objects
 */
public class Control{

    public enum Port{

        // Driver-Assist
        GYRO_LOCK(0,12,0),    // Joystick: Drive, Button: L3
        FIELD_CENTRIC(0,6,0), // Joystick: Drive, Button: L1
        ALIGNMENT(0,4,0),    // Joystick: Drive, Button: L2

        // Lift Controls
        LIFT_RESET(1,1,0),   // Joystick: Aux,  Button: Square
        LIFT_BOT(1,2,0),     // Joystick: Aux,  Button: X
        LIFT_MID(1,3,0),     // Joystick: Aux,  Button: Triangle
        LIFT_TOP(1,4,0),     // Joystick: Aux,  Button Triangle

        // Intake Controls //TODO: Test POV Buttons
        INTAKE_UP(1,1,0),    // Joystick: Aux, Button: DPAD Up
        INTAKE_DOWN(1,4,180),  // Joystick: Aux, Button: DPAD Down

        // Fire Controls
        SOLENOID(1,7,0),     // Joystick: Aux, Button: L2
        BALL_FIRE(1,8,0),    // Joystick: Aux, Button: R2

        // Camera Controls
        SWAP_CAMERA(1,14,0); // Joystick: Driver, Button: Touch Pad

        private int stick, button, angle;

        Port(int stick, int button, int angle){
            this.stick = stick;
            this.button = button;
            this.angle = angle;
        }

        public int getStick() {
            return stick;
        }

        public int getButton() {
            return button;
        }

        public int getAngle(){
            return angle;
        }
    }

    public static FRCJoystick driveStick;
    public static FRCJoystick auxStick;

    public static ArcadeDrive dt_input;
    public static InputComponent<Double> intakeInput;
    public static InputComponent<Double> liftInput;

    public static FRCButton gyroLock;
    public static FRCButton fieldCentric;
    public static FRCButton solenoid;

    public static FRCButton ballFire;

    public static FRCButton liftReset;
    public static FRCButton liftTop;
    public static FRCButton liftMid;
    public static FRCButton liftBot;

    public static POVButton intakeUp;
    public static POVButton intakeDown;

    public static Input<Double> DRIVE_RIGHT_X_AXIS;
    public static Input<Double> DRIVE_RIGHT_Y_AXIS;
    public static Input<Double> DRIVE_LEFT_X_AXIS;
    public static Input<Double> DRIVE_LEFT_Y_AXIS;

    public static Input<Double> AUX_RIGHT_X_AXIS;
    public static Input<Double> AUX_RIGHT_Y_AXIS;
    public static Input<Double> AUX_LEFT_X_AXIS;
    public static Input<Double> AUX_LEFT_Y_AXIS;

    public static void init(){
        driveStick = new FRCJoystick(0);
        auxStick = new FRCJoystick(1);

        dt_input = new ArcadeDrive(driveStick, driveStick);
        liftInput = new InputComponent<Double>(){}.setInput(AUX_RIGHT_Y_AXIS);
        intakeInput = new InputComponent<Double>(){}.setInput(AUX_LEFT_Y_AXIS);


        ballFire = new FRCButton(auxStick, Port.BALL_FIRE.getButton());

        liftReset = new FRCButton(auxStick, Port.LIFT_RESET.getButton());
        liftTop = new FRCButton(auxStick, Port.LIFT_TOP.getButton());
        liftMid = new FRCButton(auxStick, Port.LIFT_MID.getButton());
        liftBot = new FRCButton(auxStick, Port.LIFT_BOT.getButton());

        intakeUp = new POVButton(Port.INTAKE_UP.angle, driveStick.getStick());

        intakeDown = new POVButton(Port.INTAKE_DOWN.angle, driveStick.getStick());

        gyroLock = new FRCButton(driveStick, Port.GYRO_LOCK.getButton());
        fieldCentric = new FRCButton(driveStick, Port.FIELD_CENTRIC.getButton());
        solenoid = new FRCButton(driveStick, Port.SOLENOID.getButton());


        DRIVE_RIGHT_X_AXIS = () -> driveStick.getStick().getRawAxis(2);
        DRIVE_RIGHT_Y_AXIS = () -> driveStick.getStick().getRawAxis(5);
        DRIVE_LEFT_X_AXIS = () -> driveStick.getStick().getRawAxis(0);
        DRIVE_LEFT_Y_AXIS = () -> driveStick.getStick().getRawAxis(1);

        AUX_RIGHT_X_AXIS = () -> auxStick.getStick().getRawAxis(2);
        AUX_RIGHT_Y_AXIS = () -> auxStick.getStick().getRawAxis(5);
        AUX_LEFT_X_AXIS = () -> auxStick.getStick().getRawAxis(0);
        AUX_LEFT_Y_AXIS = () -> auxStick.getStick().getRawAxis(1);
    }
}