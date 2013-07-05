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

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

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

        if (value instanceof Operation) {
            Operation operation = (Operation) value;
            OperationStatus status = operation.getStatus();
            if (status == OperationStatus.FINISH) {
                label.setIcon(this.iconSuccess);
            } else if (status == OperationStatus.ERROR) {
                label.setIcon(this.iconError);
            }
            if (operation.getMessage() != null) {
                label.setText(operation.getMessage());
            } else {
                if (status == OperationStatus.PENDING) {
                    label.setText(Localization.get("status_pending"));
                } else if (status == OperationStatus.FINISH) {
                    label.setText(Localization.get("status_finished"));
                } else if (status == OperationStatus.ERROR) {
                    label.setText(Localization.get("status_error"));
                } else if (status == OperationStatus.IN_PROGRESS) {
                    label.setText(Localization.get("status_in_progress"));
                }
            }
        }

        return label;
    }

}
