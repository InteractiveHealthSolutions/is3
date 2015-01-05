/**
 * This class contains constants and project-specific methods which will be used throughout the System
 */

package com.ihsinformatics.is3web.shared;

import java.util.ArrayList;


/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public final class IRZ
{
	private static String		resourcesPath;
	private static String		hashingAlgorithm;
	private static String		projectTitle					= "";
	private static String		databaseName					= "smssurvey";
	private static String		reportingDatabase				= "smssurvey_rpt";
	private static String		reportsDirectoryName			= "";
	private static String		defaultPassword					= "admin";
	private static int			smsPusherServiceRunupMins		= 60;
	private static int			smsUpdaterServiceRunupMins		= 30;
	private static int			responseReaderServiceRunupMins	= 30;
	private static Integer		SmsAppAssignedProjectID			= null;

	private static int			sessionLimit					= 900000;

	private static String		currentVersion						= "";
	private static String		currentUserName						= "";
	private static String		currentRole						= "";
	private static String		currentLocation						= "";
	private static String		passCode						= "";
	private static String[][]	schema;

	
	private static String		clientId						= "";

	public static String getHashingAlgorithm ()
	{
		return hashingAlgorithm;
	}

	public static void setHashingAlgorithm (String hashingAlgorithm)
	{
		IRZ.hashingAlgorithm = hashingAlgorithm;
	}
//
	public static String getClientId ()
	{
		return clientId;
	}

	public static void setClientId (String clientId)
	{
		IRZ.clientId = clientId;
	}
//
	
	public static String getProjectTitle ()
	{
		return projectTitle;
	}

	public static void setProjectTitle (String projectTitle)
	{
		IRZ.projectTitle = projectTitle;
	}

	public static String getDatabaseName ()
	{
		return databaseName;
	}

	public static void setDatabaseName (String databaseName)
	{
		IRZ.databaseName = databaseName;
	}

	public static String getReportingDatabase ()
	{
		return reportingDatabase;
	}

	public static void setReportingDatabase (String reportingDatabase)
	{
		IRZ.reportingDatabase = reportingDatabase;
	}

	public static String getReportsDirectoryName ()
	{
		return reportsDirectoryName;
	}

	public static void setReportsDirectoryName (String reportsDirectoryName)
	{
		IRZ.reportsDirectoryName = reportsDirectoryName;
	}

	public static int getSmsPusherServiceRunupMins ()
	{
		return smsPusherServiceRunupMins;
	}

	public static void setSmsPusherServiceRunupMins (int smsPusherServiceRunupMins)
	{
		IRZ.smsPusherServiceRunupMins = smsPusherServiceRunupMins;
	}

	public static int getSmsUpdaterServiceRunupMins ()
	{
		return smsUpdaterServiceRunupMins;
	}

	public static void setSmsUpdaterServiceRunupMins (int smsUpdaterServiceRunupMins)
	{
		IRZ.smsUpdaterServiceRunupMins = smsUpdaterServiceRunupMins;
	}

	public static int getResponseReaderServiceRunupMins ()
	{
		return responseReaderServiceRunupMins;
	}

	public static void setResponseReaderServiceRunupMins (int responseReaderServiceRunupMins)
	{
		IRZ.responseReaderServiceRunupMins = responseReaderServiceRunupMins;
	}

	public static void setSmsAppAssignedProjectID (Integer smsAppAssignedProjectID)
	{
		SmsAppAssignedProjectID = smsAppAssignedProjectID;
	}

	public static Integer getSmsAppAssignedProjectID ()
	{
		return SmsAppAssignedProjectID;
	}

	public static int getSessionLimit ()
	{
		return sessionLimit;
	}

	public static void setSessionLimit (int sessionLimit)
	{
		IRZ.sessionLimit = sessionLimit;
	}

	public static String[][] getSchema ()
	{
		return schema;
	}

	public static void setSchema (String[][] schema)
	{
		IRZ.schema = schema;
	}



	public static void setResourcesPath (String resourcesPath)
	{
		IRZ.resourcesPath = resourcesPath;
	}

	public static void fillSchema (String[][] schema)
	{
		IRZ.schema = schema;
	}



	/**
	 * Get maximum length of a column in a table
	 * 
	 * @param tablename
	 * @param columnName
	 * @return
	 */
	public static int getMaxLength (String tablename, String columnName)
	{
		try
		{
			for (int i = 0; i < schema.length; i++)
			{
				if (schema[i][0].equals (tablename) && schema[i][1].equals (columnName))
					return Integer.parseInt (schema[i][4]);
			}
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace ();
			return 0;
		}
		return 255;
	}

	/**
	 * Concatenate an Array of Strings into single String
	 * 
	 * @param array
	 * @return string
	 */
	public static String concatenateArray (String[] array)
	{
		StringBuilder concatenated = new StringBuilder ();
		for (String s : array)
			concatenated.append (s + ",");
		// Remove additional comma
		concatenated.deleteCharAt (concatenated.length () - 1);
		return concatenated.toString ();
	}




	public static String getCurrentVersion ()
	{
		return currentVersion;
	}

	public static void setCurrentVersion (String version)
	{
		IRZ.currentVersion = version;
	}

	/**
	 * Get current User Name (saved in cookies on client-side)
	 * 
	 * @return currentUser
	 */
	public static String getCurrentUserName ()
	{
		return currentUserName;
	}

	/**
	 * Set current user
	 * 
	 * @param userName
	 */
	public static void setCurrentUserName (String userName)
	{
		IRZ.currentUserName = userName;
	}

	public static String getCurrentRole ()
	{
		return currentRole;
	}

	public static void setCurrentRole (String userRole)
	{
		IRZ.currentRole = userRole;
	}

	/**
	 * @return the currentLocation
	 */
	public static String getCurrentLocation ()
	{
		return currentLocation;
	}

	/**
	 * @param currentLocation
	 *            the currentLocation to set
	 */
	public static void setCurrentLocation (String currentLocation)
	{
		IRZ.currentLocation = currentLocation;
	}

	/**
	 * Get pass code (first 4 characters of User's password)
	 * 
	 * @return passCode
	 */
	public static String getPassCode ()
	{
		return passCode;
	}

	/**
	 * Set pass code for current user
	 * 
	 * @param passcode
	 */
	public static void setPassCode (String passcode)
	{
		IRZ.passCode = passcode;
	}

	/**
	 * @return the reportPath
	 */
	public static String getReportPath ()
	{
		String path = getResourcesPath ();
		char separatorChar = (path.charAt (path.length () - 2));
	//	return getResourcesPath () + reportsDirectoryName + separatorChar;
		return getResourcesPath () + reportsDirectoryName ;
	}

	/**
	 * @return the resourcesPath
	 */
	public static String getResourcesPath ()
	{
		return resourcesPath;
	}

	/**
	 * 
	 * @return
	 */
	public static String getDefaultPassword ()
	{
		return defaultPassword;
	}
}
