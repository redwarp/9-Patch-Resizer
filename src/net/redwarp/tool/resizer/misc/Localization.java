/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright 2013 Redwarp
 */
package net.redwarp.tool.resizer.misc;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {
    private static ResourceBundle bundle = ResourceBundle.getBundle(
            "locale.Strings", Locale.getDefault(), new MyResourceControl());

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    private static class MyResourceControl extends ResourceBundle.Control {
        @Override
        public Locale getFallbackLocale(String baseName, Locale locale) {
            return null;
        }
    }
}
