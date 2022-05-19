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
	private String passwort;
	private String accountname;
	private String max;
	private String hashcode;
	
	public SQLBaukasten()
	{
		//initialisieren
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
		max="max ";
		hashcode="hashcode ";
	}
	
	//deletes
	protected String delete_loginDaten()
	{
		//Löscht Zeile mit LoginDaten aus login_Daten
		return delete+from+login_Daten+where+"Benutzername"+ist+fragezeichen;
	}
	
	//inserts
	protected String insert_LoginDB()
	{
		//induziert Zeile mit logindaten in login_Daten
		return insert+into+login_Daten+klammerAuf+benutzername+komma+passwort+klammerZu+values+klammerAuf+fragezeichen+komma+fragezeichen+klammerZu;
	}
	
	protected String insert_Client()
	{
		//lötet Zeile mit clientdaten auf die Metallplatte
		return insert+into+client+klammerAuf+benutzername+klammerZu+values+klammerAuf+fragezeichen+klammerZu; 
	}
	
	protected String insert_Loginliste()
	{
		//schreibt in die liste der Logins eine neuen login
		return insert+into+loginliste+klammerAuf+timestamp_beginn+komma+ip+klammerZu+values+klammerAuf+fragezeichen+komma+fragezeichen+klammerZu; 
	}
	
	protected String insert_LoginClientZT() 
	{
		//Verbindet Login und Client->Zuordnungstabelle
		return insert+into+loginClient_ZT+klammerAuf+loginliste_id+komma+client_id+klammerZu+values+klammerAuf+

				klammerAuf+select_Client_id()+klammerZu+komma+
				klammerAuf+select_Loginlisten_id()+klammerZu+klammerZu; 
	}
	
	protected String insert_Chatroom()
	{
		//gewährt einen eintrag für einen chatroom
		return insert+into+chatroom+klammerAuf+chatroomname+komma+hashcode+klammerZu+values+fragezeichen;
	}
	
	protected String insert_ClientChatroomZT()
	{
		//stellt Verbindung zwischen Chatroom und Client her. verwendet für hinzufügen von cliets zu chatrooms
		return insert+into+clientChatroom_ZT+klammerAuf+client_id+komma+chatroom_id+klammerZu+values+klammerAuf+
				klammerAuf+select_Client_id()+klammerZu+komma+
				select_Chatroom_nach_hashcode()+klammerZu; 
	}
	
	protected String insert_Nachricht()
	{
		//Nachricht einfügen
		return insert+into+nachricht+klammerAuf+inhalt+komma+timestamp+komma+client_id+komma+chatroom_id+klammerZu+
				values+klammerAuf+fragezeichen+komma+fragezeichen+komma+
				select_Client_id()+komma+
				select_Chatroom_nach_hashcode()+klammerZu;
	}
	
	//updates
	protected String update_Benutzername()
	{
		//benutzername (nicht Accname) wird aktualisiert
		return update+client+set+benutzername+ist+fragezeichen+where+benutzername+ist+fragezeichen;
	}
	
	protected String update_timestamp()
	{
		//bei Ausloggen wird der momentane login mit end-timestamp versehen
		return update+loginliste+set+timestamp_ende+ist+fragezeichen+where+id+ist;
	}
	
	//selects
	protected String select_Client_id()
	{
		//schnappt sich die client id aus dem accname
		return select+id+from+client+where+accountname+ist+fragezeichen;
	}
	
	protected String select_Loginlisten_id()
	{
		//zieht sich die id aus loginliste mit der angefragten ip und angefragten timestamp_beginn
		return select+id+from+loginliste+where+timestamp_beginn+ist+fragezeichen+and+ip+ist+fragezeichen ;
	}
	
	protected String select_Chatroom_nach_hashcode()
	{
		//gibt die id des chatrooms mit bekanntem hashcode
		return select+id+from+chatroom+where+hashcode+ist+fragezeichen;
	}
	
	protected String select_Chatroomname_nach_hashcode()
	{
		//holt den namen des chatrooms mit brkanntem hashcode
		return select+chatroomname+from+chatroom+where+hashcode+ist+fragezeichen;
	}
	
	protected String select_hashcode()
	{
		//beantwortet die frage, ob hashcode bereits vorhanden
		return select+hashcode+from+chatroom+where+hashcode+ist+fragezeichen;
	}
	
	protected String select_Loginname()
	{
		//abfrage zur überprüfung ob benutzername frei ist
		return select+benutzername+from+login_Daten+where+benutzername+ist+fragezeichen;
	}
	
	protected String select_passwort()
	{
		//schnabuliert sich das passwort zum benutzer
		return select+passwort+from+login_Daten+where+benutzername+ist+fragezeichen;
	}
	
	protected String select_benutzername_nach_Client_id()
	{
		//gönnt sich den accountnamen für bekannte id aus client
		return select+accountname+from+client+where+id+ist+fragezeichen;
	}
	
	protected String select_chatroomnamen_by_client()
	{
		//schenkt dem server die ergebnisse aus chatrooms für bekannten accnamen eines clients
		return select+chatroomname+from+chatroom+where+id+ist+klammerAuf+select+chatroom_id+from+clientChatroom_ZT+where+client_id+ist+klammerAuf+select_Client_id()+klammerZu+klammerZu;
	}
	
	protected String select_nachricht_in_chatroom()
	{
		//jöötet die nachrichten aus bekannten chatroom aus
		return select+"cl."+benutzername+komma+"n."+timestamp+komma+"n."+inhalt+from+nachricht+"n "+komma+client+"cl"+komma+chatroom+"ch "+where+"n."+chatroom_id+ist+"ch."+id+and+"ch."+hashcode+ist+fragezeichen;
	} 
}
