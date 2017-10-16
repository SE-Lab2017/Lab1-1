//lab3 1.4

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.event.*;

public class GUI_LAB1 
{   
    static GraphIO GG=new GraphIO();
    public static void main(String[] args)
    {
        
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {   
                GuiFrame frame = new GuiFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
    

class GuiFrame extends JFrame 
{
    private static final long serialVersionUID = 1L;
    private JLabel background;
    private JPanel func_area;
    private JButton func1;
    private JButton func2;
    private JButton func3;
    private JButton func4;
    private JButton func5;
    private JButton func6;
    
    public GuiFrame()
    {
        //Container content = getContentPane();
        setTitle("GUI_LAB1");
        setBounds(700,100,400,800);
        //this.setResizable(false);
        setLayout(new BorderLayout());
        //设置图标
        Image img1 = new ImageIcon("G:\\BaiduYunDownload\\图片\\th.jpg").getImage();
        setIconImage(img1);
        //设置背景
        background = new JLabel();
        
        Image img_background = new ImageIcon("G:\\BaiduYunDownload\\图片\\eva.jpg").getImage();
        background.setIcon(new ImageIcon(img_background));
        //设置按钮
        func_area = new JPanel();
        func_area.setLayout(new GridLayout(6,1));
        func1 = new JButton("读入文件生成有向图");
        func1.setBounds(0, 100, 200, 100);                                                                                               
        func1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                JFileChooser dlg = new JFileChooser();
                int result = dlg.showOpenDialog(null);  // "打开文件"对话框
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                    //File file = dlg.getSelectedFile();
                    GUI_LAB1.GG.fileUrl = dlg.getSelectedFile().getAbsolutePath();
                    try {
                        GUI_LAB1.GG.read();
                        GUI_LAB1.GG.showDirectedGraph(GUI_LAB1.GG.G,0,"");
                        //GUI_LAB1.GG.showDirectedGraph(G,"");
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        
        func2 = new JButton("展示有向图");
        func2.setBounds(0, 200, 200, 100);
        func2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                JFrame frame = new JFrame("展示有向图");
                ImageViewer imageViewer = null;
                try {
                    imageViewer = new ImageViewer("C:\\Users\\zhang\\Desktop\\out.gif");
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
        func3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                bridgeWordsFrame frame = new bridgeWordsFrame();
                frame.setTitle("查询桥接词");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setVisible(true);
            }
        });
        func4 = new JButton("生成新文本");
        func4.setBounds(0, 400, 200, 100);
        func4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                NewTextFrame frame = new NewTextFrame();
                frame.setTitle("生成新文本");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setVisible(true);
            }
        });
        func5 = new JButton("最短路径");
        func5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
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
        func6.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                RandomFrame frame = new RandomFrame();
                frame.setTitle("随机游走");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setVisible(true);
            }
        });
        //组件填进容器
        func_area.add(func1);
        func_area.add(func2);
        func_area.add(func3);
        func_area.add(func4);
        func_area.add(func5);
        func_area.add(func6);       
        add(background);
        add(func_area,BorderLayout.CENTER); 
    } 
}

class ImageViewer extends JComponent {

    /**
     * 
     */
    private static final long serialVersionUID = 7329313876812878327L;
    final JSlider slider;
    final ImageComponent image;
    final JScrollPane scrollPane;

    public ImageViewer(String path) throws IOException {
        slider = new JSlider(0, 1000, 500);
        image = new ImageComponent(path);
        scrollPane = new JScrollPane(image);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                image.setZoom(2. * slider.getValue() / slider.getMaximum());
            }
        });

        this.setLayout(new BorderLayout());
        this.add(slider, BorderLayout.NORTH);
        this.add(scrollPane);
    }
}

class ImageComponent extends JComponent {

    /**
     * 
     */
    private static final long serialVersionUID = -5912541581518794288L;
    final BufferedImage img;

    public ImageComponent(String path) throws IOException {
        img = ImageIO.read(new File(path));
        setZoom(1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim = getPreferredSize();
        g.drawImage(img, 0, 0, dim.width, dim.height, this);
    }

    public void setZoom(double zoom) {
        int w = (int) (zoom * img.getWidth());
        int h = (int) (zoom * img.getHeight());
        setPreferredSize(new Dimension(w, h));
        revalidate();
        repaint();
    }
}

class bridgeWordsFrame extends JFrame
{
   /**
     * 
     */
    private static final long serialVersionUID = -6997430244984473897L;
    public static final int TEXTAREA_ROWS = 8;
    public static final int TEXTAREA_COLUMNS = 20;

