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

import net.redwarp.tool.resizer.misc.Localization;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResultModel extends AbstractTableModel {
    private static final long serialVersionUID = -6799282358729483044L;
    private List<Operation> operationList;
    private String[] columns = new String[]{Localization.get("column_name"),
            Localization.get("column_status")};

    public ResultModel() {
        this.operationList = new ArrayList<Operation>();
    }

    @Override
    public synchronized int getRowCount() {
        return this.operationList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public synchronized Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return this.operationList.get(rowIndex).getFile().getName();
            case 1:
                return this.operationList.get(rowIndex);
            default:
                return "";
        }
    }

    public synchronized void addOperation(Operation operation) {
        this.operationList.add(operation);
        int rowIndex = this.operationList.size() - 1;
        this.fireTableRowsInserted(rowIndex, rowIndex);
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }

    public synchronized void notifyChange(Operation operation) {
        int row = this.operationList.indexOf(operation);
        if (row != -1) {
            this.fireTableCellUpdated(row, 1);
            this.fireTableCellUpdated(row, 2);
        }
    }

    public synchronized void clear() {
        Iterator<Operation> itor = this.operationList.iterator();
        while (itor.hasNext()) {
            Operation op = itor.next();
            OperationStatus status = op.getStatus();
            if (status == OperationStatus.FINISH
                    || status == OperationStatus.ERROR) {
                itor.remove();
            }
        }
        this.fireTableDataChanged();
    }

}
