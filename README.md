# Tinkerforge Motor Control

The DCMotorControl.java class makes it easy to control up to 3 (and a half) DC motors with L9110 boards (3€ on ebay) via the Tinkerforge Servo Brick. This is a lot cheaper than using the DC Brick. However, you can only drive small motors with these boards. It is very easy to use:

~~~~
DCMotorControl motor = new DCMotorControl(SERVO_BRICK, PWM_PIN_1, PWM_PIN_2);
motor.forward(100); // drive the motor forward with 100% speed
motor.stop();
motor.backward(60); // drive the motor backwards with 60% speed
motor.shutdown(); // shutdown the motor (and disable the servo pins)
~~~~

Please checkout the DCMotorControl.java class for documentation on how to wire everything up and a more detailed documentation. Execute the main method in Starter.java (after you added your ServoBrick UID) to control a motor using the Servo pins 0 and 1.

WARNING: DO NOT USE THE POWER PINS (MIDDLE AND LOWER ONE) TO DRIVE THE MOTOR. YOU NEED AN ADDITIONAL POWER SOURCE.
