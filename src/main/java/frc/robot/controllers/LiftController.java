package frc.robot.controllers;

import org.montclairrobotics.alloy.components.Component;
import org.montclairrobotics.alloy.components.Step;
import org.montclairrobotics.alloy.control.ButtonAction;
import org.montclairrobotics.alloy.core.Button;
import org.montclairrobotics.alloy.update.Update;
import org.montclairrobotics.alloy.utils.ErrorCorrection;

public class LiftController extends Component implements Step<Double> {
    ErrorCorrection<Double> correction;

    private Button up;
    private Button down;

    private int pos = 0;
    private int[] positions = {0, 100, 1000};

    public LiftController(ErrorCorrection<Double> correction, Button up, Button down) {
        this.correction = correction;
        this.up = up;
        this.down = down;

        new ButtonAction(up).addOnPressedAction(this::decrement);
        new ButtonAction(down).addOnPressedAction(this::increment);
    }

    @Override
    public Double getOutput(Double aDouble) {
        if(status.isEnabled()){
            return aDouble + correction.getCorrection();
        }else {
            return aDouble;
        }
    }

    @Update
    public void control(){
        if(up.getValue() || down.getValue()){
            enable();
        }else {
            disable();
        }
    }

    public void increment(){
        if(pos + 1 < positions.length){
            pos++;
        }
        correction.setTarget((double) positions[pos]);
    }

    public void decrement(){
        if(pos - 1 > 0){
            pos--;
        }
        correction.setTarget((double) positions[pos]);
    }

    @Override
    public void enableAction() {
    }

    @Override
    public void disableAction() {

    }
}
