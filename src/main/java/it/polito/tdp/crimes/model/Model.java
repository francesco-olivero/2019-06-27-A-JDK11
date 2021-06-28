package it.polito.tdp.crimes.model;

import java.util.List;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	EventsDao dao;

	public Model() {
		dao = new EventsDao();
	}
	
	public List<String> getCategoryEvents(){
		return dao.getCategoryEvents();
	}
	
}
