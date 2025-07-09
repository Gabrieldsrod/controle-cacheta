package com.gabrieldsrod.controlecacheta.db.dao.impl;

import com.gabrieldsrod.controlecacheta.db.Database;
import com.gabrieldsrod.controlecacheta.db.DatabaseException;
import com.gabrieldsrod.controlecacheta.db.dao.TableDao;
import com.gabrieldsrod.controlecacheta.entities.EntityInstantiator;
import com.gabrieldsrod.controlecacheta.entities.Table;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TableDaoJDBC implements TableDao {

    private Connection conn;

    public TableDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createTable(Table table) {
        PreparedStatement st = null;
        String sql = "INSERT INTO 'Table' DEFAULT VALUES ";
        try {
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rsTable = st.getGeneratedKeys();
                if (rsTable.next()) {
                    int id = rsTable.getInt(1);
                    table.setTableNumber(id);
                }
                Database.closeResultSet(rsTable);
            }
            else  {
                throw new DatabaseException("Error while inserting Table.");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public void updateTableStatus(int id, String status) {
        PreparedStatement st = null;

        String sql = "UPDATE 'Table' SET status = ? WHERE table_id = ?";

        try {
            st = conn.prepareStatement(sql);

            st.setString(1, status);
            st.setInt(2, id);

            int rowAffected = st.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Changed table " + id + " status");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public void updateTableStartTime(int id, LocalDateTime startTime) {
        PreparedStatement st = null;

        String sql = "Update 'Table' SET start_time = ? WHERE table_id = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, startTime.toString());
            st.setInt(2,id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
        }
    }

    @Override
    public void deleteTableById(int id) {
        PreparedStatement st = null;
        String sql = "DELETE FROM 'Table' WHERE 'Table'.table_id = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            if  (rowsAffected > 0) {
                System.out.println("Table " + id + " has been deleted successfully.");
            } else {
                System.out.println("No table found with id " + id + ".");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeStatement(st);
        }

    }

    @Override
    public Table getTableById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM 'Table' WHERE table_id = ?";
        try {
            st = conn.prepareStatement(sql);

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Table table = EntityInstantiator.instantiateTable(rs);
                return table;
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
    }

    @Override
    public List<Table> getTableByStatus(String status) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Table> tables = new ArrayList<>();

        String sql = "SELECT * FROM 'Table' WHERE status = ?";
        try {
            st = conn.prepareStatement(sql);
            st.setString(1, status);
            rs = st.executeQuery();

            while (rs.next()) {
              Table table = EntityInstantiator.instantiateTable(rs);
              tables.add(table);
            }
            return tables;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
    }

    @Override
    public List<Table> getAllTables() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Table> tables = new ArrayList<>();

        String sql = "SELECT * FROM 'Table'";
        try {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Table table = EntityInstantiator.instantiateTable(rs);
                tables.add(table);
            }
            return tables;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
