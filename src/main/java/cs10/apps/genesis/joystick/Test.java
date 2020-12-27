package cs10.apps.genesis.joystick;

import cs10.apps.columns.core.Game;
import net.java.games.input.*;

public class Test {
    private final Game game;

    public Test(){
        game = new Game();
        game.start();
    }

    public void readJoystickEvents(){
        while (!game.isStarted()) {
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
                    if (key != null) {
                        if (key == Keys.START && !game.isStarted()) game.start();
                        else if (key == Keys.B) game.getFallingBlock().rotate();
                        else if (key == Keys.C) game.getPlayer1().push();
                        else if (key == Keys.LEFT) game.getFallingBlock().moveLeft();
                        else if (key == Keys.RIGHT) game.getFallingBlock().moveRight();
                        else if (key == Keys.DOWN) game.getFallingBlock().moveDown(true);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.readJoystickEvents();
    }
}
