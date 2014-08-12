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

package net.redwarp.tool.resizer.views;

import net.iharder.dnd.FileDrop;
import net.redwarp.tool.resizer.misc.Localization;
import net.redwarp.tool.resizer.table.Operation;
import net.redwarp.tool.resizer.table.ResultTable;
import net.redwarp.tool.resizer.worker.ImageScaler;
import net.redwarp.tool.resizer.worker.ScreenDensity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    private JPanel inputPanel;
    private JPanel outputPanel;

    private ImageIcon blueArrow, redArrow;
    private ImageIcon blueArrowSmall, redArrowSmall;
    private JButton xhdpiButton;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private ResultTable resultTable;
    private JLabel instructionLabel;
    private JMenuBar menuBar;
    private JMenu mnHelp;
    private JMenu mnEdit;
    private JMenuItem mntmClear;
    private JMenuItem mntmAbout;
    private final Action action = new SwingAction();

    public MainWindow() {
        this.setSize(new Dimension(500, 370));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle(Localization.get("app_name"));

        List<Image> icons = new ArrayList<Image>();
        icons.add(Toolkit.getDefaultToolkit().getImage(
                MainWindow.class.getResource("/img/icon_512.png")));
        icons.add(Toolkit.getDefaultToolkit().getImage(
                MainWindow.class.getResource("/img/icon_256.png")));
        icons.add(Toolkit.getDefaultToolkit().getImage(
                MainWindow.class.getResource("/img/icon_128.png")));
        icons.add(Toolkit.getDefaultToolkit().getImage(
                MainWindow.class.getResource("/img/icon_64.png")));
        icons.add(Toolkit.getDefaultToolkit().getImage(
                MainWindow.class.getResource("/img/icon_32.png")));
        icons.add(Toolkit.getDefaultToolkit().getImage(
                MainWindow.class.getResource("/img/icon_16.png")));
        this.setIconImages(icons);

        this.blueArrow = new ImageIcon(
                MainWindow.class.getResource("/img/blue_big.png"));
        this.redArrow = new ImageIcon(
                MainWindow.class.getResource("/img/red_big.png"));
        this.blueArrowSmall = new ImageIcon(
                MainWindow.class.getResource("/img/blue_small.png"));
        this.redArrowSmall = new ImageIcon(
                MainWindow.class.getResource("/img/red_small.png"));
        this.getContentPane().setLayout(new CardLayout(0, 0));

        this.inputPanel = new JPanel();
        this.inputPanel.setPreferredSize(new Dimension(10, 140));
        this.getContentPane().add(this.inputPanel, "input");

        this.xhdpiButton = new JButton(String.format(Locale.getDefault(), Localization.get("xhdpi"), ScreenDensity.getDefaultInputDensity().getName()));
        this.xhdpiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        this.inputPanel.setLayout(new BorderLayout(0, 0));
        this.xhdpiButton.setBorderPainted(false);
        this.xhdpiButton.setFocusPainted(false);
        this.xhdpiButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.xhdpiButton.setHorizontalTextPosition(SwingConstants.CENTER);
        this.xhdpiButton.setIcon(this.blueArrow);
        this.xhdpiButton.setSelectedIcon(this.redArrow);
        this.xhdpiButton.setBorder(null);
        this.xhdpiButton.setContentAreaFilled(false);
        this.inputPanel.add(this.xhdpiButton, BorderLayout.CENTER);

        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.PAGE_AXIS));
        optionPanel.add(Box.createVerticalGlue());

        JLabel inputLabel = new JLabel(Localization.get("input_density"));
        inputLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        optionPanel.add(inputLabel);
        JComboBox<ScreenDensity> inputDensityChoice = new JComboBox<ScreenDensity>(new Vector<ScreenDensity>(ScreenDensity.getSupportedScreenDensity()));
        inputDensityChoice.setSelectedItem(ScreenDensity.getDefaultInputDensity());
        inputDensityChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox box = (JComboBox) actionEvent.getSource();
                ScreenDensity selectedDensity = (ScreenDensity) box.getSelectedItem();
                ScreenDensity.setDefaultInputDensity(selectedDensity);
                xhdpiButton.setText(String.format(Locale.getDefault(), Localization.get("xhdpi"), selectedDensity.getName()));
            }
        });
        inputDensityChoice.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputDensityChoice.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        inputDensityChoice.setPreferredSize(new Dimension(1, 10));

        optionPanel.add(inputDensityChoice);
        optionPanel.add(Box.createVerticalGlue());

        JLabel outputLabel = new JLabel(Localization.get("output_density"));
        optionPanel.add(outputLabel);
        for (final ScreenDensity density : ScreenDensity.getSupportedScreenDensity()) {
            final JCheckBox box = new JCheckBox(density.getName());
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    density.setActive(box.isSelected());
                }
            });
            box.setSelected(density.isActive());
            box.setAlignmentX(Component.LEFT_ALIGNMENT);
            optionPanel.add(box);
        }
        optionPanel.add(Box.createVerticalGlue());

        final JButton saveButton = new JButton(Localization.get("save"));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScreenDensity.save(saveButton);
            }
        });
        saveButton.setToolTipText(Localization.get("save_tooltip"));
        optionPanel.add(saveButton);
        optionPanel.add(Box.createVerticalGlue());

        optionPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Options"), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        this.inputPanel.add(optionPanel, BorderLayout.LINE_START);

        this.outputPanel = new JPanel();
        this.getContentPane().add(this.outputPanel, "output");
        this.outputPanel.setLayout(new BorderLayout(0, 0));

        this.textArea = new JTextArea();
        this.textArea.setLineWrap(true);
        this.textArea.setEditable(false);

        this.resultTable = new ResultTable();
        this.scrollPane = new JScrollPane(this.resultTable);
        this.scrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.outputPanel.add(this.scrollPane, BorderLayout.CENTER);

        FileDrop.Listener<Container> dropListener = new FileDrop.Listener<Container>() {

            @Override
            public void filesDropped(Container source, File[] files) {
                for (File input : files) {
                    String name = input.getName().toLowerCase();
                    if (name.endsWith(".png") || name.endsWith(".jpg")) {
                        MainWindow.this.mntmClear.setEnabled(true);
                        CardLayout layout = (CardLayout) MainWindow.this
                                .getContentPane().getLayout();
                        layout.show(MainWindow.this.getContentPane(), "output");
                        Operation operation = new Operation(input);
                        MainWindow.this.resultTable.addOperation(operation);

                        ImageScaler scaler = new ImageScaler(operation,
                                ScreenDensity.getDefaultInputDensity()) {
                            @Override
                            protected void process(
                                    java.util.List<Operation> chunks) {
                                for (Operation operation : chunks) {
                                    MainWindow.this.resultTable
                                            .notifyChange(operation);
                                }
                            }
                        };
                        scaler.post();
                    }
                }
            }

            @Override
            public void dragEnter(Container source) {
                MainWindow.this.xhdpiButton.setSelected(true);
                MainWindow.this.instructionLabel
                        .setIcon(MainWindow.this.redArrowSmall);
            }

            @Override
            public void dragExit(Container source) {
                MainWindow.this.xhdpiButton.setSelected(false);
                MainWindow.this.instructionLabel
                        .setIcon(MainWindow.this.blueArrowSmall);
            }
        };
        new FileDrop<Container>(this.getContentPane(), null, dropListener);
        new FileDrop<Container>(this.outputPanel, null, dropListener);

        this.instructionLabel = new JLabel(Localization.get("xhdpi"));
        this.instructionLabel.setIcon(this.blueArrowSmall);
        this.instructionLabel.setBorder(new EmptyBorder(4, 4, 4, 4));
        this.outputPanel.add(this.instructionLabel, BorderLayout.SOUTH);

        new FileDrop<Container>(this.textArea, null, dropListener);

        this.setMenuBar();
    }

    private void setMenuBar() {
        this.menuBar = new JMenuBar();
        this.setJMenuBar(this.menuBar);

        this.mnEdit = new JMenu(Localization.get("menu_edit"));
        this.menuBar.add(this.mnEdit);

        this.mntmClear = new JMenuItem(Localization.get("menu_item_clear"));
        this.mntmClear.setAction(this.action);
        this.mntmClear.setEnabled(false);
        this.mnEdit.add(this.mntmClear);

        this.mnHelp = new JMenu(Localization.get("menu_help"));
        this.menuBar.add(this.mnHelp);

        this.mntmAbout = new JMenuItem();
        this.mntmAbout.setAction(new AboutAction());
        this.mnHelp.add(this.mntmAbout);
    }

    private class AboutAction extends AbstractAction {
        public AboutAction() {
            this.putValue(NAME, Localization.get("menu_item_about"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new AboutDialog(MainWindow.this).setVisible(true);
        }
    }

    private class SwingAction extends AbstractAction {
        public SwingAction() {
            this.putValue(NAME, Localization.get("menu_item_clear"));
            this.putValue(SHORT_DESCRIPTION,
                    Localization.get("menu_item_clear_desc"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MainWindow.this.resultTable.clear();
            if (MainWindow.this.resultTable.getModel().getRowCount() == 0) {
                MainWindow.this.mntmClear.setEnabled(false);

                CardLayout layout = (CardLayout) MainWindow.this
                        .getContentPane().getLayout();
                layout.show(MainWindow.this.getContentPane(), "input");
            }
        }
    }
}
