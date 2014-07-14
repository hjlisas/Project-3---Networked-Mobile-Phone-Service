// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: CallHandler.java 1.0 02/09/13         **
// **   hjlisas, admu 2002                              **
// *******************************************************

import java.sql.*;
import java.util.*;

public class CallHandler
{
	Connection c = null;
	PreparedStatement addCall, getCalls, refuteCall, payCall, 
					  getCallCost, findCall, getCall;
	String cID, cRef, cNumCalled;
	double cCharge;
	int cDuration;

	public CallHandler()
	{
		cID = "";
		cRef = "";
		cNumCalled = "";
		cCharge = 0;
		cDuration = 0;
		try
		{
			Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
		}
		catch( ClassNotFoundException c )
		{
			printError( "ClassNotFound Error: " + c.getMessage() );
		}
	}
	
	public void pay( String ID )
	{
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			payCall = c.prepareStatement( "DELETE * FROM CALLS WHERE ID = ?)" );
			payCall.setString( 1, ID );
			payCall.executeUpdate();
			payCall.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( e.getMessage() );
		}
	}
	
	public boolean findCall( String ID, String ref )
	{
		ResultSet rs;
		boolean b;
		String s;
		
		s = "";
		b = false;

		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			findCall = c.prepareStatement( "SELECT * FROM CALLS WHERE ID=? AND REF=?" );
			findCall.setString( 1, ID );
			findCall.setString( 2, ref );
			rs = findCall.executeQuery();

			while ( rs.next() )
			{
				s = rs.getString( 2 );
			}

			if ( s.equals( ref ) ) b = true;
			else b = false;
			
			findCall.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( e.getMessage() );
		}
		return b;		
	}
	
	public double getCallCost( String ID, String ref )
	{
		ResultSet rs;
		double x = 0;
		
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			getCallCost = c.prepareStatement( "SELECT * FROM CALLS WHERE ID=? AND REF=?" );
			getCallCost.setString( 1, ID );
			getCallCost.setString( 2, ref );
			rs = getCallCost.executeQuery();

			while ( rs.next() )
			{
				x = rs.getDouble( 4 );
			}

			getCallCost.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( e.getMessage() );
		}
		
		return x;
	}
	
	public void refuteThisCall( String ID, String ref )
	{
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			refuteCall = c.prepareStatement( "DELETE * FROM CALLS WHERE ID=? AND REF=?" );
			refuteCall.setString( 1, ID );
			refuteCall.setString( 2, ref );
			refuteCall.executeUpdate();
			refuteCall.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "" + e );
		}
	}
	
	public void addThisCall( String ref, String ID, String numCalled, 
								  double charge, int duration )
	{
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			addCall = c.prepareStatement( "INSERT INTO CALLS (ID, REF, NUMCALLED,COST,DURATION) VALUES (?,?,?,?,?)" );
			addCall.setString( 1, ID );
			addCall.setString( 2, ref );
			addCall.setString( 3, numCalled );
			addCall.setDouble( 4, charge );
			addCall.setInt( 5, duration );
			addCall.executeUpdate();
			addCall.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( e.getMessage() );
		}
	}
	
	public Vector getAll( String ID )
	{
		ResultSet rs;
		Vector v = new Vector();
		
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			getCalls = c.prepareStatement( "SELECT * FROM CALLS WHERE ID=?" );
			getCalls.setString( 1, ID );
			rs = getCalls.executeQuery();
			while( rs.next() )
			{
				cID = rs.getString( 1 );
				cRef = rs.getString( 2 );
				cNumCalled = rs.getString( 3 );
				cCharge = rs.getDouble( 4 );
				cDuration = rs.getInt( 5 );

				String s = cID + " " + cRef + " " + cNumCalled + " " + cCharge + " " + cDuration;
				v.addElement( s );
			}
			getCalls.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( e.getMessage() );
		}
		return v;
	}
	
	public String getCall( String ID, String ref )
	{
		ResultSet rs;
		String str = "";
		
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			getCall = c.prepareStatement( "SELECT * FROM CALLS WHERE ID=? AND REF=?" );
			getCall.setString( 1, ID );
			getCall.setString( 2, ref );
			rs = getCall.executeQuery();
			while( rs.next() )
			{
				cID = rs.getString( 1 );
				cRef = rs.getString( 2 );
				cNumCalled = rs.getString( 3 );
				cDuration = rs.getInt( 5 );

				str = "" + cRef + ": " + cNumCalled + " " + cDuration + " minutes";
			}
			getCall.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( e.getMessage() );
		}
		return str;
	}
	
	public void printError( String s ) {
		System.out.println( s );
	}
}
