package privatChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class PrivatChatSenden implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long hashcode;
	private String chatName;
	private String user;
	private ArrayList<String> empfaenger;
	
	public PrivatChatSenden()
	{
	}
	
	public PrivatChatSenden(String chatName, String user, ArrayList<String> empfaenger)
	{
		this.chatName = chatName;
		this.user = user;
		this.empfaenger = empfaenger;
		hashcode = hashCode();
	}

	public String getChatName()
	{
		return chatName;
	}

	public String getUser()
	{
		return user;
	}

	public ArrayList<String> getEmpfaenger()
	{
		return empfaenger;
	}

	public long getHashcode()
	{
		return hashcode;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(chatName, empfaenger, user);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrivatChatSenden other = (PrivatChatSenden) obj;
		return Objects.equals(chatName, other.chatName) && Objects.equals(empfaenger, other.empfaenger)
				&& Objects.equals(user, other.user);
	}
}
