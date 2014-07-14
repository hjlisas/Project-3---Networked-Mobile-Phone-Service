// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Load.java 							**
// **   hjlisas, admu, September 2002                   **
// *******************************************************

import java.util.*;

public class Load extends Command
{
	public theServer ts;
	
	public Load( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}
	
	public void process()
	{
		StringTokenizer token = new StringTokenizer( parameter, " " );
		String ID, val;
		double amt;
		
		ID = token.nextToken();
		val = token.nextToken();
		amt = new Double( val ).doubleValue();
		temp = "";
		
		try
		{
			if ( subscribers.findSubscriber( ID ) )
			{
				if ( subscribers.getType( ID ).equals( "prepaid" ) )
				{
					prepaid.updateBalance( ID, amt );
					String temp = amt + " pesos loaded for " + ID;
					ts.broadcast( "LOAD CREDITS:" );
					ts.broadcast( temp );
				}
				else
				{
					ts.broadcast( ID + "is not a prepaid number," );
					ts.broadcast( "this transaction is not applicable to the subscriber." );
				}
			}
			else
			{
				ts.broadcast( "Invalid SIM number, subscriber does not exist!" );
			}
		}
		catch( Exception e)
		{
			ts.writeln( "ERROR" + e );
		}
	}
}