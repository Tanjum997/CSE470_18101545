package model;

import controller.DB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class LibrarianDaoTest {

    @Test
    public void save() {
        LibrarianDao.save("test","test","test","test","test","test");
        try{
            Connection con= DB.getConnection();
            PreparedStatement ps=con.prepareStatement("select name, password, email, address, city, contact from librarian WHERE name='test'", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=ps.executeQuery();

            String name = "";
            String password = "";
            String email = "";
            String city = "";
            String contact = "";

            while(rs.next()) {
                name = rs.getString("name");
                password = rs.getString("password");
                email = rs.getString("email");
                city = rs.getString("city");
                contact = rs.getString("contact");
            }
            // ASSERT THAT EVERYTHING IS TEST
            assertEquals("test", name);
            assertEquals("test", password);
            assertEquals("test", email);
            assertEquals("test", city);
            assertEquals("test", contact);

            con.close();
        }catch(Exception e){
            // FAIL THE TEST
            assertTrue(false);
            System.out.println(e);
        }

    }

    @Test
    public void delete() {
        // INSERT DUMMY RECORD
        try{
            Connection con=DB.getConnection();
            PreparedStatement ps=con.prepareStatement("insert into librarian(id, name,password,email,address,city,contact) values(999, 'test', 'test', 'test', 'test', 'test', 'test')");
            ps.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }

        assertEquals(1, LibrarianDao.delete(999));

    }

    @Test
    public void validateTrue() {
        // INSERT DUMMY RECORD
        try{
            Connection con=DB.getConnection();
            PreparedStatement ps=con.prepareStatement("insert into librarian(id, name,password,email,address,city,contact) values(999, 'test', 'test', 'test', 'test', 'test', 'test')");
            ps.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        assertTrue(LibrarianDao.validate("test","test"));
    }

    @Test
    public void validateFalse() {
        // INSERT DUMMY RECORD
        try{
            Connection con=DB.getConnection();
            PreparedStatement ps=con.prepareStatement("insert into librarian(id, name,password,email,address,city,contact) values(999, 'test', 'test', 'test', 'test', 'test', 'test')");
            ps.executeUpdate();
            con.close();
        }catch(Exception e){
            System.out.println(e);
        }
        assertFalse(LibrarianDao.validate("test","fake"));
    }

    @Before
    @After
    public void cleanup() {
        try{
            Connection con= DB.getConnection();
            PreparedStatement ps=con.prepareStatement("delete from librarian where name='test'");
            ps.executeUpdate();
            con.close();
        }catch(Exception e){System.out.println(e);}
    }

}