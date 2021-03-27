package org.hibernate.java;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import junit.framework.TestCase;

public class JavaApiTest extends TestCase {
	private SessionFactory sessionFactory;

	@Override
	protected void setUp() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() 
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}

	@Override
	protected void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void testBasicUsage() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save( new Message( " first Message!", new Date() ) );
		session.save( new Message( "second Message", new Date() ) );
		session.getTransaction().commit();
		session.close();

		session = sessionFactory.openSession();
		session.beginTransaction();
		List result = session.createQuery( "from Message" ).list();
		for ( Message message : (List<Message>) result ) {
			System.out.println( "Message (" + message.getDate() + ") : " + message.getTitle() );
		}
		session.getTransaction().commit();
		session.close();
	}
}