   public bridgeWordsFrame()
   {
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
      serchtButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
                String tmp = GUI_LAB1.GG.G.queryBridgeWords(textField1.getText(), textField2.getText());
                textArea.append("The bridge words from "+textField1.getText()+" to "+
                        textField2.getText()+" is:"+tmp+ "\n");
                GUI_LAB1.GG.showDirectedGraph(GUI_LAB1.GG.G,1,textField1.getText()+" "+tmp+" "+textField2.getText());
            }
         });

      add(southPanel, BorderLayout.SOUTH);
      //pack();
   }
}

class NewTextFrame extends JFrame
{
   /**
     * 
     */
    private static final long serialVersionUID = -9094412266398525746L;
    public static final int TEXTAREA_ROWS = 8;
   public static final int TEXTAREA_COLUMNS = 20;

   public NewTextFrame()
   {
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
      createButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {

                textArea.append(GUI_LAB1.GG.G.generateNewText(textField1.getText())+ "\n");
            }
         });

      add(southPanel, BorderLayout.SOUTH);
      //pack();
   }
}

class ShortestFrame extends JFrame
{
   /**
     * 
     */
    private static final long serialVersionUID = -6997430244984473897L;
    public static final int TEXTAREA_ROWS = 8;
    public static final int TEXTAREA_COLUMNS = 20;

   public ShortestFrame()
   {
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

      JButton Button = new JButton("生成最短路径");
      southPanel.add(Button);
      Button.addActionListener(new ActionListener()
    {
            public void actionPerformed(ActionEvent event)
            {
                /*if(textField1.getText()==null && textField2.getText() == null)
                {
                    textArea.append("No Input");
                }
                else if(textField1.getText()==null && textField2.getText() != null)
                {
                    textArea.append(GUI_LAB1.GG.G.calcShortestPath(textField2.getText())+ "\n");
                }*/
                if(textField1.getText()!=null && textField2.getText().equals(""))
                {
                    String[] paths = GUI_LAB1.GG.G.calcShortestPath(textField1.getText());
                    for (int i = 1; i <= GUI_LAB1.GG.G.getNumOfVertex(); i++) {
                        if (i != GUI_LAB1.GG.G.getIndexOfVex(textField1.getText())) {
                            textArea.append(paths[i] + " ");
                            textArea.append(String.valueOf(GUI_LAB1.GG.G.getDistanceOfPath(GUI_LAB1.GG.G.getVexData(i))));
                            textArea.append("\n");
                        }
                    }
                }
                else
                {
                    GUI_LAB1.GG.showDirectedGraph(GUI_LAB1.GG.G,2, GUI_LAB1.GG.G.calcShortestPath(textField1.getText(), textField2.getText()));
                    textArea.append(GUI_LAB1.GG.G.calcShortestPath(textField1.getText(), textField2.getText())+ "\n");
                }
            }
        });
                
      add(southPanel, BorderLayout.SOUTH);
   }
}

class RandomFrame extends JFrame
{
   /**
     * 
     */
    private static final long serialVersionUID = -9094412266398525746L;
    public static final int TEXTAREA_ROWS = 8;
   public static final int TEXTAREA_COLUMNS = 20;

   public RandomFrame()
   {
      /*final JTextField textField1 = new JTextField();

      JPanel northPanel = new JPanel();
      northPanel.setLayout(new GridLayout(0, 2));
      northPanel.add(new JLabel("起点: ", SwingConstants.RIGHT));
      northPanel.add(textField1);
      add(northPanel, BorderLayout.NORTH);*/

      final JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
      JScrollPane scrollPane = new JScrollPane(textArea);

      add(scrollPane, BorderLayout.CENTER);

      JPanel southPanel = new JPanel();

      JButton rButton = new JButton("随机游走");
      southPanel.add(rButton);
      rButton.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               try {
                textArea.append(GUI_LAB1.GG.G.randomWalk()+ "\n");
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

