
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristian
 */
public class InMemoryPersistenceTest {
	ApplicationContext ac =new ClassPathXmlApplicationContext("applicationContext.xml");
    @Test
    public void saveNewAndLoadTest() throws CinemaPersistenceException{
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Movies Bogotá",functions);
        ipct.saveCinema(c);
        
        assertNotNull("Loading a previously stored cinema returned null.",ipct.getCinema(c.getName()));
        assertEquals("Loading a previously stored cinema returned a different cinema.",ipct.getCinema(c.getName()), c);
    }


    @Test
    public void saveExistingCinemaTest() {
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Movies Bogotá",functions);
        
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
        
        List<CinemaFunction> functions2= new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie 3","Action"),functionDate);
        CinemaFunction funct22 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
        functions.add(funct12);
        functions.add(funct22);
        Cinema c2=new Cinema("Movies Bogotá",functions2);
        try{
            ipct.saveCinema(c2);
            fail("An exception was expected after saving a second cinema with the same name");
        }
        catch (CinemaPersistenceException ex){
            
        }
    }
    @Test
    public void BuyTicketsTest1() {
    	
    	try {
			CinemaServices cn=ac.getBean(CinemaServices.class);
			cn.buyTicket(4, 4, "cinemaX", "2018-12-18 15:30", "The Night");
			cn.buyTicket(4, 4, "cinemaX", "2018-12-18 15:30", "The Night");
		}
		catch(CinemaException e){
			assertEquals(e.getMessage(),"Seat booked");
		} 
    	catch(CinemaPersistenceException e) {
    		
    	}
    }
    @Test
    public void BuyTicketsTest2() {
    	
    	try {
			CinemaServices cn=ac.getBean(CinemaServices.class);
			cn.buyTicket(4, 4, "cinemax", "2018-12-18 15:30", "The Night");
			
		}
		catch(CinemaException e){
			
		} 
    	catch(CinemaPersistenceException e) {
    		assertEquals(e.getMessage(),"No existe el cinema cinemax");
    	}
    }
    @Test
    public void BuyTicketsTest3() {
    	
    	try {
			CinemaServices cn=ac.getBean(CinemaServices.class);
			cn.buyTicket(4, 4, "cinemaX", "2018-12-18 15:30", "The night");
			
		}
		catch(CinemaException e){
			
		} 
    	catch(CinemaPersistenceException e) {
    		assertEquals(e.getMessage(),"No existe una pelicula con el nombre The night en el cinema cinemaX");
    	}
    }
    @Test
    public void GetFunctionByCinemaAndDateTest1() {
    	
    	try {
			CinemaServices cn=ac.getBean(CinemaServices.class);
			cn.getFunctionsbyCinemaAndDate("cinemax", "2018-12-18 15:30");
			
		}
    	catch(CinemaPersistenceException e) {
    		assertEquals(e.getMessage(),"No existe el cinema cinemax");
    	}
    }
    @Test
    public void GetFunctionByCinemaAndDateTest2() {
    	
    	try {
			CinemaServices cn=ac.getBean(CinemaServices.class);
			List<CinemaFunction> ar=cn.getFunctionsbyCinemaAndDate("cinemaX", "2018-12-18 15:30");
			assertEquals(2,ar.size() );
		}
    	catch(CinemaPersistenceException e) {
    		
    	}
    }
    @Test
    public void GetFunctionByCinemaAndDateTest3() {
    	
    	try {
			CinemaServices cn=ac.getBean(CinemaServices.class);
			List<CinemaFunction> ar=cn.getFunctionsbyCinemaAndDate("cinemaX", "2018-12-18 15:40");
			assertEquals(0,ar.size() );
		}
    	catch(CinemaPersistenceException e) {
    		
    	}
    }
}
