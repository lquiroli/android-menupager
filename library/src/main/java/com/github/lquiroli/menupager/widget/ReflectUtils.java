package com.github.lquiroli.menupager.widget;

import com.github.lquiroli.menupager.annotation.Collection;
import com.github.lquiroli.menupager.annotation.Label;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by lorenzo.quiroli on 05/05/2015.
 */
class ReflectUtils {

    public static ArrayList reflectList(Object obj) {

        ArrayList list = new ArrayList();

        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Collection) {
                        //Found data, check type
                        if (field.getType().newInstance() instanceof java.util.Collection) {
                            field.setAccessible(true);
                            list = (ArrayList) field.get(obj);
                            field.setAccessible(false);
                        }

                    }
                }
            }
        } catch (Exception ex) {

        }

        return list;

    }

    public static String reflectLabel(Object obj) {

        try {
            ArrayList<Field> totFields = new ArrayList<Field>();
            for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
                Field[] fields = c.getDeclaredFields();
                for (Field field : fields) {
                    totFields.add(field);
                }
            }

            for (Field field : totFields) {
                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof Label) {
                        //Found label
                        field.setAccessible(true);
                        String label = (String) field.get(obj);
                        field.setAccessible(false);
                        return label;
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
