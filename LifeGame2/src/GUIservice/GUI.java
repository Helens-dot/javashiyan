package GUIservice;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Cellservice.Map;


public class GUI extends JFrame implements ActionListener {
  /** 界面. */
  private static GUI frame;
  /** 细胞. */
  private Map cell;
  /** 长和宽. */
  private int maxLength, maxWidth;
  /**一个按钮表示一个细胞.*/
  private JButton[][] nGrid;

  /**按钮是否被选中.*/
  private boolean[][] isSelected;
  /**确定，当前代数，随机初始化，代数清零.*/
  private JButton OK, GridNowGeneration, RandomInit, clearGenenration;
  /**清除细胞，下一代，开始繁衍，暂停，退出.*/
  private JButton ClearCell, nextGeneration, Start, Stop, Exit;
  /**长宽选择.*/
  private JComboBox lengthList, widthList;
  /**线程是否在运行.*/
  private boolean isRunning;
  /**线程.*/
  private Thread thread;
  /**细胞是否死亡.*/
  private boolean isdead;

  /**
   * 实现数据封装.
   * @return 长度
   */
  public int getMaxLength() {
    return maxLength;
  }

  /**
   * 实现数据封装.
   * @return 宽度
   */
  public int getMaxWidth() {
    return maxWidth;
  }
  /**
   * 实现数据封装.
   * @param maxLength宽度
   */
  public void setMaxLength(int MaxLength) {
    this.maxLength = MaxLength;
  }

  /**
   * 实现数据封装.
   * @param maxWidth 宽度
   */
  public void setMaxWidth(int MaxWidth) {
    this.maxWidth = MaxWidth;
  }

