/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import edu.eci.cinema.filter.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */

public class InMemoryCinemaPersistence implements CinemaPersitence{
	 @Autowired
	 Filter fil=null;
	 public void setFil(Filter fil) {
		 this.fil=fil;
	 }
	 public Filter getFil() {
		 return fil;
	 }
    
    private final Map<String,Cinema> cinemas=new HashMap<>();

    public InMemoryCinemaPersistence() {
        //load stub data
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("SuperHeroes Movie2","Action"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        Cinema c=new Cinema("cinemaX",functions);
        cinemas.put("cinemaX", c);
    }    

    @Override
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException, CinemaPersistenceException {
    	List<CinemaFunction> functions;
       
        functions=getCinema(cinema).getFunctions();
        for(CinemaFunction cf:functions) {
        	if(cf.getMovie().getName().equals(movieName) && cf.getDate().equals(date)) {
        		cf.buyTicket(row, col);
        		return;
        	}
        }
        throw new CinemaPersistenceException("No existe una pelicula con el nombre "+movieName+" en el cinema "+cinema);
        
    }

    @Override
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) throws CinemaPersistenceException {
        Cinema c=getCinema(cinema);
        List<CinemaFunction> functions=new ArrayList<CinemaFunction>();
        for(CinemaFunction cf: c.getFunctions()) {
        	if(cf.getDate().equals(date)) {
        		functions.add(cf);
        	}
        }
        return functions;
    }

    @Override
    public void saveCinema(Cinema c) throws CinemaPersistenceException {
        if (cinemas.containsKey(c.getName())){
            throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
        }
        else{
            cinemas.put(c.getName(),c);
        }   
    }

    @Override
    public Cinema getCinema(String name) throws CinemaPersistenceException {
    	if(cinemas.containsKey(name)) return cinemas.get(name);
    	throw new CinemaPersistenceException("No existe el cinema "+name);
    }

	@Override
	public Set<Cinema> getAllCinemas() {
		Set<Cinema> cs=new HashSet<Cinema>();
		for(Cinema c: cinemas.values()) {
			cs.add(c);
		}
		return cs;
		
	}
	public List<Movie> filtrar(String cinema,String date,String category){
		return fil.filter(this, cinema, date, category);
	}

}
