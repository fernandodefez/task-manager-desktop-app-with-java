package com.fernandodefez.app.dao;

import com.fernandodefez.app.dao.sqlite.SQLiteDAOFactory;
import java.sql.Connection;

// Abstract Class DAO Factory
public abstract class DAOFactory {

   // List of DAO types supported by the factory
   public static final Integer SQLITE = 1;
   //public static final Integer MYSQL = 2;

   // There will be a method for each DAO that can be
   // created. The concrete factories will have to
   // implement these methods.
   public abstract TaskDAO getTaskDAO();
   public abstract Connection connect();

   public static DAOFactory getInstance(Integer whichFactory){
      // If you want to change the database management system you must change the validation below and create
      // a new Factory
      if (whichFactory == 1) {
         return new SQLiteDAOFactory();
      }
      return null;
   }
}