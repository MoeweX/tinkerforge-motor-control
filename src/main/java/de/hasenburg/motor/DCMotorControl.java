package de.hasenburg.motor;

import com.tinkerforge.BrickServo;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * 
 * This class is used to control a single DC motor with a Tinkerforge ServoBrick
 * ({@link BrickServo}) and the motor driver L9110. In theory, the L9110 can control two DC
 * motors at a time, for that simply create another {@link DCMotorControl}.
 * 
 * Two {@link PWMGenerator} are needed. Connect the PWM pins of the servo brick to IA1 and IB1
 * (or IA2 and IB2) of the L9110. Then, connect the power source VCC to the VCC pin on the
 * L9110. Finally, connect the power source GND to the GND pin of the L9110.
 * 
 * If forward lets the motor spin in the "wrong" direction, switch the order of the two PWM
 * pins.
 * 
 * WARNING: You should not go from fullspeed in one direction to full speed in the other
 * direction!
 * 
 * @author jonathanhasenburg
 *
 */
public class DCMotorControl {

	private final PWMGenerator pwm1;
	private final PWMGenerator pwm2;

	/**
	 * Create a new {@link DCMotorControl}. The controller can be used without any further
	 * modifications. Do not forget to run {@link #shutdown()} at the end.
	 * 
	 * @param servoBrick - the {@link BrickServo} the controller should use
	 * @param pwmPin1 - the first pwm pin used by {@link PWMGenerator}
	 * @param pwmPin2 - the second pwm pin used by {@link PWMGenerator}
	 * @throws NotConnectedException
	 * @throws TimeoutException
	 */
	public DCMotorControl(BrickServo servoBrick, short pwmPin1, short pwmPin2)
			throws TimeoutException, NotConnectedException {
		pwm1 = new PWMGenerator(servoBrick, pwmPin1);
		pwm2 = new PWMGenerator(servoBrick, pwmPin2);
		pwm1.initialize();
		pwm2.initialize();
	}

	/**
	 * Stops the motor and disables the related pins on the servo brick.
	 * 
	 * @throws TimeoutException
	 * @throws NotConnectedException
	 */
	public void shutdown() throws TimeoutException, NotConnectedException {
		pwm1.shutdown();
		pwm2.shutdown();
	}

	/**
	 * Lets the motor drive forward.
	 * 
	 * WARNING: You should not go from fullspeed in one direction to full speed in the other
	 * direction!
	 * 
	 * @param speed - in percent (0 - 100)
	 * @throws TimeoutException
	 * @throws NotConnectedException
	 */
	public void forward(int speed) throws TimeoutException, NotConnectedException {
		pwm1.setSpeedInPercent(speed);
		pwm2.setSpeedInPercent(0);
	}

	/**
	 * Lets the motor drive backward.
	 * 
	 * WARNING: You should not go from fullspeed in one direction to full speed in the other
	 * direction!
	 * 
	 * @param speed - in percent (0 - 100)
	 * @throws TimeoutException
	 * @throws NotConnectedException
	 */
	public void backward(int speed) throws TimeoutException, NotConnectedException {
		pwm1.setSpeedInPercent(0);
		pwm2.setSpeedInPercent(speed);
	}

	/**
	 * Stops the motor.
	 * 
	 * @throws TimeoutException
	 * @throws NotConnectedException
	 */
	public void stop() throws TimeoutException, NotConnectedException {
		pwm1.setSpeedInPercent(0);
		pwm2.setSpeedInPercent(0);
	}

}
