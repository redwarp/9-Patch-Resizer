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
package net.redwarp.tool.resizer.table;

import java.io.File;

public class Operation {
    private volatile OperationStatus status;
    private File file;
    private String message = null;

    public Operation(File f) {
        this.file = f;
        this.status = OperationStatus.PENDING;
    }

    public OperationStatus getStatus() {
        return this.status;
    }

    public File getFile() {
        return this.file;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
        this.message = null;
    }

    public void setStatus(OperationStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
