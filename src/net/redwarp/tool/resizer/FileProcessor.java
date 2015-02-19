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
 * Copyright 2014 Jean-Baptiste Lab
 */
package net.redwarp.tool.resizer;

import net.redwarp.tool.resizer.misc.Configuration;
import net.redwarp.tool.resizer.misc.NameValidator;
import net.redwarp.tool.resizer.table.Operation;
import net.redwarp.tool.resizer.table.OperationStatus;
import net.redwarp.tool.resizer.worker.ImageScaler;

import java.io.File;

public class FileProcessor {

  public interface FileProcessorStatusListener {

    void onSuccess();

    void onFailure(String msg);
  }

  private ImageScaler scaler;
  FileProcessorStatusListener listener;
  String fileName;

  public FileProcessor(String name, FileProcessorStatusListener l) {
    fileName = name;
    listener = l;
    if (NameValidator.isFilenameValid(fileName)) {
      Operation operation = new Operation(new File(name));

      scaler = new ImageScaler(operation,
                               Configuration.getSettings().getDefaultInputDensity()) {
        @Override
        protected void process(java.util.List<Operation> chunks) {
          for (Operation operation : chunks) {
            OperationStatus status = operation.getStatus();
            if (status == OperationStatus.FINISH) {
              if (listener != null) {
                listener.onSuccess();
              }
            } else if (status == OperationStatus.ERROR) {
              if (listener != null) {
                listener.onFailure(operation.getMessage());
              }
            }
          }
        }
      };
    }
  }

  public void process() {
    if (scaler != null) {
      scaler.post();
    } else {
      if (listener != null) {
        listener.onFailure("processor for argument:" + fileName
                           + " is null");
      }
    }
  }

}
