/**
 * 
 */
package com.application;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Amrit
 *
 */
public class GenericDAO<T> implements GenericDAOInterface<T> {
	
	private Class<T> classType;  
	
	private Session session = null;
	private Transaction tx = null;

	public GenericDAO(Class<T> type) {
		session = HibernateUtil.getSessionFactory().openSession();
		this.classType = type;
	}

	@Override
	public Integer create(T t) {
		Integer id = null;
		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(t);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	@Override
	public Set<T> get() {
		Set<T> objSet = new HashSet<>(0);
		try {
			tx = session.beginTransaction();

			List users = session.createQuery("FROM " + classType.getName()).list();
			for (Iterator iterator = users.iterator(); iterator.hasNext();) {
				T t = (T) iterator.next();
				objSet.add(t);
			}

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return objSet;
	}

	@Override
	public T getById(Integer id) {
		T t = null;
		try {
			tx = session.beginTransaction();

			t = (T) session.get(classType.getName(), id);

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return t;
	}

	@Override
	public T update(T t, Integer id) {
		T tmpT = null;
		try {
			tx = session.beginTransaction();
			tmpT = (T) session.get(classType.getName(), id);

			if (tmpT == null) {
				return null;
			} else {
				tmpT = (T) session.merge(t);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return tmpT;
	}

	@Override
	public Integer delete(Integer id) {
		T t = null;
		Integer returnVal = 200;
		try {
			tx = session.beginTransaction();
			
			t = (T) session.get(classType.getName(), id);
			
			if(t != null){
				session.delete(t);
				returnVal = 200;
			} else {
				returnVal = 404;
			}
			
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return returnVal;
	}


}
