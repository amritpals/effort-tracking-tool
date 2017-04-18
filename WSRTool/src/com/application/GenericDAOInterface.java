/**
 * 
 */
package com.application;

import java.util.Set;

/**
 * @author Amrit
 *
 */
public interface GenericDAOInterface<T> {
	public Integer create(T t);
	public Set<T> get();
	public T getById(Integer id);
	public T update(T t, Integer id);
	public Integer delete(Integer id);
}
