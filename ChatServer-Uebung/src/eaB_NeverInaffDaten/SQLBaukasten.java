package eaB_NeverInaffDaten;

public class SQLBaukasten
{
	// �ber diese Klasse werden dynamisch die Strings f�r die einzelnen SQL-Befehle zusammengebaut
	
	private String chatroom;
	private String client;
	private String clientChatroom_ZT;
	private String loginClient_ZT;
	private String loginliste;
	private String nachricht;
	private String login_Daten;
	private String select;
	private String sternchen;
	private String from;
	private String insert;
	private String into;
	private String klammerAuf;
	private String klammerZu;
	private String fragezeichen;
	private String komma;
	private String update;
	private String set;
	private String where;
	private String ist;
	private String like;
	private String delete;
	private String and;
	private String or;
	private String hochkomma;
	private String values;
	private String id;
	private String inhalt;
	private String timestamp;
	private String client_id;
	private String chatroom_id;
	private String timestamp_beginn;
	private String timestamp_ende;
	private String ip;
	private String loginliste_id;
	private String benutzername;
	private String chatroomname;
	private String istPrivat;
	private String passwort;
	private String accountname;
	
	public SQLBaukasten()
	{
		chatroom = "Chatroom ";
		client = "Client ";
		clientChatroom_ZT = "Client/Chatroom_ZT ";
		loginClient_ZT = "Login/Client_ZT ";
		loginliste = "Loginliste ";
		nachricht = "Nachricht ";
		login_Daten = "Login_Daten ";
		select = "Select ";
		sternchen = "* ";
		from = "from ";
		insert = "Insert ";
		into = "into ";
		klammerAuf = "( ";
		klammerZu = ") ";
		fragezeichen = "? ";
		komma = ", ";
		update = "Update ";
		set = "set ";
		where = "where ";
		ist = "= ";
		like = "like ";
		delete = "Delete ";
		and="and ";
		or="or ";
		hochkomma="' "; 
		values="values ";
		id="ID ";
		inhalt="inhalt ";
		timestamp="timestamp ";
		client_id="client_ID ";
		chatroom_id="chatroom_ID ";
		timestamp_beginn="timestamp_beginn ";
		timestamp_ende="timestamp_ende ";
		ip="ID ";
		loginliste_id="loginliste_ID ";
		benutzername="benutzername ";
		chatroomname="chatroomname ";
		istPrivat="istPrivat ";
		passwort="passwort ";
		accountname="accountname ";
	}
	 
	protected String delete_1()
	{
		return delete+from+login_Daten+where+"Benutzername"+ist+fragezeichen;
	}
	
	protected String insert_LoginDB()
	{
		return insert+into+login_Daten+klammerAuf+benutzername+komma+passwort+klammerZu+values+klammerAuf+fragezeichen+komma+fragezeichen+klammerZu;
	}
	
	protected String insert_Client()
	{
		return insert+into+client+klammerAuf+benutzername+klammerZu+values+klammerAuf+fragezeichen+klammerZu; 
	}
	
	protected String insert_Loginliste()
	{
		return insert+into+loginliste+klammerAuf+timestamp_beginn+komma+timestamp_ende+komma+ip+klammerZu+values+klammerAuf+fragezeichen+komma+fragezeichen+komma+fragezeichen+klammerZu; 
	}
	
	protected String insert_LoginClientZT() 
	{
		return insert+into+loginClient_ZT+klammerAuf+loginliste_id+komma+client_id+klammerZu+values+klammerAuf+

				klammerAuf+select+id+from+client+where+//fertigmachen+klammerZu+komma+
				klammerAuf+klammerZu+klammerZu; 
		  
	}
	
	protected String select_Client_id()
	{
		return select+id+from+client+where+accountname+ist+fragezeichen;
	}
	
	protected String select_Login_id()
	{
		return select+id+from+loginliste+where+timestamp_beginn+ist+fragezeichen+and+ip+ist+fragezeichen ;
	}
	
}