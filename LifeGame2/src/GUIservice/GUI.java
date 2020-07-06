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
  /** ����. */
  private static GUI frame;
  /** ϸ��. */
  private Map cell;
  /** ���Ϳ�. */
  private int maxLength, maxWidth;
  /**һ����ť��ʾһ��ϸ��.*/
  private JButton[][] nGrid;

  /**��ť�Ƿ�ѡ��.*/
  private boolean[][] isSelected;
  /**ȷ������ǰ�����������ʼ������������.*/
  private JButton OK, GridNowGeneration, RandomInit, clearGenenration;
  /**���ϸ������һ������ʼ���ܣ���ͣ���˳�.*/
  private JButton ClearCell, nextGeneration, Start, Stop, Exit;
  /**����ѡ��.*/
  private JComboBox lengthList, widthList;
  /**�߳��Ƿ�������.*/
  private boolean isRunning;
  /**�߳�.*/
  private Thread thread;
  /**ϸ���Ƿ�����.*/
  private boolean isdead;

  /**
   * ʵ�����ݷ�װ.
   * @return ����
   */
  public int getMaxLength() {
    return maxLength;
  }

  /**
   * ʵ�����ݷ�װ.
   * @return ���
   */
  public int getMaxWidth() {
    return maxWidth;
  }
  /**
   * ʵ�����ݷ�װ.
   * @param maxLength���
   */
  public void setMaxLength(int MaxLength) {
    this.maxLength = MaxLength;
  }

  /**
   * ʵ�����ݷ�װ.
   * @param maxWidth ���
   */
  public void setMaxWidth(int MaxWidth) {
    this.maxWidth = MaxWidth;
  }

  /**��ʼ������.*/
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
    backPanel = new JPanel(new BorderLayout());//��Ӳ��ֹ���
    centerPanel = new JPanel(new GridLayout(maxLength, maxWidth));
    bottomPanel = new JPanel();
    this.setContentPane(backPanel);//���óɵ�����
    backPanel.add(centerPanel, "Center"); //��ͼ��������
    backPanel.add(bottomPanel, "South");  //������ť���ڵײ�

    nGrid = new JButton[maxLength][maxWidth];
    isSelected = new boolean[maxLength][maxWidth];
    for (int i = 0; i < maxLength; i++) {
      for (int j = 0; j < maxWidth; j++) {
        nGrid[i][j] = new JButton("");
        nGrid[i][j].setBackground(Color.WHITE);
        centerPanel.add(nGrid[i][j]);
      }
    }

    jLength = new JLabel("���ȣ�");
    lengthList = new JComboBox(); 
    for (int i = 3; i <= 100; i++) {
      lengthList.addItem(String.valueOf(i)); //Ϊ���б����Ԫ��
    }
    lengthList.setSelectedIndex(maxLength - 3);

    jWidth = new JLabel("��ȣ�");
    widthList = new JComboBox();
    for (int i = 3; i <= 100; i++) {
      widthList.addItem(String.valueOf(i));
    }
    widthList.setSelectedIndex(maxWidth - 3);

    OK = new JButton("ȷ��");
    jNowGenenration = new JLabel("��ǰ����:");
    GridNowGeneration = new JButton("" + cell.getNowGenneration());
    GridNowGeneration.setEnabled(false);
    clearGenenration = new JButton("��������");
    RandomInit = new JButton("�����ʼ��");
    ClearCell = new JButton("ϸ������");
    Start = new JButton("��ʼ����");
    nextGeneration = new JButton("��һ��");
    Stop = new JButton("��ͣ");
    Exit = new JButton("�˳�");

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

    // ���ô���
    this.setSize(1000, 650);
    this.setResizable(true);
    this.setLocationRelativeTo(null); // �ô�������Ļ�о���
    this.setVisible(true); // ����������Ϊ�ɼ�

    // ע��������������رմ���
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
   * �½�����.
   * @param name  �������
   */
  public GUI(String name) {
    super(name);
    initGUI();
  }

  /**
   * ���ղ����¼�.
   * @param e �����¼�
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
              JOptionPane.showMessageDialog(null, "����ϸ���Ѿ�����");
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

  /**��һ��.*/
  public void makeNextGeneration() {
    cell.update();
    showCell();
    GridNowGeneration.setText("" + cell.getNowGenneration());
  }

  /**��ϸ�����ص�������.*/
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
