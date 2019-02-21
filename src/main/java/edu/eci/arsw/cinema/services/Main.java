package edu.eci.arsw.cinema.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;

public class Main {

	public static void main(String[] args)   {
		ApplicationContext ac =new ClassPathXmlApplicationContext("applicationContext.xml");
		CinemaServices cn=ac.getBean(CinemaServices.class);
		System.out.println(cn.filtrar("cinemaX", "2018-12-18 15:30", "3"));
		
	}

}