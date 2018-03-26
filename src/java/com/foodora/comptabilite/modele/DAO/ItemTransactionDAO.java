/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foodora.comptabilite.modele.DAO;

import com.foodora.comptabilite.modele.ItemTransaction;
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
public class ItemTransactionDAO extends DAO<ItemTransaction> {

    @Override
    public boolean create(ItemTransaction x) {

        String req = "INSERT INTO ITEM_TRANSACTION(`CODE` , `ID_TRANSACTION`, `QUANTITE`) VALUES (?,?,?)";
        PreparedStatement paramStm = null;
        try {
            System.out.println("test7");
            paramStm = cnx.prepareStatement(req);

            System.out.println("test8");
            paramStm.setString(1, x.getCode());
            paramStm.setInt(2, x.getIdTransaction());
            paramStm.setInt(3, x.getQuantite());
            int n = paramStm.executeUpdate();
            System.out.println("test9");
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
    public ItemTransaction retrieve(int id) {
        String req = "SELECT * FROM ITEM_TRANSACTION WHERE `ID` = ?";

        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, id);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if (resultat.next()) {

                ItemTransaction i = new ItemTransaction();
                i.setId(resultat.getInt("ID"));
                i.setCode(resultat.getString("CODE"));
                i.setIdTransaction(resultat.getInt("ID_TRANSACTION"));
                i.setQuantite(resultat.getInt("QUANTITE"));

                resultat.close();
                paramStm.close();
                return i;

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
    public ItemTransaction retrieve(String id) {
        try {
            return this.retrieve(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean update(ItemTransaction x) {

         String req = "UPDATE ITEM_TRANSACTION SET CODE = ?, ID_TRANSACTION = ?,"
                + "QUANTITE= ?";

        PreparedStatement paramStm = null;
        try {
            paramStm = cnx.prepareStatement(req);

            if (x.getCode()!= null && !"".equals(x.getCode().trim())
                    && x.getIdTransaction()> 0 && x.getQuantite()>= 0) 
            {
                paramStm.setString(1, (x.getCode()));
                paramStm.setInt(2, (x.getIdTransaction()));
                paramStm.setInt(3, x.getQuantite());


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
    public boolean delete(ItemTransaction x) {
        String req = "DELETE FROM ITEM_TRANSACTION WHERE `ID` = ?";
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
    public List<ItemTransaction> findAll() {
        List<ItemTransaction> listeItems = new LinkedList<>();
        Statement stm;
        try {
            stm = cnx.createStatement();
            ResultSet r = stm.executeQuery("SELECT * FROM ITEM_TRANSACTION");
            while (r.next()) {
                 ItemTransaction i = new ItemTransaction();
                 i.setId(r.getInt("ID"));
                 i.setCode(r.getString("CODE"));
                 i.setIdTransaction(r.getInt("ID_TRANSACTION"));
                 i.setQuantite(r.getInt("QUANTITE"));

                listeItems.add(i);
            }

            r.close();
            stm.close();
        } catch (SQLException exp) {
            System.out.println(exp);
        }
        return listeItems;
    }
    
    public List<ItemTransaction> findByIdTransaction(int id) {
       String req = "SELECT * FROM ITEM_TRANSACTION WHERE `ID_TRANSACTION` = ?";
        List<ItemTransaction> listeItems = new ArrayList<ItemTransaction>();

        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);

                paramStm.setInt(1, id);

                ResultSet resultat = paramStm.executeQuery();

                // On vérifie s'il y a un résultat    
                while(resultat.next()){

                    ItemTransaction i = new ItemTransaction();
                    i.setId(resultat.getInt("ID"));
                    i.setCode(resultat.getString("CODE"));
                    i.setIdTransaction(resultat.getInt("ID_TRANSACTION"));
                    i.setQuantite(resultat.getInt("QUANTITE"));
                   
                    listeItems.add(i);

                }
                resultat.close();
                paramStm.close();
                return listeItems;

        }
        catch (SQLException exp) {
        }
        finally {
            try{
                if (paramStm!=null)
                    paramStm.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
            }
        }        

        return listeItems;
    }

}
