package frc.robot;

import org.montclairrobotics.alloy.core.Button;
import org.montclairrobotics.alloy.drive.ArcadeDrive;
import org.montclairrobotics.alloy.frc.FRCButton;
import org.montclairrobotics.alloy.frc.FRCJoystick;

class Control{
    public class Port{
        public static final int DRIVE_STICK = 0;
        public static final int AUX_STICK = 0;

        public static final int GYRO_LOCK = 1;
        public static final int COMPRESSOR = 3;
        public static final int SOLENOID = 2;
    }

    public static FRCJoystick driveStick;
    public static FRCJoystick auxStick;

    public static Button gyroLockButton;

    public static ArcadeDrive dt_input;

    public static Button compressor; 
    public static Button solenoid;

    public static void init(){
        driveStick = new FRCJoystick(Port.DRIVE_STICK);
        auxStick = new FRCJoystick(Port.AUX_STICK);
        gyroLockButton = new FRCButton(driveStick, Port.GYRO_LOCK);
        dt_input = new ArcadeDrive(driveStick, driveStick);
        compressor = new FRCButton(driveStick, Port.COMPRESSOR);
        solenoid = new FRCButton(driveStick, Port.SOLENOID);
    }
}