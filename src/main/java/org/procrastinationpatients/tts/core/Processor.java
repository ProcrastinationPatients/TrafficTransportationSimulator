package org.procrastinationpatients.tts.core;

import org.procrastinationpatients.tts.entities.Cross;
import org.procrastinationpatients.tts.entities.Movement;
import org.procrastinationpatients.tts.entities.Produce;

/**
 * Created by decker on 15-2-1.
 */
public class Processor {
    Produce produce;
    Movement movement;
    Thread produceThread;
    Thread moveThread;
	Thread trafficLight;

    public Processor() {
        this.produce = new Produce();
        this.produceThread = new Thread(produce);
        this.movement = new Movement(produce.getAllVehicle());
        this.moveThread = new Thread(this.movement);
		this.trafficLight = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					Cross.changeTrafficLight();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
    }

    public void start() {
        this.produceThread.start();
        this.moveThread.start();
		this.trafficLight.start();
    }

    @Deprecated
    public void pause() {
        Engine.getInstance().setIsPaused(true);
    }

    @Deprecated
    public void resume() {
        Engine.getInstance().setIsPaused(false);
    }

    @Deprecated
    public void stop() {
        Engine.getInstance().setIsStopped(true);
    }


}
