/*
 * Copryright (C) 2012 Redwarp
 * 
 * This file is part of 9Patch Resizer.
 * 9Patch Resizer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 9Patch Resizer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with 9Patch Resizer.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.redwarp.tool.resizer.table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.redwarp.tool.resizer.misc.Localization;

public class ResultModel extends AbstractTableModel {
	private static final long serialVersionUID = -6799282358729483044L;
	private List<Operation> operationList;
	private String[] columns = new String[] { Localization.get("column_name"),
			Localization.get("column_status") };

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
			return this.operationList.get(rowIndex).getStatus();
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
