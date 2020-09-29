package model;

import controller.DB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class IssueBookDaoTest {

    @Test
    public void checkBookTrue() {
        BookDao.save("test", "test", "test", "test", 5, 3);
        assertTrue(IssueBookDao.checkBook("test"));
        // CLEANUP
    }

    @Test
    public void checkBookFalse() {
        BookDao.save("test", "test", "test", "test", 5, 3);
        assertFalse(IssueBookDao.checkBook("fake"));
    }

    @Test
    public void save() {
        // CREATE A NEW BOOK FIRST
        BookDao.save("test", "test", "test", "test", 5, 3);
        // INSERT A RECORD
        IssueBookDao.save("test", 123, "tanjum", "tanjum");

        try{
            Connection con= DB.getConnection();
            PreparedStatement ps=con.prepareStatement("select * from issuebooks WHERE bookcallno='test'", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=ps.executeQuery();
            int i = 0;
            while(rs.next()) {
                i++;
            }
            // ASSERT THAT ONE ROW HAS BEEN RETURNED
            assertEquals(1, i);
            con.close();
        }catch(Exception e){
            // FAIL THE TEST
            assertTrue(false);
            System.out.println(e);
            cleanup();
        }
    }

    @Test
    public void updatebook() {
        // CREATE A NEW BOOK FIRST
        BookDao.save("test", "test", "test", "test", 5, 3);
        // INSERT A RECORD
        IssueBookDao.save("test", 123, "tanjum", "tanjum");
        // IssueBookDao.save() runs IssueBookDao.updateBook() internally
        try{
            Connection con= DB.getConnection();
            PreparedStatement ps=con.prepareStatement("select quantity, issued from books WHERE callno='test'", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=ps.executeQuery();

            int quantity = 0;
            int issued = 0;

            while(rs.next()) {
                quantity = rs.getInt("quantity");
                issued = rs.getInt("issued");
            }
            // ASSERT THAT QUANTITY HAS DECREASED
            assertEquals(4, quantity);
            // ASSERT THAT ISSUED HAS INCREASED
            assertEquals(4, issued);

            con.close();
        }catch(Exception e){
            // FAIL THE TEST
            assertTrue(false);
            System.out.println(e);
        }

    }

    @Before
    @After
    public void cleanup() {
        try{
            Connection con= DB.getConnection();
            PreparedStatement ps=con.prepareStatement("DELETE FROM books WHERE callno=?");
            ps.setString(1,"test");
            ps.executeUpdate();

            con.close();
        }catch(Exception e){System.out.println(e);}

        try{
            Connection con= DB.getConnection();
            PreparedStatement ps=con.prepareStatement("DELETE FROM issuebooks WHERE bookcallno=?");
            ps.setString(1,"test");
            ps.executeUpdate();

            con.close();
        }catch(Exception e){System.out.println(e);}
    }
}