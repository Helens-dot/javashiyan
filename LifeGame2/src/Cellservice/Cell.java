package Cellservice;


public class Cell {
	private int status;

	Cell()
	{
		this.status=0;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status=status;
	}
	
	public int randomStatus() {
		this.status=Math.random()>0.5?1:0;
		return status;
	}

}
