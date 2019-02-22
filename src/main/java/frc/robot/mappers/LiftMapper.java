package frc.robot.mappers;

import org.montclairrobotics.alloy.motor.Mapper;
import org.montclairrobotics.alloy.motor.MotorModule;

public class LiftMapper implements Mapper<Double> {

    @Override
    public void map(Double aDouble, MotorModule... motorModules) {
        for(MotorModule m : motorModules){
            m.setPower(aDouble);
        }
    }
}
