package com.github.lquiroli.menupager.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to decorate a String inside your custom class. This will be used by {@link com.github.lquiroli.menupager.widget.SimpleMenuFragmentAdapter}
 * during creation of the menu pages and by {@link com.github.lquiroli.menupager.widget.SimpleMenuRecyclerAdapter} during creation of menu items
 * <p>Created by lorenzo.quiroli</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Label {

}
