package org.hibernate.java;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class JavaHibernateApp {
	private SessionFactory sessionFactory;

	
	public void init() throws Exception {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() 
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
            e.printStackTrace();
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}

	public void release() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
	
	public static void main(String[] args) {
        JavaHibernateApp app = new JavaHibernateApp();
        try
        {
         app.init();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        SessionFactory sessionFactory = app.getSessionFactory();
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
        try
        {
          app.release();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
	}
}
