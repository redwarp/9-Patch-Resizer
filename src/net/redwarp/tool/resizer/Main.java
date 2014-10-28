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
package net.redwarp.tool.resizer;

import net.redwarp.tool.resizer.FileProcessor.FileProcessorStatusListener;
import net.redwarp.tool.resizer.misc.Localization;
import net.redwarp.tool.resizer.views.MainWindow;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    static class StatusListener implements FileProcessorStatusListener {
        int count = 0;
        int current = 0;
        int failureCount = 0;

        public StatusListener(int count) {
            this.count = count;
        }

        @Override
        public void onSuccess() {
            this.current++;
            if (this.current == this.count) {
                System.exit(failureCount);
            }
        }

        @Override
        public void onFailure(String msg) {
            System.err.print(msg + "\n");
            this.current++;
            this.failureCount++;
            if (this.current == this.count) {
                System.exit(failureCount);
            }
        }
    }

    public static void main(String[] args) {
        // Apple only stuff
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name",
                Localization.get("app_name"));
        if (args.length > 0) {
            StatusListener l = new StatusListener(args.length);
            ArrayList<FileProcessor> processors = new ArrayList<FileProcessor>();

            for (String s : args) {
                processors.add(new FileProcessor(s, l));
            }
            for (FileProcessor p : processors) {
                p.process();
            }
        } else {

            try {
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            new MainWindow().setVisible(true);
        }
    }
}
