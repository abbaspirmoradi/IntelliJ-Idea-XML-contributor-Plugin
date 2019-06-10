package ir.dotin.ui;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class XmlContributorGUI {

  private JPanel rootPanel;
  private JTextField xmlAttributesTextField;

  public XmlContributorGUI() {
    setupUI();
  }

  public JPanel getRootPanel() {
    return rootPanel;
  }

  public String getTextFieldValue(){
    if (xmlAttributesTextField != null&& !xmlAttributesTextField.getText().trim().isEmpty()) {
      return xmlAttributesTextField.getText();
    }
    return null;
  }

  private void setupUI() {
    rootPanel = new JPanel();
    rootPanel.setLayout(new GridLayoutManager(3, 2, JBUI.emptyInsets(), -1, -1));
    rootPanel.setRequestFocusEnabled(true);
    final JLabel xmlAttributesLabel = new JLabel();
    xmlAttributesLabel.setText("XML Attributes: ");

    rootPanel.add(xmlAttributesLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(80, 16), null, 0, false));

    xmlAttributesTextField = new JTextField();
    xmlAttributesTextField.setToolTipText("<html><b><font color=red> Please enter your desire xml attribute </font></b></html>");
    xmlAttributesTextField.requestFocusInWindow();
    xmlAttributesTextField.setAutoscrolls(true);
    xmlAttributesTextField.setEditable(true);
    xmlAttributesTextField.setEnabled(true);
    xmlAttributesTextField.setHorizontalAlignment(10);
    rootPanel.add(xmlAttributesTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    rootPanel.add(xmlAttributesTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final JLabel noteLabel = new JLabel();
    noteLabel.setText("Please insert your comma separated XML tags here like this: operationId,returnObject");
    noteLabel.setVerticalAlignment(SwingConstants.CENTER);
    rootPanel.add(noteLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final Spacer spacer = new Spacer();
    rootPanel.add(spacer, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    xmlAttributesLabel.setLabelFor(xmlAttributesTextField);
  }

}
