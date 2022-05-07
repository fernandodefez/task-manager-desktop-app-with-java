package com.fernandodefez.app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public interface DAO<M, I> {

   int insert(M m);

   boolean update(M m);

   boolean delete(M m);

   M getById(I i);

   List<M> getAll();

   M map(ResultSet rs);

   void close(Connection conn, Statement stmt, ResultSet rs);
}
