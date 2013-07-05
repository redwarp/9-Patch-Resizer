/*
 * Copyright 2012 redwarp
 *
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
 */
package net.redwarp.tool.resizer.worker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ScreenDensity {
    private float scale;
    private String name;


    private boolean active;

    private static List<ScreenDensity> list = null;
    private static ScreenDensity defaultInputDensity = null;

    static {
        try {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            InputStream preferenceStream;
            try {
                preferenceStream = new FileInputStream(new File("./densities.json"));
            } catch (Exception e) {
                preferenceStream = ScreenDensity.class.getClassLoader().getResourceAsStream("misc/densities.json");
            }
            JsonObject densitiesObject = parser.parse(new InputStreamReader(preferenceStream)).getAsJsonObject();
            JsonArray densitiesArray = densitiesObject.get("densities").getAsJsonArray();

            Type listType = new TypeToken<List<ScreenDensity>>() {
            }.getType();
            list = gson.fromJson(densitiesArray, listType);
            String defaultDensityName = densitiesObject.get("source").getAsString();
            for (ScreenDensity density : list) {
                if (density.getName().equals(defaultDensityName)) {
                    defaultInputDensity = density;
                    break;
                }
            }
            if (defaultInputDensity == null) {
                defaultInputDensity = list.get(0);
            }
        } catch (Exception e) {
            list = new ArrayList<ScreenDensity>();
            list.add(new ScreenDensity("xhdpi", 2.0f, true));
            defaultInputDensity = list.get(0);
        }
    }

    private ScreenDensity(String name, float density, boolean active) {
        this.scale = density;
        this.name = name;
        this.active = active;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public float getScale() {
        return this.scale;
    }

    public static List<ScreenDensity> getSupportedScreenDensity() {
        return list;
    }

    public static ScreenDensity getDefaultInputDensity() {
        return defaultInputDensity;
    }

    public static void setDefaultInputDensity(ScreenDensity density) {
        defaultInputDensity = density;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScreenDensity) {
            return this.name.equals(((ScreenDensity) obj).getName());
        }
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
