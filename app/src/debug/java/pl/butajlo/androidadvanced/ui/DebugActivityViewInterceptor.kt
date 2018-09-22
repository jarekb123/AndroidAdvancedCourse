package pl.butajlo.androidadvanced.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.debug_drawer.view.*
import pl.butajlo.androidadvanced.R
import pl.butajlo.androidadvanced.settings.DebugPreferences
import javax.inject.Inject

class DebugActivityViewInterceptor @Inject constructor(private val debugPreferences: DebugPreferences)
    : ActivityViewInterceptor {

    val disposables = CompositeDisposable()

    lateinit var debugLayout: View

    override fun setContentView(activity: Activity, layoutRes: Int) {
        debugLayout = LayoutInflater.from(activity).inflate(R.layout.debug_drawer, null)
        initializePrefs()

        val activityLayout = LayoutInflater.from(activity).inflate(layoutRes, null)
        debugLayout.activity_layout_container.addView(activityLayout)
        activity.setContentView(debugLayout)
    }

    override fun clear() {
        disposables.clear()
    }

    private fun initializePrefs() {
        debugLayout.sw_mock_responses.isChecked = debugPreferences.useMockResponsesEnabled()

        disposables.addAll(
                RxCompoundButton.checkedChanges(debugLayout.sw_mock_responses)
                        .subscribe(debugPreferences::setUseMockResponses)
        )
    }
}