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

class NewTextFrame extends JFrame {
  public static final int TEXTAREA_ROWS = 8;
  public static final int TEXTAREA_COLUMNS = 20;
  /**
   *
   */
  private static final long serialVersionUID = -9094412266398525746L;

  public NewTextFrame() {
    final JTextField textField1 = new JTextField();

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new GridLayout(0, 2));
    northPanel.add(new JLabel("输入文本: ", SwingConstants.RIGHT));
    northPanel.add(textField1);
    add(northPanel, BorderLayout.NORTH);

    final JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
    JScrollPane scrollPane = new JScrollPane(textArea);

    add(scrollPane, BorderLayout.CENTER);

    JPanel southPanel = new JPanel();

    JButton createButton = new JButton("生成文本");
    southPanel.add(createButton);
    createButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {

        textArea.append(GuiLab1.GG.adjGraph.generateNewText(textField1.getText())
                        + System.lineSeparator());
      }
    });

    add(southPanel, BorderLayout.SOUTH);
    //pack();
  }
}
