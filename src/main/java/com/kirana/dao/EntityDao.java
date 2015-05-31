/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kirana.dao;

import java.util.List;

/**
 *
 * @author nikhilvs
 * @param <T>
 */
public interface EntityDao<T> {

    /**
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean addEntity(T t) throws Exception;
	public T getEntityById(long id) throws Exception;
	public List<T> getEntityList() throws Exception;
	public boolean deleteEntity(long id) throws Exception;
        public boolean updateEntity(T t) throws Exception;
}