  /**初始化界面.*/
  public void initGUI() {
    final int wid = 20;
    final int len = 35;
    if (maxLength == 0) {
      maxLength = len;
    }
    if (maxWidth == 0) {
      maxWidth = wid;
    }

    cell = new Map(maxLength, maxWidth);

    JPanel backPanel, centerPanel, bottomPanel;
    JLabel jWidth, jLength, jNowGenenration;
    backPanel = new JPanel(new BorderLayout());//添加布局管理
    centerPanel = new JPanel(new GridLayout(maxLength, maxWidth));
    bottomPanel = new JPanel();
    this.setContentPane(backPanel);//设置成底容器
    backPanel.add(centerPanel, "Center"); //地图放在中央
    backPanel.add(bottomPanel, "South");  //操作按钮放在底部

    nGrid = new JButton[maxLength][maxWidth];
    isSelected = new boolean[maxLength][maxWidth];
    for (int i = 0; i < maxLength; i++) {
      for (int j = 0; j < maxWidth; j++) {
        nGrid[i][j] = new JButton("");
        nGrid[i][j].setBackground(Color.WHITE);
        centerPanel.add(nGrid[i][j]);
      }
    }

    jLength = new JLabel("长度：");
    lengthList = new JComboBox(); 
    for (int i = 3; i <= 100; i++) {
      lengthList.addItem(String.valueOf(i)); //为项列表添加元素
    }
    lengthList.setSelectedIndex(maxLength - 3);

    jWidth = new JLabel("宽度：");
    widthList = new JComboBox();
    for (int i = 3; i <= 100; i++) {
      widthList.addItem(String.valueOf(i));
    }
    widthList.setSelectedIndex(maxWidth - 3);

    OK = new JButton("确定");
    jNowGenenration = new JLabel("当前代数:");
    GridNowGeneration = new JButton("" + cell.getNowGenneration());
    GridNowGeneration.setEnabled(false);
    clearGenenration = new JButton("代数清零");
    RandomInit = new JButton("随机初始化");
    ClearCell = new JButton("细胞清零");
    Start = new JButton("开始繁衍");
    nextGeneration = new JButton("下一代");
    Stop = new JButton("暂停");
    Exit = new JButton("退出");

    bottomPanel.add(jLength);
    bottomPanel.add(lengthList);
    bottomPanel.add(jWidth);
    bottomPanel.add(widthList);
    bottomPanel.add(OK);
    bottomPanel.add(jNowGenenration);
    bottomPanel.add(GridNowGeneration);
    bottomPanel.add(clearGenenration);
    bottomPanel.add(RandomInit);
    bottomPanel.add(ClearCell);
    bottomPanel.add(Start);
    bottomPanel.add(nextGeneration);
    bottomPanel.add(Stop);
    bottomPanel.add(Exit);

    // 设置窗口
    this.setSize(1000, 650);
    this.setResizable(true);
    this.setLocationRelativeTo(null); // 让窗口在屏幕中居中
    this.setVisible(true); // 将窗口设置为可见

    // 注册监听器，正常关闭窗口
    this.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        System.exit(0);
      }
    });
    OK.addActionListener(this);
    clearGenenration.addActionListener(this);
    RandomInit.addActionListener(this);
    ClearCell.addActionListener(this);
    nextGeneration.addActionListener(this);
    Start.addActionListener(this);
    Stop.addActionListener(this);
    Exit.addActionListener(this);
    for (int j = 0; j < maxLength; j++) {
      for (int i = 0; i < maxWidth; i++) {
        nGrid[j][i].addActionListener(this);
      }
    }
  }

  /**
   * 新建界面.
   * @param name  界面标题
   */
  public GUI(String name) {
    super(name);
    initGUI();
  }

  /**
   * 接收操作事件.
   * @param e 操作事件
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    final int minNum=3;
    final int sleepTime=500;
    if (e.getSource() == OK) {
      frame.setMaxLength(lengthList.getSelectedIndex() + 3);
      frame.setMaxWidth(widthList.getSelectedIndex() + 3);
      initGUI();
      cell = new Map(getMaxLength(), getMaxWidth());
    } else if (e.getSource() == clearGenenration) {
      cell.setNowGeneration(0);
      GridNowGeneration.setText("" + cell.getNowGenneration());
      isRunning = false;
      thread = null;
    } else if (e.getSource() == RandomInit) {
      cell.randomCell();
      showCell();
      isRunning = false;
      thread = null;
    } else if (e.getSource() == ClearCell) {
      cell.deleteAllCell();
      showCell();
      isRunning = false;
      thread = null;
    } else if (e.getSource() == Start) {
      isRunning = true;
      thread = new Thread(new Runnable() {

        @Override
        public void run() {
          // TODO Auto-generated method stub
          while (isRunning) {
            makeNextGeneration();
            try {
              thread.sleep(500);
            } catch (InterruptedException e1) {
              // TODO: handle exception
              e1.printStackTrace();
            }
            isdead = true;
            for (int i = 0; i <= maxLength; i++) {
              for (int j = 0; j <= maxWidth; j++) {
                if (cell.getGird()[i][j] != 0) {
                  isdead = false;
                  break;
                }
              }
              if (!isdead) {
                break;
              }
            }
            if (isdead) {
              JOptionPane.showMessageDialog(null, "所有细胞已经死亡");
              isRunning = false;
              thread = null;
            }
          }
        }
      });
      thread.start();
    } else if (e.getSource() == nextGeneration) {
      makeNextGeneration();
      isRunning = false;
      thread = null;
    } else if (e.getSource() == Stop) {
      isRunning = false;
      thread = null;
    } else if (e.getSource() == Exit) {
      setVisible(false);
      //frame.dispose();
      System.exit(0);
    } else {
      int[][] Grid = cell.getGird();
      for (int i = 0; i < maxLength; i++) {
        for (int j = 0; j < maxWidth; j++) {
          if (e.getSource() == nGrid[i][j]) {
            isSelected[i][j] = !isSelected[i][j];
            if (isSelected[i][j]) {
              nGrid[i][j].setBackground(Color.BLACK);
              Grid[i + 1][j + 1] = 1;
            } else {
              nGrid[i][j].setBackground(Color.WHITE);
              Grid[i + 1][j + 1] = 0;
            }
            break;
          }
        }
      }
      cell.setGird(Grid);
    }
  }

  /**下一代.*/
  public void makeNextGeneration() {
    cell.update();
    showCell();
    GridNowGeneration.setText("" + cell.getNowGenneration());
  }

  /**将细胞加载到界面上.*/
  public void showCell() {
    int[][] grid = cell.getGird();
    for (int i = 0; i < maxLength; i++) {
      for (int j = 0; j < maxWidth; j++) {
        if (grid[i + 1][j + 1] == 1) {
          nGrid[i][j].setBackground(Color.BLACK);
        } else {
          nGrid[i][j].setBackground(Color.WHITE);
        }
      }
    }
  }

}
