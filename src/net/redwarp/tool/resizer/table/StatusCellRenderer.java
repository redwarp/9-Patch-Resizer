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

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.redwarp.tool.resizer.misc.Localization;

public class StatusCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 518341333665088552L;
	private ImageIcon iconSuccess = new ImageIcon(
			StatusCellRenderer.class.getResource("/img/valid.png"));
	private ImageIcon iconError = new ImageIcon(
			StatusCellRenderer.class.getResource("/img/error.png"));

	public StatusCellRenderer() {
		this.setHorizontalAlignment(LEADING);
		this.setVerticalAlignment(CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		JLabel label = (JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		label.setIcon(null);
		label.setText(null);

		if (value instanceof OperationStatus) {
			OperationStatus status = (OperationStatus) value;
			if (status == OperationStatus.PENDING) {
				label.setText(Localization.get("status_pending"));
			} else if (status == OperationStatus.FINISH) {
				label.setIcon(this.iconSuccess);
				label.setText(Localization.get("status_finished"));
			} else if (status == OperationStatus.ERROR) {
				label.setIcon(this.iconError);
				label.setText(Localization.get("status_error"));
			} else if (status == OperationStatus.IN_PROGRESS) {
				label.setText(Localization.get("status_in_progress"));
			}
		}

		return label;
	}

}
