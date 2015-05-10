package com.github.lquiroli.menupager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to decorate a collection inside your custom class. This will be used by {@link com.github.lquiroli.menupager.widget.SimpleMenuFragmentAdapter}
 * during creation of the menu pages
 * <p>Created by lorenzo.quiroli</p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Collection {
}
