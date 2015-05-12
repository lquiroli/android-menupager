/*
   Copyright (C) 2015 Lorenzo Quiroli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
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

    public static ArrayList reflectCollection(Object obj) {

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
            Field[] totFields = obj.getClass().getDeclaredFields();
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
