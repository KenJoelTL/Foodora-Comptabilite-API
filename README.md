# Foodora-Comptabilite-API



## GET : Obtenir des données

/comptabilite/transaction

/comptabilite/itemTransaction

### GET : Retrouver un donnée particulière
/comptabilite/transaction/id

/comptabilite/itemtransaction/id
_______________________________________________________________
## POST : Ajouter des données
/comptabilite/transaction

**Données dans le corps de la requête :**

 Transaction : { int idSuccursale ,
                 int idClient,
                 String date,
                 ArrayList itemsCommandes,
                 double sousTotal,
                 double pourboireCoursier }
_______________________________________________________________

/comptabilite/itemtransaction

**Données dans le corps de la requête :** 

 ItemTransaction : { String code,
                     int idTransaction,
                     int quantite }
_______________________________________________________________
## DELETE : Suppression de données
/comptabilite/transaction/id

/comptabilite/itemtransaction/id
_______________________________________________________________

## PUT : Modification de données 
/comptabilite/transaction/id

**Données dans le corps de la requête :** 

id : int,

Transaction : { int idSuccursale ,
                 int idClient,
                 String date,
                 double sousTotal,
                 double pourboireCoursier }
_______________________________________________________________
/comptabilite/itemTransaction/id

**Données dans le corps de la requête :** 

 id : int,
 
 ItemTransaction : { String code,
                     int idTransaction,
                     int quantite }
