package pl.butajlo.androidadvanced.ui;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;

public interface ScreenNavigator {

    void initWithRouter(Router router, Controller rootScreen);

    /**
     * Pop the actual screen
     * @return true if the screen was popped, false if not
     */
    boolean pop();

    /**
     * It is used to clean the router, if the activity is destroyed
     */
    void clear();
}
