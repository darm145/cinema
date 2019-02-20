package edu.eci.arsw.cinema.filter.impl;

import java.util.ArrayList;
import java.util.List;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import edu.eci.cinema.filter.Filter;

public class AvailabilityFilter implements Filter{

	
	public List<Movie> filter(CinemaPersitence c,String cinema,String date,String seats) {
		ArrayList<Movie> filtered=new ArrayList<Movie>();
		int nSeats=Integer.parseInt(seats);
		Cinema actual = null;
		for(Cinema ci:c.getAllCinemas()) {
			if(ci.getName().equals(cinema)) {
				actual=ci;
				break;
			}
		}
		for(CinemaFunction fun:actual.getFunctions()) {
			if(fun.getDate().equals(date) && fun.getFreeSeats()>nSeats ) {
				filtered.add(fun.getMovie());
			}
		}
		return filtered;
	}

}
