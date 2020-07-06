package Cellservice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CellTest {
    Cell cell = new Cell();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSetStatus() {
		cell.setStatus(1);
		int nstatus=cell.getStatus();
		assertEquals(1, nstatus);
	}

	@Test
	public void testRandomStatus() {
		//fail("Not yet implemented");
		int nstatus=cell.randomStatus();
		assertEquals(nstatus, cell.getStatus());
	
	}

}
