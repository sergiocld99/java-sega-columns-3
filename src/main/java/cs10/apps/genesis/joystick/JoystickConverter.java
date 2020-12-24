package cs10.apps.genesis.joystick;

public class JoystickConverter {

    public static Keys convertKey(String componentName, float value){

        // 0 if i left the button

        if (componentName.contains("X")){
            if (value == -1f) return Keys.LEFT;
            else if (value == 1f) return Keys.RIGHT;
        } else if (componentName.contains("Y")){
            if (value == 1f) return Keys.DOWN;
            else if (value == -1f) return Keys.UP;
        } else if (componentName.contains("2")){
            if (value == 1f) return Keys.B;
        } else if (componentName.contains("1")){
            if (value == 1f) return Keys.C;
        } else if (componentName.contains("3")){
            if (value == 1f) return Keys.START;
        }

        return null;
    }
}
