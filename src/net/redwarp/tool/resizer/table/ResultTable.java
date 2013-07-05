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

import javax.swing.*;
import java.awt.*;

public class ResultTable extends JTable {
    private static final long serialVersionUID = -8240707430938246389L;
    private ResultModel model;

    public ResultTable() {
        this.model = new ResultModel();
        this.setModel(this.model);
        this.getColumnModel().getColumn(1)
                .setCellRenderer(new StatusCellRenderer());
        this.setIntercellSpacing(new Dimension(5, 0));
        this.setRowHeight(20);

        this.setRowSelectionAllowed(false);
        this.setFillsViewportHeight(true);
    }

    public void addOperation(Operation operation) {
        this.model.addOperation(operation);
    }

    public void notifyChange(Operation operation) {
        this.model.notifyChange(operation);
    }

    public void clear() {
        this.model.clear();
    }
}
