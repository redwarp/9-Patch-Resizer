/*
 * Copyright 2012 redwarp
 *
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
 */
package net.redwarp.tool.resizer.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.redwarp.tool.resizer.misc.Localization;
import net.redwarp.tool.resizer.misc.Preferences;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 7783865044667012251L;

	public AboutDialog(JFrame parent) {
		this.setResizable(false);
		this.setSize(new Dimension(400, 250));
		this.getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblResizer = new JLabel(Localization.get("app_name") + " "
				+ Preferences.getVersion());
		lblResizer.setBorder(new EmptyBorder(10, 10, 10, 10));
		lblResizer.setVerticalTextPosition(SwingConstants.BOTTOM);
		lblResizer.setIconTextGap(10);
		lblResizer.setFont(lblResizer.getFont().deriveFont(
				lblResizer.getFont().getStyle() | Font.BOLD, 16f));
		lblResizer.setIcon(new ImageIcon(AboutDialog.class
				.getResource("/img/icon_64.png")));
		this.getContentPane().add(lblResizer, BorderLayout.NORTH);

		JTextArea txtrResizerIsA = new JTextArea();
		txtrResizerIsA.setEditable(false);
		txtrResizerIsA.setWrapStyleWord(true);
		txtrResizerIsA.setBorder(new EmptyBorder(0, 10, 10, 10));
		txtrResizerIsA.setFont(UIManager.getFont("Label.font"));
		txtrResizerIsA.setLineWrap(true);
		txtrResizerIsA.setText(Localization.get("about_text"));
		txtrResizerIsA.setBackground(new Color(0, 0, 0, 0));
		this.getContentPane().add(txtrResizerIsA, BorderLayout.CENTER);

		this.setLocationRelativeTo(parent);
	}

}
