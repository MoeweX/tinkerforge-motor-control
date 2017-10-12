package de.hasenburg.motor;

import com.tinkerforge.BrickServo;
import com.tinkerforge.IPConnection;

public class Starter {

	public static void main(String args[]) throws Exception {
		String HOST = "localhost";
		int PORT = 4223;
		String UID = "6qCy7n";

		IPConnection ipcon = new IPConnection(); // Create IP connection
		BrickServo servoBrick = new BrickServo(UID, ipcon); // Create device object
		ipcon.connect(HOST, PORT); // Connect to brickd
		
		DCMotorControl motor = new DCMotorControl(servoBrick, (short) 0, (short) 1);

		System.out.println("Press a button to run forward at 100% speed");
		System.in.read();
		motor.forward(100);
		
		System.out.println("Press a button to run forward at 60% speed");
		System.in.read();
		motor.forward(60);
		
		System.out.println("Press a button to stop the motor");
		System.in.read();
		motor.stop();
		
		System.out.println("Press a button to run backwards at 100% speed");
		System.in.read();
		motor.backward(100);

		System.out.println("Press a button to stop the motor and terminate the program");
		System.in.read();
		motor.shutdown();
		
		ipcon.disconnect();
	}

}
