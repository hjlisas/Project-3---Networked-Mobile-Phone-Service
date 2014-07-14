// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Undo.java 1.0 02/09/13                **
// **   hjlisas, admu 2002                              **
// *******************************************************

import java.util.*;

public class Undo extends Command
{
	public theServer ts;
	
	public Undo( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}

	public void process()
	{
		StringTokenizer token = new StringTokenizer( parameter, " " );
		String num = token.nextToken();
		String ref = token.nextToken();
		double balance, newBalance;
		
		try
		{
			if ( subscribers.findSubscriber( num ) )
			{
				if ( subscribers.getType( num ).equals( "postpaid" ) )
				{
					balance = postpaid.getBalance( num );
					
					if ( calls.findCall( num, ref ) )
					{
						newBalance = balance + calls.getCallCost( num, ref );
						postpaid.updateBalance( num, newBalance );
						calls.refuteThisCall( num, ref );
						String temp = "credited " + calls.getCallCost( num, ref ) +
									  " pesos to " + num + "(" +
									  calls.getCall( num, ref) + ")";
						ts.broadcast( "REFUTE CALL:" );
						ts.broadcast( temp );
					}
					else
					{
						ts.broadcast( "undo not allowed for this call." );
					}
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