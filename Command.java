// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Command.java                          **
// **   hjlisas, admu, September 2002                   **
// *******************************************************
import java.io.*;
import java.net.*;

public class Command
{
	public Command() 
	{
		subscribers = new SubscriberHandler();
		postpaid = new PostpaidHandler();
		prepaid = new PrepaidHandler();
		calls = new CallHandler();
	}
	
	public void process()
	{
	}

	CallHandler calls;
	PrepaidHandler prepaid;
	PostpaidHandler postpaid;
	SubscriberHandler subscribers;
	String parameter, temp;
}