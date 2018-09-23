package pl.butajlo.androidadvanced.lifecycle

import android.view.View

abstract class ScreenLifecycleTask {

    /**
     * Callback received when a Screen becomes the visible screen.
     */
    fun onEnterScope(view: View) {

    }

    /**
     * Callback received when a Screen is popped or moved to the back stack.
     */
    fun onExitScope() {

    }

    /** Callback received when a Screen is destroyed and will not be available to use again.
     * This should be used to clear any {@link ActivityScope} connections (Disposables, etc.) to avoid memory leaks.
     */
    fun onDestroy() {

    }

}