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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import net.redwarp.tool.resizer.worker.ScreenDensity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Settings {

  private static final String KEY_DENSITIES = "densities";
  private static final String KEY_SOURCE = "source";
  private static final String KEY_KEEP_SAME_DENSITY_FILE = "keep_same_density_file";
  private static final String KEY_VERSION_CODE = "version_code";
  public static final String DENSITIES_PATHNAME = "./densities.json";

  private List<ScreenDensity> list = null;
  private ScreenDensity defaultInputDensity = null;
  private boolean keepSameDensityFile = false;
  private int versionCode;

  public void load(String path) {
    try {
      Gson gson = new Gson();
      JsonParser parser = new JsonParser();
      InputStream preferenceStream;
      try {
        preferenceStream = new FileInputStream(new File(path));
      } catch (Exception e) {
        preferenceStream =
            this.getClass().getClassLoader().getResourceAsStream("misc/densities.json");
      }
      JsonObject
          densitiesObject =
          parser.parse(new InputStreamReader(preferenceStream)).getAsJsonObject();
      JsonArray densitiesArray = densitiesObject.get(KEY_DENSITIES).getAsJsonArray();

      Type listType = new TypeToken<List<ScreenDensity>>() {
      }.getType();
      list = gson.fromJson(densitiesArray, listType);
      String defaultDensityName = densitiesObject.get(KEY_SOURCE).getAsString();
      for (ScreenDensity density : list) {
        if (density.getName().equals(defaultDensityName)) {
          defaultInputDensity = density;
          break;
        }
      }
      if (defaultInputDensity == null) {
        defaultInputDensity = list.get(0);
      }

      JsonElement keepSameDensityElement = densitiesObject.get(
          KEY_KEEP_SAME_DENSITY_FILE);
      if (keepSameDensityElement != null) {
        keepSameDensityFile = keepSameDensityElement.getAsBoolean();
      } else {
        keepSameDensityFile = false;
      }
    } catch (Exception e) {
      list = new ArrayList<ScreenDensity>();
      list.add(new ScreenDensity("xhdpi", 2.0f, true));
      defaultInputDensity = list.get(0);
      keepSameDensityFile = false;
//      versionCode = Configuration.getVersionCode();
    }
  }

  public void save(Runnable postAction) {
//    saveButton.setEnabled(false);
    JsonObject rootObject = new JsonObject();
    // Save source
    rootObject.addProperty(KEY_SOURCE, defaultInputDensity.getName());

    // Save densities
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    Type listOfDensityType = new TypeToken<List<ScreenDensity>>() {
    }.getType();
    JsonElement densities = gson.toJsonTree(list, listOfDensityType);
    rootObject.add(KEY_DENSITIES, densities);
    rootObject.addProperty(KEY_KEEP_SAME_DENSITY_FILE, keepSameDensityFile);
    rootObject.addProperty("versionCode", Configuration.getVersionCode());

    SaveWorker worker = new SaveWorker(postAction, gson.toJson(rootObject));
    worker.execute();
  }

  public List<ScreenDensity> getSupportedScreenDensity() {
    return list;
  }

  public boolean shouldKeepSameDensityFile() {
    return keepSameDensityFile;
  }

  public ScreenDensity getDefaultInputDensity() {
    return defaultInputDensity;
  }

  public void setDefaultInputDensity(ScreenDensity defaultInputDensity) {
    this.defaultInputDensity = defaultInputDensity;
  }

  public void setShouldKeepSameDensityFile(boolean shouldKeepSameDensityFile) {
    this.keepSameDensityFile = shouldKeepSameDensityFile;
  }

  public static class SaveWorker extends SwingWorker<Void, Void> {

    private final String mSavePayload;
    private final Runnable mPostAction;

    public SaveWorker(Runnable postAction, String savePayload) {
      mPostAction = postAction;
      mSavePayload = savePayload;
    }


    @Override
    protected Void doInBackground() throws Exception {
      FileOutputStream fos = null;
      try {
        fos = new FileOutputStream(DENSITIES_PATHNAME);
        PrintWriter writer = new PrintWriter(fos);
        writer.write(mSavePayload);

        writer.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.out.println("Couldn't save");
      }

      return null;
    }

    @Override
    protected void done() {
      if (mPostAction != null) {
        mPostAction.run();
      }
    }
  }
}
