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

import java.awt.Dimension;

import javax.swing.JTable;

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
