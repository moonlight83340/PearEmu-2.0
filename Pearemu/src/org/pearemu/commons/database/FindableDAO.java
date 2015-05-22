/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pearemu.commons.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class FindableDAO<T extends Model> extends DAO<T> {
    
    final protected PreparedStatement findStatement = Database.prepare("SELECT * FROM " + tableName() + " WHERE " + primaryKey() + " = ?");

    /**
     * find an element by his primary key
     *
     * @param pk
     * @return
     */
    public T find(int pk) {
        if (primaryKey().isEmpty()) {
            return null;
        }

        try {
            synchronized (findStatement) {
                findStatement.setInt(1, pk);
                ResultSet RS = findStatement.executeQuery();

                if (!RS.next()) {
                    System.out.println("Impossible de trouver la pk " + pk + " dans la table " + tableName());
                    return null;
                }

                return createByResultSet(RS);
            }
        } catch (SQLException e) {
            System.out.println("Chargement impossible !");
            e.printStackTrace();
            return null;
        }
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();

        ResultSet RS = Database.query("SELECT * FROM " + tableName());

        try {
            while (RS.next()) {
                list.add(createByResultSet(RS));
            }
        } catch (SQLException ex) {
        }

        return list;
    }
}
