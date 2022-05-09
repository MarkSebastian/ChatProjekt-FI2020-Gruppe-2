package eaB_NeverInaffDaten;

public class SQLBaukasten
{
	// Über diese Klasse werden dynamisch die Strings für die einzelnen SQL-Befehle zusammengebaut
	
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
	}
	
	protected String Delete_1(String Bname, String Passwort)
	{
		return delete+from+login_Daten+where+"Benutzername"+ist+fragezeichen;
	}
	
	protected String Insert_(String Bname, String Passwort)
	{
		return insert+into+login_Daten+klammerAuf+values;
	}
	
}
