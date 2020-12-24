package cs10.apps.genesis.joystick;

import net.java.games.input.*;

public class Test {

    public void readJoystickEvents(){
        while (true) {
            /* Get the available controllers */
            Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
            if (controllers.length == 0) {
                System.out.println("Found no controllers.");
                System.exit(0);
            }

            for (Controller controller : controllers) {
                if (!controller.getName().contains("Axis")) continue;

                /* Remember to poll each one */
                controller.poll();

                /* Get the controllers event queue */
                EventQueue queue = controller.getEventQueue();

                /* Create an event object for the underlying plugin to populate */
                Event event = new Event();

                /* For each object in the queue */
                while (queue.getNextEvent(event)) {
                    Keys key = JoystickConverter.convertKey(event.getComponent().getName(), event.getValue());
                    if (key != null) System.out.println(key.name());
                }
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.readJoystickEvents();
    }
}
