/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foodora.comptabilite.modele.service;

import com.foodora.comptabilite.modele.DAO.DAO;
import com.foodora.comptabilite.modele.DAO.ItemTransactionDAO;
import com.foodora.comptabilite.modele.DAO.TransactionDAO;
import com.foodora.comptabilite.modele.ItemTransaction;
import com.foodora.comptabilite.modele.Transaction;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import jdbc.Config;
import jdbc.Connexion;

/**
 *
 * @author Joel
 */
public class AbstractFacade<T> {

    private Class<T> entityClass;
    private DAO dao;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        if (entityClass.isInstance(new Transaction())) {
            this.dao = new TransactionDAO();
        } else if (entityClass.isInstance(new ItemTransaction())) {
            this.dao = new ItemTransactionDAO();
        }
    }

    public Response create(T entity) {
//        JSONobject jo = new JSONobject();
//        Gson gson = new gson();
        ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
        int id = 0;
        String jsonMessage = "\"message\":\"Erreur dans la création de l'entité\"}";
        try {
            Class.forName(Config.DRIVER);
            Connection cnx = Connexion.getInstance();
            dao.setCnx(cnx);
            id = dao.create(entity);   
            if(id > 0){
                jsonMessage = "\"message\":\"L'élément a été créée avec succès\"}";
                builder.status(Response.Status.CREATED);
            }

        } catch (SQLException | ClassNotFoundException e) {
            builder.status(Response.Status.SERVICE_UNAVAILABLE);
        } finally {
            Connexion.close();
        }
        String jsonId = "{\"id\":"+id+",";
        builder.entity(jsonId + jsonMessage);
        return  builder.build();
    }

    public Response edit(T entity) {
        ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
        String jsonMessage = "\"message\":\"Erreur dans la création de l'entité\"}";
        int nb = 0;
        try {
            Class.forName(Config.DRIVER);
            Connection cnx = Connexion.getInstance();
            dao.setCnx(cnx);
            nb = dao.update(entity);   
            if(nb > 0){
                jsonMessage = "\"message\":\"L'élément a été créée avec succès\"}";
                builder.status(Response.Status.ACCEPTED);
            }

        } catch (SQLException | ClassNotFoundException e) {
            builder.status(Response.Status.SERVICE_UNAVAILABLE);
        } finally {
            Connexion.close();
        }
        String jsonId = "{\"nombreModifie\":"+nb+",";
        builder.entity(jsonId + jsonMessage);
        return builder.build();
    }

    public void remove(T entity) {
        try {
            Class.forName(Config.DRIVER);
            Connection cnx = Connexion.getInstance();
            dao.setCnx(cnx);

            if (dao.delete(entity)) {
//                    System.out.println("{'message':'transaction supprimee', 'id':" + ((T)entity).getId() + "}");
            } else {
                System.out.println("{}");
            }

        } catch (SQLException | ClassNotFoundException e) {
        } finally {
            Connexion.close();
        }
    }

    public T find(Object id) {
        Object o = null;
        try {
            Class.forName(Config.DRIVER);
            Connection cnx = Connexion.getInstance();
            dao.setCnx(cnx);
            o = (T) dao.retrieve(id.toString());
        } catch (SQLException | ClassNotFoundException e) {
        } finally {
            Connexion.close();
        }
        return (T) o;
    }

    public List<T> findAll() throws SQLException {
        List<T> liste = new LinkedList();
        try {
            Class.forName(Config.DRIVER);
            Connection cnx = Connexion.getInstance();

            dao.setCnx(cnx);
            liste = dao.findAll();
//        } catch (SQLException ex) {

        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Connexion.close();
        }
        return liste;

    }

    public List<T> findRange(int[] range) {
        /*        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();*/
        List<T> liste = new LinkedList();
        return liste;
    }

    public int count() {
        /*     javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();*/
        return 0;
    }

}
