/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foodora.comptabilite.modele.DAO;

import com.foodora.comptabilite.modele.ItemTransaction;
import com.foodora.comptabilite.modele.Transaction;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class TransactionDAO extends DAO<Transaction> {

    @Override
    public boolean create(Transaction x) {

        System.out.println("creaaaaateeeeeeeeeeeeeeeeee");

        String req = "INSERT INTO transaction (`ID_SUCCURSALE` , `ID_CLIENT`, `SOUS_TOTAL`, `POURBOIRE_COURSIER`) VALUES "
                + "(?,?,?,?)";
        System.out.println("test6");
        PreparedStatement paramStm = null;
        try {
            System.out.println("test7");
            paramStm = cnx.prepareStatement(req);

            System.out.println("test8");
            paramStm.setInt(1, x.getIdSuccursale());
            paramStm.setInt(2, x.getIdClient());
            paramStm.setDouble(3, x.getSousTotal());
            paramStm.setDouble(4, x.getPourboireCoursier());
            int n = paramStm.executeUpdate();
            System.out.println("test9");
            
            //Créer les items de la transaction courrante
            ItemTransactionDAO itDao = new ItemTransactionDAO();
            itDao.setCnx(cnx);
            for (ItemTransaction itemTransaction : x.getItemsCommande()) {
                itDao.create(itemTransaction);
            }
            
            
            if (n > 0) {
                paramStm.close();
                //stm.close();
                return true;
            }
        } catch (SQLException exp) {
        } finally {
            try {
                if (paramStm != null) {
                    paramStm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TransactionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }

    @Override
    public Transaction retrieve(int id) {
        String req = "SELECT * FROM transaction WHERE `ID_TRANSACTION` = ?";

        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, id);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if (resultat.next()) {

                Transaction t = new Transaction();
                t.setId(resultat.getInt("ID_TRANSACTION"));
                t.setIdSuccursale(resultat.getInt("ID_SUCCURSALE"));
                t.setIdClient(resultat.getInt("ID_CLIENT"));
                t.setDate(resultat.getString("DATE_TRANSACTION"));
                t.setSousTotal(resultat.getDouble("SOUS_TOTAL"));
                t.setPourboireCoursier(resultat.getDouble("POURBOIRE_COURSIER"));
                
                //Recherche les items de la transaction courrante
                ItemTransactionDAO itDao = new ItemTransactionDAO();
                itDao.setCnx(cnx);
                t.setItemsCommande((ArrayList<ItemTransaction>) itDao.findByIdTransaction(t.getId()));
                
                resultat.close();
                paramStm.close();
                return t;

            }
            resultat.close();
            paramStm.close();
            return null;

        } catch (SQLException exp) {
        } finally {
            try {
                if (paramStm != null) {
                    paramStm.close();
                }
            } catch (SQLException exp) {
            } catch (Exception e) {
            }
        }

        return null;
    }

    @Override
    public Transaction retrieve(String id) {
        try {
            return this.retrieve(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean update(Transaction x) {
        String req = "UPDATE transaction SET ID_CLIENT = ?, ID_SUCCURSALE = ?,"
                + "DATE_TRANSACTION = ?, SOUS_TOTAL = ?,"
                + " POURBOIRE_COURSIER = ? WHERE ID_TRANSACTION = ?";

        PreparedStatement paramStm = null;
        try {
            paramStm = cnx.prepareStatement(req);

            if (x.getDate() != null && !"".equals(x.getDate().trim())
                    && x.getSousTotal() > 0 && x.getIdClient() > 0
                    && x.getIdSuccursale() > 0 && x.getPourboireCoursier() >= 0) {
                paramStm.setInt(1, (x.getIdClient()));
                paramStm.setInt(2, (x.getIdSuccursale()));
                paramStm.setString(3, x.getDate());
                paramStm.setDouble(4, x.getSousTotal());
                paramStm.setDouble(5, x.getPourboireCoursier());
                paramStm.setInt(6, x.getId());

                int nbLignesAffectees = paramStm.executeUpdate();

                if (nbLignesAffectees > 0) {
                    paramStm.close();
                    return true;
                }
            }
            return false;
        } catch (SQLException exp) {
            System.out.println(exp.getMessage());
        } finally {
            try {
                if (paramStm != null) {
                    paramStm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TransactionDAO.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        }
        return false;

    }

    @Override
    public boolean delete(Transaction x) {
        String req = "DELETE FROM transaction WHERE `ID_TRANSACTION` = ?";
        PreparedStatement paramStm = null;
        try {
            paramStm = cnx.prepareStatement(req);
            paramStm.setInt(1, x.getId());
            int nbLignesAffectees = paramStm.executeUpdate();
            if (nbLignesAffectees > 0) {
                paramStm.close();
                System.out.println("test6");
                return true;

            }
            return false;
        } catch (SQLException exp) {
        } catch (Exception exp) {
        } finally {
            try {
                if (paramStm != null) {
                    paramStm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TransactionDAO.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }

    @Override
    public List<Transaction> findAll() {

        List<Transaction> listeTransaction = new LinkedList<>();
        Statement stm;
        try {
            stm = cnx.createStatement();
            ResultSet r = stm.executeQuery("SELECT * FROM transaction");
            while (r.next()) {
                Transaction t = new Transaction();
                t.setId(r.getInt("ID_TRANSACTION"));
                t.setIdSuccursale(r.getInt("ID_SUCCURSALE"));
                t.setIdClient(r.getInt("ID_CLIENT"));
                t.setDate(r.getString("DATE_TRANSACTION"));
                t.setSousTotal(r.getDouble("SOUS_TOTAL"));
                t.setPourboireCoursier(r.getDouble("POURBOIRE_COURSIER"));
                
                //Recherche les items de la transaction courrante
                ItemTransactionDAO itDao = new ItemTransactionDAO();
                itDao.setCnx(cnx);
                t.setItemsCommande((ArrayList<ItemTransaction>) itDao.findByIdTransaction(t.getId()));
                
                listeTransaction.add(t);
            }

            r.close();
            stm.close();
        } catch (SQLException exp) {
            System.out.println(exp);
        }
        return listeTransaction;
    }

}
