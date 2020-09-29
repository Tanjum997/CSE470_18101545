package model;

import controller.DB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import static org.junit.Assert.*;

public class BookDaoTest {

    @Test
    public void saveTrue() {
        // CREATE A NEW BOOK FIRST
        BookDao.save("test", "test", "test", "test", 5, 5);
        assertTrue(IssueBookDao.checkBook("test"));
        // CLEANUP
        try{
            Connection con= DB.getConnection();
            PreparedStatement ps=con.prepareStatement("DELETE FROM books WHERE callno=?");
            ps.setString(1,"test");
            ps.executeUpdate();

            con.close();
        }catch(Exception e){
            // FAIL THE TEST
            assertTrue(false);
            System.out.println(e);
        }
    }

    @After
    @Before
    public void cleanup() {
        try{
            Connection con= DB.getConnection();
            PreparedStatement ps=con.prepareStatement("DELETE FROM books WHERE callno=?");
            ps.setString(1,"test");
            ps.executeUpdate();

            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

}