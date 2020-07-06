package Cellservice;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MapTest {
  Map map;

  @Before
  public void setUp() throws Exception {
    map = new Map(10, 10);
  }

  @Test
  public void testMap() {
    map.setMaxlength(10);
    map.setMaxwidth(10);
    map.setNowGeneration(1);
    assertEquals(10, map.getMaxlength());
    assertEquals(10, map.getMaxwidth());
    assertEquals(1, map.getNowGenneration());
    int[][] nGrid = map.getGird();
    for (int i = 0; i < map.getGird().length; i++) {
      for (int j = 0; j < map.getGird()[i].length; j++) {
        assertEquals(nGrid[i][j], map.getGird()[i][j]);
      }
    }
  }

  @Test
  public void testRandomCell() {
    // fail("Not yet implemented");
    Cell cell = new Cell();
    cell.randomStatus();
    for (int i = 1; i <= map.getMaxlength(); i++) {
      for (int j = 1; j <= map.getMaxwidth(); j++) {
        map.getGird()[i][j] = cell.randomStatus();
        assertEquals(cell.getStatus(), map.getGird()[i][j]);
      }
    }
    // cell.randomStatus();
  }

  @Test
  public void testDeleteAllCell() {
    // fail("Not yet implemented");
    map.deleteAllCell();
    for (int i = 1; i <= map.getMaxlength(); i++) {
      for (int j = 1; j <= map.getMaxwidth(); j++) {
        assertEquals(0, map.getGird()[i][j]);
      }
    }
  }

  @Test
  public void testUpdate() {
    // fail("Not yet implemented");
    map = new Map(2, 2);
    int[][] grid = { { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 1 }, { 0, 0, 1, 0 } };
    // int [][]newGrid={{0,0,0,0},{0,0,1,0},{0,1,0,1},{0,0,1,0}};
    map.setGird(grid);
    map.update();
    int[][] newGrid = map.getGird();
    assertEquals(1, map.getNowGenneration());
    assertEquals(0, newGrid[1][1]);
    assertEquals(1, newGrid[1][2]);
    assertEquals(1, newGrid[2][1]);
    assertEquals(0, newGrid[2][2]);
  }

  @Test
  public void testGetCellNeighborCount() {
    int[][] grid = { { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 1 }, { 0, 0, 1, 0 } };
    map.setGird(grid);
    assertEquals(3, map.getCellNeighborCount(2, 1));
    assertEquals(4, map.getCellNeighborCount(2, 2));
  }
}
