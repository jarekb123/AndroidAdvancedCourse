package pl.butajlo.androidadvanced.di;

import com.bluelinelabs.conductor.Controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import dagger.MapKey;
import pl.butajlo.androidadvanced.base.BaseController;

@MapKey
@Target(ElementType.METHOD)
public @interface ControllerKey {

    Class<? extends Controller> value();
}
