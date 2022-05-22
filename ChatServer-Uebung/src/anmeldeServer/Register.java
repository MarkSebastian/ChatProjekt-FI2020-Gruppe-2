package anmeldeServer;

import com.esotericsoftware.kryo.Kryo;

import Message.nachrichtP.FehlerNachricht;
import Message.nachrichtP.LogInNachricht;

public class Register
{
	public static void register(Kryo kryo)
	{
		kryo.register(LogInNachricht.class);
		kryo.register(FehlerNachricht.class);
		kryo.register(String.class);
	}
}
