package frc.robot.controllers;

import org.montclairrobotics.alloy.components.Component;
import org.montclairrobotics.alloy.components.Step;
import org.montclairrobotics.alloy.frc.FRCButton;


public class IntakeController implements Step<Double> {

    FRCButton launch;

    public IntakeController(FRCButton launch) {
        this.launch = launch;
    }

    @Override
    public Double getOutput(Double aDouble) {
        if(Math.abs(aDouble) < 0.05) return .1;
        if(launch.getValue()) return 1D;
        return aDouble;
    }
}
