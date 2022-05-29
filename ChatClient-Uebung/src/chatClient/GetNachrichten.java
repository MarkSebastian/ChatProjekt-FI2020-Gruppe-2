package chatClient;

public class GetNachrichten extends Thread
{
	private static ChatController cc;
	
	public GetNachrichten(ChatController cc)
	{
		this.cc = cc;
	}
	
	public void run()
	{
		System.out.println("A");
		do
		{
			try
			{
				System.out.println("B");
				Thread.sleep(1000);
				if (cc.isChatNachrichtEingegangen())
				{
					System.out.println("C");
					cc.bekommeNachricht(cc.getEmpfangeneNachrichString());
				}
				System.out.println("D");
			}
			catch (InterruptedException e)
			{
				Thread.interrupted();
				e.printStackTrace();
			}

		} while (!this.isInterrupted());
	}
}
