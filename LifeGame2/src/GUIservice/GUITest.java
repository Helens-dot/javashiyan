package GUIservice;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import Cellservice.Map;
import junit.framework.TestCase;
public class GUITest extends TestCase {
	GUI gui=new GUI("…˙√¸”Œœ∑≤‚ ‘");

	@Before
	public void setUp() throws Exception {
		gui=new GUI("…˙√¸”Œœ∑≤‚ ‘");
	}

	@Test
	public void testInitGUI() {
		//fail("Not yet implemented");
		gui.setMaxLength(35);
		gui.setMaxWidth(20);
		gui.initGUI();
		assertEquals(35, gui.getMaxLength());
		assertEquals(20, gui.getMaxWidth());
		int maxLength=gui.getMaxLength();
		int maxWidth=gui.getMaxWidth();
		JPanel backPanel,centerPanel,bottomPanel;
		backPanel=new JPanel(new BorderLayout());
		centerPanel=new JPanel(new GridLayout(maxLength,maxWidth));
		bottomPanel=new JPanel();
		backPanel.add(centerPanel,"Center");
		backPanel.add(bottomPanel,"South");
	}

	@Test
	public void testMakeNextGeneration() {
		//fail("Not yet implemented");
		int maxLength=gui.getMaxLength();
		int maxWidth=gui.getMaxWidth();
		Map cell=new Map(maxLength, maxWidth);
		cell.randomCell();
		//gui.actionPerformed();
		gui.makeNextGeneration();
		assertEquals(0,cell.getNowGenneration());
	}

	@Test
	public void testShowCell() {
		//fail("Not yet implemented");
	    gui.showCell();
	}

}
