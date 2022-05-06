package audiopaket;

public class Nachricht
{
	private static final long serialVersionUID = 1L;
	//art der nachricht
	private Object data;
	

	
	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}




	public Nachricht(Object data)
	{
		this.data = data;
	}

	
	
	
	@Override
	public String toString()
	{
		return serialVersionUID + " ";
	}
}
