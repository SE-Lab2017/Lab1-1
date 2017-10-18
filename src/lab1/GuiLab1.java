package lab1;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class GuiLab1 {
  public static final GraphIo graphIo = new GraphIo();

  public static void main(String[] args) {

    EventQueue.invokeLater(new Runnable() {
      public void run() {
        GuiFrame frame = new GuiFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
      }
    });
  }
}
