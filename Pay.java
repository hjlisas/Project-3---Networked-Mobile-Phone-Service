// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Pay.java 								**
// **   hjlisas, admu, September 2002                   **
// *******************************************************

import java.util.*;

public class Pay extends Command
{
	public theServer ts;
	
	public Pay( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}

	public void process()
	{
		StringTokenizer token = new StringTokenizer( parameter, " " );
		String num = token.nextToken();
		String amt = token.nextToken();
		double balance, newBalance;
		
		try
		{
			if ( subscribers.findSubscriber( num ) )
			{
				if ( subscribers.getType( num ).equals( "postpaid" ) )
				{
					balance = postpaid.getBalance( num );
					newBalance = balance + new Double( amt ).doubleValue();
					ts.broadcast( "PAYMENTS:" );
					ts.broadcast( amt + "pesos paid by " + num );
					postpaid.updateBalance( num, newBalance );
					calls.pay( num );
				}
				else
				{
					ts.broadcast( num + " is not a postpaid subscriber." );
				}
			}
			else
			{
				ts.broadcast( "subscriber has not been activated." );
			}
		}
		catch( Exception e ) {
			ts.writeln( "ERROR" + e );
		}
	}
}