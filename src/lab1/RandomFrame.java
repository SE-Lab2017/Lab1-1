package lab1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class RandomFrame extends JFrame {
  public static final int TEXTAREA_ROWS = 8;
  public static final int TEXTAREA_COLUMNS = 20;
  /**
   *
   */
  private static final long serialVersionUID = -9094412266398525746L;

  public RandomFrame() {
    final JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
    JScrollPane scrollPane = new JScrollPane(textArea);

    add(scrollPane, BorderLayout.CENTER);

    JPanel southPanel = new JPanel();

    JButton rwButton = new JButton("随机游走");
    southPanel.add(rwButton);
    rwButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        try {
          textArea.append(GuiLab1.graphIo.adjGraph.randomWalk() + System.lineSeparator());
        } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });

    add(southPanel, BorderLayout.SOUTH);
    //pack();
  }
}
