// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Call.java 							**
// **   hjlisas, admu, September 2002                   **
// *******************************************************

import java.util.*;

public class Call extends Command
{
	private final int POSTPAIDCOST = BusinessRules.POSTPAID_CALL_COST;
	private final int PREPAIDCOST = BusinessRules.PREPAID_CALL_COST;
	private final int DUE_LIMIT = BusinessRules.POSTPAID_DUE_LIMIT;
	public theServer ts;
	
	public Call( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}

	public void process()
	{
		StringTokenizer token = new StringTokenizer( parameter, " " );
		String ID, numCalled, length, ref, type, temp;
		double balance, cost, newBalance;
		int callLength, callCost, timeAvailable, callAllowed;
		
		ID = token.nextToken(); numCalled = token.nextToken();
		length = token.nextToken(); ref = token.nextToken();
		
		try
		{
			if ( subscribers.findSubscriber( ID ) )
			{
				type = subscribers.getType( ID );
				callLength = new Integer( length ).intValue();
				
				if ( type.equals( "prepaid" ) )
				{
					balance = prepaid.getBalance( ID );
					callCost = callLength * PREPAIDCOST;
					timeAvailable = (int)balance / PREPAIDCOST;
					
					if ( balance == 0 | balance < PREPAIDCOST )
					{
						ts.writeln( "call cannot be made due to insufficient balance." );
					}
					else if ( callCost > balance )
					{	
						if ( timeAvailable < callLength )
						{
							cost = timeAvailable * PREPAIDCOST;
							newBalance = balance - cost;
							prepaid.updateBalance( ID, newBalance );
							calls.addThisCall( ref, ID, numCalled, cost, timeAvailable );
							temp = ref + ": " + timeAvailable + "-minute call made to " +
								   numCalled + " from " + ID;
							ts.broadcast( "CALL MADE:" );
							ts.broadcast( temp );
						}
					}
					else 
					{
						cost = callLength * PREPAIDCOST;
						newBalance = balance - cost;
						calls.addThisCall( ref, ID, numCalled, cost, callLength );
						prepaid.updateBalance( ID, newBalance );
						temp = ref + ": " + callLength + "-minute call made to " +
							   numCalled + " from " + ID;
						ts.broadcast( "CALL MADE:" );
						ts.broadcast( temp );
					}
				}
				else if ( type.equals( "postpaid" ) )
				{
					callCost = callLength * POSTPAIDCOST;
					balance = postpaid.getBalance( ID );
					callAllowed = ( 1000 - (int)balance ) / POSTPAIDCOST;
					timeAvailable =  callAllowed / POSTPAIDCOST;
				
					if ( balance >= DUE_LIMIT )
					{
						ts.broadcast( "call cannot be made. credit limited has been reached." );
					}
					else if ( callCost > callAllowed )
					{
						cost = timeAvailable * POSTPAIDCOST;
						newBalance = balance + cost;
						calls.addThisCall( ref, ID, numCalled, cost, timeAvailable );
						postpaid.updateBalance( ID, newBalance );
						temp = ref + ": " + timeAvailable + "-minute call made to " +
							   numCalled + " from " + ID;
						ts.broadcast( "CALL MADE:" );
						ts.broadcast( temp );
					}
					else 
					{
						cost = callLength * POSTPAIDCOST;
						newBalance = balance + cost;
						calls.addThisCall( ref, ID, numCalled, cost, callLength );
						postpaid.updateBalance( ID, newBalance );
						temp = ref + ": " + callLength + "-minute call made to " +
							   numCalled + " from " + ID;
						ts.broadcast( "CALL MADE:" );
						ts.broadcast( temp );
					}
				}
			}
			else
			{
				ts.broadcast( "Subscriber has not been activated" );
			}
			
		}
		catch( Exception e )
		{
			ts.writeln( "ERROR" + e );
		}
	}
}