package de.hasenburg.motor;

import com.tinkerforge.BrickServo;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * The PWM Generator generates a PWM signal on the most upper pin of the Tinkerforge
 * ServoBrick at a given servo index (0 to 6) to control a DC motor's speed. NOTE: THE PWM
 * SIGNAL OF THE SERVOBRICK IS NOT STRONG ENOUGH TO DRIVE THE MOTOR BY ITSELF.
 * 
 * With {@link #setSpeedInPercent(int)}, one can that the speed for the motor in 1% steps (0%
 * to 100%).
 * 
 * Do not forget to run {@link #initialize()} before setting the speed and make sure to run
 * {@link #shutdown()} at the end.
 * 
 * @author jonathanhasenburg
 *
 */
public class PWMGenerator {

	private final int PWM_FREQUENCY = 175000; // in Hz [15Hz to 1MHz]

	private short servoNumber;
	private BrickServo servoBrick;

	public PWMGenerator(BrickServo servoBrick, short servoNumber) {
		this.servoNumber = servoNumber;
		this.servoBrick = servoBrick;
	}

	public void initialize() throws TimeoutException, NotConnectedException {
		// Set degree range to 0-100, this will allow to set the PWM duty cycle in 1% steps
		servoBrick.setDegree(servoNumber, (short) 0, (short) 100);

		// Set PWM frequency (1-65535µs == 1MHz-15Hz)
		int period = 1000000 / PWM_FREQUENCY;
		if (period < 1) {
			period = 1; // 1MHz
		} else if (period > 65535) {
			period = 65535; // ~15Hz
		}
		servoBrick.setPulseWidth(servoNumber, 0, period);
		servoBrick.setPeriod(servoNumber, period);

		// Fast acceleration and full speed
		servoBrick.setAcceleration(servoNumber, 65535);
		servoBrick.setVelocity(servoNumber, 65535);

		// Set position to 0
		setSpeedInPercent(0);

		// Enable PWM signal
		servoBrick.enable(servoNumber);
	}

	public void shutdown() throws TimeoutException, NotConnectedException {
		// Slow down
		setSpeedInPercent(0);

		// Shut off
		servoBrick.disable(servoNumber);
	}

	public void setSpeedInPercent(int speed) throws TimeoutException, NotConnectedException {
		// Set PWM duty cycle (0-100 %)
		int position = speed;
		if (position < 0) {
			position = 0;
		} else if (position > 100) {
			position = 100;
		}
		servoBrick.setPosition(servoNumber, (short) position);
	}

}
