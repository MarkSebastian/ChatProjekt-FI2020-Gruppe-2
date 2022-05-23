package chatClient;

public class Datei
{
	private int id;
	private String name;
	private byte[] data;
	private String dateiendung;

	public Datei(int id, String name, byte[] data, String dateiendung)
	{
		super();
		this.id = id;
		this.name = name;
		this.data = data;
		this.dateiendung = dateiendung;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setData(byte[] data)
	{
		this.data = data;
	}

	public String getDateiendung()
	{
		return dateiendung;
	}

	public void setDateiendung(String dateiendung)
	{
		this.dateiendung = dateiendung;
	}

}
