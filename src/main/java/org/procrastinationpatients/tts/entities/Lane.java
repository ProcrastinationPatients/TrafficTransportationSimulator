package org.procrastinationpatients.tts.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by decker on 15-1-28.
 */
public class Lane {

    private List<Lane> inputs;
    private List<Lane> outputs;
    private Vehicle [] vehicles;

    public Lane(int length) {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.setVehicles(new Vehicle[length]);
    }

    public List<Lane> getInputs() {
        return inputs;
    }

    public void setInputs(List<Lane> inputs) {
        this.inputs = inputs;
    }

    public List<Lane> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Lane> outputs) {
        this.outputs = outputs;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public void setVehicles(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }
}
