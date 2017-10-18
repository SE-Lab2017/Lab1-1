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

class ShortestFrame extends JFrame {
  public static final int TEXTAREA_ROWS = 8;
  public static final int TEXTAREA_COLUMNS = 20;
  /**
   *
   */
  private static final long serialVersionUID = -6997430244984473897L;

  public ShortestFrame() {
    final JTextField textField1 = new JTextField();
    final JTextField textField2 = new JTextField();

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new GridLayout(0, 2));
    northPanel.add(new JLabel("起点: ", SwingConstants.RIGHT));
    northPanel.add(textField1);
    northPanel.add(new JLabel("终点: ", SwingConstants.RIGHT));
    northPanel.add(textField2);

    add(northPanel, BorderLayout.NORTH);

    final JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
    JScrollPane scrollPane = new JScrollPane(textArea);

    add(scrollPane, BorderLayout.CENTER);

    JPanel southPanel = new JPanel();

    JButton spButton = new JButton("生成最短路径");
    southPanel.add(spButton);
    spButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        if (textField1.getText() != null && textField2.getText().equals("")) {
          String[] paths = GuiLab1.GG.adjGraph.calcShortestPath(textField1.getText());
          for (int i = 1; i <= GuiLab1.GG.adjGraph.getNumOfVertex(); i++) {
            if (i != GuiLab1.GG.adjGraph.getIndexOfVex(textField1.getText())) {
              textArea.append(paths[i] + " ");
              textArea.append(String.valueOf(GuiLab1.GG.adjGraph
                                              .getDistanceOfPath(GuiLab1.GG.adjGraph
                                                                  .getVexData(i))));
              textArea.append(System.lineSeparator());
            }
          }
        } else {
          GuiLab1.GG.showDirectedGraph(GuiLab1.GG.adjGraph, 2,
                                        GuiLab1.GG.adjGraph.calcShortestPath(textField1.getText(),
                                        textField2.getText()));
          textArea.append(GuiLab1.GG.adjGraph.calcShortestPath(textField1.getText(),
                          textField2.getText()) + System.lineSeparator());
        }
      }
    });

    add(southPanel, BorderLayout.SOUTH);
  }
}
