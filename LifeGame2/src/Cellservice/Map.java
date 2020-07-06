package Cellservice;

public class Map {
  private int maxlength;
  private int maxwidth;
  private int nowGeneration;
  private int[][] Gird;

  public Map(int maxlength, int maxwidth) {
    // TODO Auto-generated constructor stub
    this.maxlength = maxlength;
    this.maxwidth = maxwidth;
    nowGeneration = 0;
    Gird = new int[maxlength + 2][maxwidth + 2];
    for (int i = 0; i <= maxlength + 1; i++) {
      for (int j = 0; j <= maxwidth + 1; j++) {
        Gird[i][j] = (new Cell()).getStatus();
      }
    }
  }

  public void setMaxlength(int maxlength) {
    this.maxlength = maxlength;
  }

  public int getMaxlength() {
    return maxlength;
  }

  public void setMaxwidth(int maxwidth) {
    this.maxwidth = maxwidth;
  }

  public int getMaxwidth() {
    return maxwidth;
  }

  public void setGird(int[][] Gird) {
    this.Gird = Gird;
  }

  public int[][] getGird() {
    return Gird;
  }

  public void setNowGeneration(int nowGeneration) {
    this.nowGeneration = nowGeneration;
  }

  // �����ʼ��ϸ��
  public void randomCell() {
    for (int i = 1; i <= maxlength; i++) {
      for (int j = 1; j <= maxwidth; j++) {
        Gird[i][j] = (new Cell()).randomStatus();
      }
    }
  }

  public int getNowGenneration() {
    return nowGeneration;
  }

  // �������ϸ��
  public void deleteAllCell() {
    for (int i = 1; i <= maxlength; i++) {
      for (int j = 1; j <= maxwidth; j++) {
        Gird[i][j] = (new Cell()).getStatus();
      }
    }
  }

  // ����ϸ��״̬
  public void update() {
    int[][] newGird = new int[maxlength + 2][maxwidth + 2];
    for (int i = 1; i <= maxlength; i++) {
      for (int j = 1; j <= maxwidth; j++) {
        switch (getCellNeighborCount(i, j)) {
        case 2: {
          newGird[i][j] = Gird[i][j]; // ϸ��״̬����
          break;
        }
        case 3:
          newGird[i][j] = 1; // ϸ�����´��
          break;
        default:
          newGird[i][j] = 0; // ϸ������
        }
      }
    }

    for (int i = 1; i <= maxlength; i++) {
      for (int j = 1; j <= maxwidth; j++) {
        Gird[i][j] = newGird[i][j]; // ϸ��״̬����
      }
    }
    nowGeneration++;
  }

  // ��ȡϸ���ھӸ���
  public int getCellNeighborCount(int ni, int nj) {
    int count = 0;
    for (int i = ni - 1; i <= ni + 1; i++) {
      for (int j = nj - 1; j <= nj + 1; j++) {
        count += Gird[i][j];
      }
    }
    count -= Gird[ni][nj];
    return count;
  }
}
