package it.polito.tdp.crimes.db;

import it.polito.tdp.crimes.model.Arco;
import it.polito.tdp.crimes.model.Event;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		for(Arco e : dao.getArchi("arson", 2014))
			System.out.println(e);
	}

}
