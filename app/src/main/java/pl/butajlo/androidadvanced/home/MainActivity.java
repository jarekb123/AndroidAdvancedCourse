package pl.butajlo.androidadvanced.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bluelinelabs.conductor.Controller;

import org.jetbrains.annotations.NotNull;

import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.base.BaseActivity;
import pl.butajlo.androidadvanced.trending.TrendingReposController;

public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @NotNull
    @Override
    public Controller initialScreen() {
        return new TrendingReposController();
    }
}
