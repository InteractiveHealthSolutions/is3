/**
 * Implements hibernate features. This class must be initialized before performing any hibernate operation 
 */

package com.ihsinformatics.is3web.server.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.hibernate.EntityMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class HibernateUtil implements Serializable
{
	private static final long		serialVersionUID	= -2333198879612643152L;
	public static HibernateUtil		util				= new HibernateUtil ();
	private static SessionFactory	factory;

	// private Class<?>[] classes;

	/**
	 * Default Constructor to initialize Session
	 */
	public HibernateUtil ()
	{
		/* Use when trying Annotation */
		// factory = getInitializedConfiguration().buildSessionFactory();
		factory = new Configuration ().configure ().buildSessionFactory ();
	}

	/**
	 * Create a query object from query string
	 * 
	 * @param queryString
	 * @return
	 */
	public Query create (String query)
	{
		return getSession ().createQuery (query);
	}

	/**
	 * Get record count for given query
	 * 
	 * @return
	 */
	public long count (String query)
	{
		return count (create (query));
	}

	/**
	 * Get record count for given query
	 * 
	 * @return
	 */
	public long count (Query query)
	{
		return Long.parseLong (query.uniqueResult ().toString ());
	}

	/**
	 * Check if certain data exists in the database
	 * 
	 * @param query
	 * @return
	 */
	public boolean exists (String query)
	{
		return count (query) > 0;
	}

	/**
	 * Overloaded method for findObject(String)
	 * 
	 * @param query
	 * @return
	 */
	public Object findObject (String query)
	{
		try
		{
			return findObjects (query)[0];
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return null;
		}
	}

	/**
	 * Get a list of objects from given query.
	 * 
	 * @param query
	 * @return
	 */
	public Object[] findObjects (String query)
	{
		try
		{
			Session session = getSession ();
			Query q = session.createQuery (query);
			List<?> list = q.list ();
			return list.toArray ();
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return null;
		}
	}

	/**
	 * Get a list of objects from given SQL query
	 * 
	 * @param SQLQuery
	 * @return
	 */
	public Object selectObject (String sqlQuery)
	{
		return selectObjects (sqlQuery)[0];
	}

	/**
	 * Get a list of objects from given SQL query
	 * 
	 * @param SQLQuery
	 * @return
	 */
	public Object[] selectObjects (String sqlQuery)
	{
		Session session = getSession ();
		// session.beginTransaction();
		SQLQuery q = session.createSQLQuery (sqlQuery);
		List<?> list = q.list ();
		System.out.println(list);
		return list.toArray ();
	}

	/**
	 * Get a list of objects from given SQL query
	 * 
	 * @param SQLQuery
	 * @return
	 */
	public Object[][] selectData (String SqlQuery)
	{
		Session session = getSession ();
		SQLQuery q = session.createSQLQuery (SqlQuery);
		List<?> list = q.list ();
		Object[][] tableData = new Object[list.size ()][];
		try
		{
			int cnt = 0;
			for (ListIterator<?> iter = list.listIterator (); iter.hasNext ();)
			{
				Object[] array = (Object[]) iter.next ();
				tableData[cnt++] = array;
			}
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return null;
		}
	}

	/**
	 * Update an object present in the database
	 * 
	 * @param obj
	 * @return true if record was successfully updated false if record did not
	 *         save for some constraint null if transaction causes breakage in
	 *         integrity (duplication, etc.)
	 */
	public Boolean update (Object obj)
	{
		try
		{
			Session session = getSession ();
			session.update (obj);
			session.flush ();
			session.close ();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			if (e.getCause ().getClass () == BatchUpdateException.class)
				return null;
			else
				return false;
		}
	}

	/**
	 * Saves multiple objects into database
	 * 
	 * @param obj
	 * @return true if all record was successfully saved false if any of the
	 *         records did not save
	 */
	public boolean bulkSave (Object[] objects)
	{
		try
		{
			Session session = getSession ();
			for (Object o : objects)
				session.save (o);
			session.flush ();
			session.close ();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * Save an object in the database
	 * 
	 * @param obj
	 * @return true if record was successfully saved false if record did not
	 *         save for some constraint null if transaction causes breakage in
	 *         integrity (duplication, etc.)
	 */
	public Boolean save (Object obj)
	{
		try
		{
			Session session = getSession ();
			session.save (obj);
			session.flush ();
			session.close ();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			if (e.getCause ().getClass () == BatchUpdateException.class)
				return null;
			else
				return false;
		}
	}

	/**
	 * Deletes an Object from database
	 * 
	 * @param obj
	 * @return true if record was successfully deleted false if record did not
	 *         delete for some constraint null if transaction causes breakage in
	 *         integrity (duplication, etc.)
	 */
	public Boolean delete (Object obj)
	{
		try
		{
			Session session = getSession ();
			session.delete (obj);
			session.flush ();
			session.close ();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace ();
			if (e.getCause ().getClass () == BatchUpdateException.class)
				return null;
			else
				return false;
		}
	}

	/**
	 * Executes a DML command
	 * 
	 * @param command
	 *            SQL statement as command
	 * @return number of records affected
	 */
	public int runCommand (String command)
	{
		Session session = getSession ();
		Transaction transaction = session.beginTransaction ();
		SQLQuery q = session.createSQLQuery (command);
		int results = q.executeUpdate ();
		transaction.commit ();
		return results;
	}

	/**
	 * Executes a Stored Procedure
	 * 
	 * @param procedure
	 *            SQL statement as procedure
	 * @return number of records affected
	 */
	@SuppressWarnings("deprecation")
	public boolean runProcedure (String procedure)
	{
		Session session = getSession ();
		session.beginTransaction ();
		try
		{
			CallableStatement callableStatement = session.connection ().prepareCall (procedure);
			System.out.println ("Executing stored procedure: " + procedure);
			callableStatement.execute ();
			session.getTransaction ().commit ();
			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace ();
			return false;
		}
	}

	/**
	 * @return the session
	 */
	public Session getSession ()
	{
		return factory.openSession ();
	}

	/**
	 * Returns the respective Database class to which an object belongs
	 * 
	 * @param obj
	 *            object to be identified
	 * @return Class object of class 'Class'
	 */
	public Class<?> identifyClass (Object obj)
	{
		return obj.getClass ();
	}

	/**
	 * Returns Entity name (table name) of a Persistance class
	 * 
	 * @param persistanceClass
	 * @return
	 */
	public String getTableName (Class<?> persistanceClass)
	{
		return factory.getClassMetadata (persistanceClass).getEntityName ();
	}

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and sub packages.
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Class<?>[] getClasses (String packageName) throws ClassNotFoundException, IOException
	{
		ClassLoader classLoader = Thread.currentThread ().getContextClassLoader ();
		assert classLoader != null;
		String path = packageName.replace ('.', '/');
		Enumeration<URL> resources = classLoader.getResources (path);
		List<File> dirs = new ArrayList<File> ();
		while (resources.hasMoreElements ())
		{
			URL resource = resources.nextElement ();
			dirs.add (new File (resource.getFile ()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>> ();
		for (File directory : dirs)
		{
			classes.addAll (findClasses (directory, packageName));
		}
		return classes.toArray (new Class[classes.size ()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and sub
	 * directories.
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses (File directory, String packageName) throws ClassNotFoundException
	{
		List<Class<?>> classes = new ArrayList<Class<?>> ();
		if (!directory.exists ())
		{
			return classes;
		}
		File[] files = directory.listFiles ();
		for (File file : files)
		{
			if (file.isDirectory ())
			{
				assert !file.getName ().contains (".");
				classes.addAll (findClasses (file, packageName + "." + file.getName ()));
			}
			else if (file.getName ().endsWith (".class"))
			{
				classes.add (Class.forName (packageName + '.' + file.getName ().substring (0, file.getName ().length () - 6)));
			}
		}
		return classes;
	}

	public void dump (String backupFolder, String table) throws IOException
	{
		Document document = DocumentHelper.createDocument ();
		Element rootElement = document.addElement (table);

		Iterator<Element> it = queryDOM4J ("from " + table).iterator ();
		while (it.hasNext ())
		{
			Element element = it.next ();
			rootElement.add (element);
		}
		saveDocument (document, backupFolder, table);
	}

	@SuppressWarnings("unchecked")
	public List<Element> queryDOM4J (String hsql)
	{
		Transaction tx = null;
		Session session = getSession ();
		Session dom4jSession = session.getSession (EntityMode.DOM4J);
		List<Element> list = null;
		try
		{
			tx = session.beginTransaction ();
			list = dom4jSession.createQuery (hsql).list ();
			tx.commit ();
		}
		catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback ();
			}
			e.printStackTrace ();
		}
		finally
		{
			session.close ();
		}
		return list;
	}

	protected void saveDocument (Document document, String path, String table) throws IOException
	{
		if (canWrite (path))
		{
			File file = new File (getDumpFile (path, table));
			XMLWriter writer = new XMLWriter (new FileWriter (file));
			writer.write (document);
			writer.close ();
		}
	}

	protected boolean canWrite (String path)
	{
		File dir = new File (path);
		if (!dir.exists ())
		{
			boolean makeDir = dir.mkdirs ();
			if (!makeDir)
			{
				System.out.println ("Unable to create directory: " + path);
			}
		}

		return dir.exists ();
	}

	protected String getDumpFile (String path, String tableName)
	{
		return path + "/" + tableName + ".xml";
	}

	/**
	 * Restore database.
	 * 
	 * @warning for this to succeed the constraints must allow null
	 * @param path
	 *            backup folder
	 */
	public void restore (String path)
	{
		File[] files = getDumpFiles (path);
		if (files != null)
		{
			restoreTables (files);
		}
		else
		{
			System.out.println ("[ERROR] Database restore | Backup folder '" + path + "' does not exist.");
		}
	}

	/**
	 * Restore database tables
	 * 
	 * @param files
	 *            backup files
	 */
	protected void restoreTables (File[] files)
	{
		Session session = getSession ();
		Session dom4jSession = session.getSession (EntityMode.DOM4J);
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction ();
			restoreTables (dom4jSession, files);
			session.flush ();
			tx.commit ();
			session.close ();
		}
		catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback ();
				session.close ();
			}
			e.printStackTrace ();
		}
	}

	/**
	 * Restore database tables
	 * 
	 * @param session
	 * @param files
	 *            backup files
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public void restoreTables (Session session, File[] files) throws DocumentException
	{
		for (File file : files)
		{
			SAXReader reader = new SAXReader ();
			Document document = reader.read (file);

			Iterator<Object> it = document.getRootElement ().nodeIterator ();
			while (it.hasNext ())
			{
				Object obj = it.next ();
				if (obj instanceof DefaultElement)
				{
					session.replicate (obj, ReplicationMode.OVERWRITE);
				}
			}
		}
	}

	/**
	 * Get a list of files from specified folder
	 * 
	 * @param path
	 *            backup folder
	 * @return list of backup files or null if path folder does not exist
	 */
	public File[] getDumpFiles (String path)
	{
		File dir = new File (path);
		if (dir.exists ())
		{
			return dir.listFiles ();
		}
		return null;
	}
}
