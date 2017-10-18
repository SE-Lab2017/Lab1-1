package lab1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class BridgeWordsFrame extends JFrame {
  public static final int TEXTAREA_ROWS = 8;
  public static final int TEXTAREA_COLUMNS = 20;
  /**
   *
   */
  private static final long serialVersionUID = -6997430244984473897L;

  public BridgeWordsFrame() {
    final JTextField textField1 = new JTextField();
    final JTextField textField2 = new JTextField();

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new GridLayout(0, 2));
    northPanel.add(new JLabel("单词1: ", SwingConstants.RIGHT));
    northPanel.add(textField1);
    northPanel.add(new JLabel("单词2: ", SwingConstants.RIGHT));
    northPanel.add(textField2);

    add(northPanel, BorderLayout.NORTH);

    final JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
    JScrollPane scrollPane = new JScrollPane(textArea);

    add(scrollPane, BorderLayout.CENTER);

    JPanel southPanel = new JPanel();

    JButton serchtButton = new JButton("开始查询");
    southPanel.add(serchtButton);
    serchtButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        String tmp = GuiLab1.graphIo.adjGraph
                      .queryBridgeWords(textField1.getText(), textField2.getText());
        textArea.append("The bridge words from " + textField1.getText() + " to "
                        + textField2.getText() + " is:" + tmp + System.lineSeparator());
        GuiLab1.graphIo.showDirectedGraph(GuiLab1.graphIo.adjGraph, 1,
                                      textField1.getText()
                                          + " " + tmp + " " + textField2.getText());
      }
    });

    add(southPanel, BorderLayout.SOUTH);
    //pack();
  }
}
