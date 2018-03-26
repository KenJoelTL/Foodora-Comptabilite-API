/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foodora.comptabilite.modele.DAO;

import java.sql.Connection;
import java.util.List;
/**
 *
 * @author Charles
 */

public abstract class DAO<T> {
	protected Connection cnx;

        public DAO(){}
        
	public DAO(Connection cnx) {
		this.cnx = cnx;
	}
	
	public Connection getCnx() {
		return cnx;
	}

	public void setCnx(Connection cnx) {
		this.cnx = cnx;
	}

	public abstract boolean create(T x);    //INSERT
	public abstract T retrieve(int id);     //SELECT
	public abstract T retrieve(String id);  //SELECT
	public abstract boolean update(T x);    //UPDATE
	public abstract boolean delete(T x);    //DELETE
	public abstract List<T> findAll();      //SELECT
}