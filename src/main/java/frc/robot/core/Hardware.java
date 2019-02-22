package frc.robot.core;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;


/**
 * The hardware class is in charge of storing all hardware
 * devices and configurations that the 2019 DeepSpace robot
 * will use. The configurations are based on the google sheet
 * that can be found Here: <link>https://docs.google.com/spreadsheets/d/1AGDKN64j39yfSKluaY3xmQL363ybChiW1dxFRdN-Dng/edit?usp=sharing</link>
 * The sheet is in place so that both code and electronics are
 * on the same page from the start on what the device configurations
 * are.
 *
 * Hardware device: Any physical device on the robot that is connected to the electronics
 * board or on roborio. This includes motors, cameras, servos, etc.
 *
 * Sensors and inputs should be defined in the control class and not in hardware
 *
 * Structure
 *
 * - Device Port configuration: All port ID's for hardware devices.
 *      - Drive Train Motor ID's: motor ports to be used for the drive train
 * - Motor Configuration: Declaration of all motor controllers on the robot
 *      - Drive Train Motors: Declaration of the drive train motors
 *
 */
public class Hardware {
    private enum DeviceID{
        DRIVE_RIGHT_FRONT(1),// Forwards
        DRIVE_RIGHT_BACK(13), // Forwards
        DRIVE_LEFT_FRONT(0), // Backwards
        DRIVE_LEFT_BACK(14), // Backwards

        INTAKE_MOTOR_RIGHT(4),
        INTAKE_MOTOR_LEFT(5),

        LIFT_MOTOR_1(12),
        LIFT_MOTOR_2(2),
        LIFT_MOTOR_3(15),

        INTAKE_MOTOR_ROTATE(3);

        private int deviceID;
        DeviceID(int deviceID){
            this.deviceID = deviceID;
        }

        public int getDeviceID() {
            return deviceID;
        }
    }

    public static WPI_TalonSRX dt_rightFront;
    public static WPI_TalonSRX dt_rightBack;
    public static WPI_TalonSRX dt_leftFront;
    public static WPI_TalonSRX dt_leftBack;

    public static WPI_TalonSRX intake_right;
    public static WPI_VictorSPX intake_left;
    public static WPI_TalonSRX intake_rotate;

    public static Encoder intake_rotate_encoder;

    public static WPI_TalonSRX lift_1;
    public static WPI_TalonSRX lift_2;
    public static WPI_TalonSRX lift_3;

    public static Encoder lift_encoder;
    public static Encoder second_lift_encoder;

    public static AHRS navx;

    public static void init(){
        System.out.println("Initializing Hardware");

        dt_rightFront = new WPI_TalonSRX(DeviceID.DRIVE_RIGHT_FRONT.getDeviceID());
        dt_rightBack =  new WPI_TalonSRX(DeviceID.DRIVE_RIGHT_BACK.getDeviceID());
        dt_leftFront =  new WPI_TalonSRX(DeviceID.DRIVE_LEFT_FRONT.getDeviceID());
        dt_leftBack =   new WPI_TalonSRX(DeviceID.DRIVE_LEFT_BACK.getDeviceID());

        intake_right  = new WPI_TalonSRX(DeviceID.INTAKE_MOTOR_RIGHT.getDeviceID());
        intake_left   = new WPI_VictorSPX(DeviceID.INTAKE_MOTOR_LEFT.getDeviceID());
        intake_rotate = new WPI_TalonSRX(DeviceID.INTAKE_MOTOR_ROTATE.getDeviceID());

        lift_1        = new WPI_TalonSRX(DeviceID.LIFT_MOTOR_1.getDeviceID());
        lift_2        = new WPI_TalonSRX(DeviceID.LIFT_MOTOR_2.getDeviceID());
        lift_2.setInverted(true);
        lift_3        = new WPI_TalonSRX(DeviceID.LIFT_MOTOR_3.getDeviceID());
        // lift_3.setInverted(true);

        intake_rotate_encoder = new Encoder(4, 5);

        navx = new AHRS(I2C.Port.kMXP);
    }

}