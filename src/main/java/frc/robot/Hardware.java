package frc.robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SPI;


public class Hardware {
    
    public static class DeviceID {
        // Drive Train ID's
        public static final int DRIVE_RIGHT_FRONT = 1;
        public static final int DRIVE_RIGHT_BACK = 2;
        public static final int DRIVE_LEFT_FRONT = 3;
        public static final int DRIVE_LEFT_BACK = 4;
      
        public static final SPI.Port navxPort = SPI.Port.kMXP;

    }
    


    public static WPI_TalonSRX dt_rightFront;
    public static WPI_TalonSRX dt_rightBack;
    public static WPI_TalonSRX dt_leftFront;
    public static WPI_TalonSRX dt_leftBack;
    
    public static void init(){
        System.out.println("Initializing Hardware");
        dt_rightFront = new WPI_TalonSRX(DeviceID.DRIVE_RIGHT_FRONT);
        dt_rightBack = new WPI_TalonSRX(DeviceID.DRIVE_RIGHT_BACK);
        dt_leftFront = new WPI_TalonSRX(DeviceID.DRIVE_LEFT_FRONT);
        dt_leftBack = new WPI_TalonSRX(DeviceID.DRIVE_LEFT_BACK);

    }
}

