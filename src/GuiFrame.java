import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class GuiFrame extends JFrame {
  private static final long serialVersionUID = 1L;
  private JLabel background;
  private JPanel funcArea;
  private JButton func1;
  private JButton func2;
  private JButton func3;
  private JButton func4;
  private JButton func5;
  private JButton func6;

  public GuiFrame() {
    //Container content = getContentPane();
    setTitle("GuiLab1");
    setBounds(700, 100, 400, 800);
    //this.setResizable(false);
    setLayout(new BorderLayout());
    //设置图标
    Image img1 = new ImageIcon("adjGraph:\\BaiduYunDownload\\图片\\th.jpg").getImage();
    setIconImage(img1);
    //设置背景
    background = new JLabel();

    Image imgBackground = new ImageIcon("adjGraph:\\BaiduYunDownload\\图片\\eva.jpg").getImage();
    background.setIcon(new ImageIcon(imgBackground));
    //设置按钮
    funcArea = new JPanel();
    funcArea.setLayout(new GridLayout(6, 1));
    func1 = new JButton("读入文件生成有向图");
    func1.setBounds(0, 100, 200, 100);
    func1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JFileChooser dlg = new JFileChooser();
        int result = dlg.showOpenDialog(null);  // "打开文件"对话框
        if (result == JFileChooser.APPROVE_OPTION) {
          //File file = dlg.getSelectedFile();
          GuiLab1.GG.fileUrl = dlg.getSelectedFile().getAbsolutePath();
          try {
            GuiLab1.GG.read();
            GuiLab1.GG.showDirectedGraph(GuiLab1.GG.adjGraph, 0, "");
            //GuiLab1.GG.showDirectedGraph(adjGraph,"");
          } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    });

    func2 = new JButton("展示有向图");
    func2.setBounds(0, 200, 200, 100);
    func2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JFrame frame = new JFrame("展示有向图");
        ImageViewer imageViewer = null;
        try {
          imageViewer = new ImageViewer(System.getProperty("user.home")
                                        + "\\AppData\\Local\\Temp" + "\\out.gif");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        frame.add(imageViewer);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setVisible(true);
      }
    });
    func3 = new JButton("查询桥接词");
    func3.setBounds(0, 300, 200, 100);
    func3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        BridgeWordsFrame frame = new BridgeWordsFrame();
        frame.setTitle("查询桥接词");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
      }
    });
    func4 = new JButton("生成新文本");
    func4.setBounds(0, 400, 200, 100);
    func4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        NewTextFrame frame = new NewTextFrame();
        frame.setTitle("生成新文本");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
      }
    });
    func5 = new JButton("最短路径");
    func5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        ShortestFrame frame = new ShortestFrame();
        frame.setTitle("最短路径");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
      }
    });
    func5.setBounds(0, 500, 200, 100);
    func6 = new JButton("随机游走");
    func6.setBounds(0, 600, 200, 100);
    func6.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        RandomFrame frame = new RandomFrame();
        frame.setTitle("随机游走");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
      }
    });
    //组件填进容器
    funcArea.add(func1);
    funcArea.add(func2);
    funcArea.add(func3);
    funcArea.add(func4);
    funcArea.add(func5);
    funcArea.add(func6);
    add(background);
    add(funcArea, BorderLayout.CENTER);
  }
}
