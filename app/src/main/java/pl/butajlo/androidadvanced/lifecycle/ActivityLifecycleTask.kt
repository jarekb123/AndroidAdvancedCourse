package pl.butajlo.androidadvanced.lifecycle

import pl.butajlo.androidadvanced.base.BaseActivity

abstract class ActivityLifecycleTask {

    /*
     * There are empty methods, because it is not needed that every subclass has to implement
     * all these methods.
     */

    open fun onCreate(activity: BaseActivity) {}

    open fun onStart(activity: BaseActivity) {}

    open fun onResume(activity: BaseActivity) {}

    open fun onPause(activity: BaseActivity) {}

    open fun onStop(activity: BaseActivity) {}

    open fun onDestroy(activity: BaseActivity) {}

}