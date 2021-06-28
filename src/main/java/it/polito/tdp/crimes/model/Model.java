package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	EventsDao dao;
	Graph<String, DefaultWeightedEdge> grafo;
	List<String> vertexList;
	
	// variabili per la ricorsione
		 List<String> percorsoBest ;
		 double pesoBest;

	public Model() {
		dao = new EventsDao();
		percorsoBest = new ArrayList<String>();
		pesoBest=Integer.MAX_VALUE;
	}
	
	public List<String> getCategoryEvents(){
		return dao.getCategoryEvents();
	}
	
	public String creaGrafo(String categoria, int anno) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(categoria, anno));
		this.vertexList=this.dao.getVertici(categoria, anno);
		
		// aggiungo gli archi
		for (Arco a : this.dao.getArchi(categoria, anno) ) {
			Graphs.addEdge(this.grafo, a.getTipoReato1(), a.getTipoReato2(), a.getPeso());
		}
		
		return String.format("Grafo creato con %d vertici e %d archi\n",
				this.grafo.vertexSet().size(),
				this.grafo.edgeSet().size()) ;
	}
	
	public List<String> archiPesoMax() {
		double max = Integer.MIN_VALUE;
		List<String> list = new ArrayList<String>();
		for (DefaultWeightedEdge dwe : this.grafo.edgeSet()) {
			if (this.grafo.getEdgeWeight(dwe)>max) {
				max = this.grafo.getEdgeWeight(dwe);
				list.clear();
				list.add(""+this.grafo.getEdgeSource(dwe)+" "+this.grafo.getEdgeTarget(dwe)+ " "+this.grafo.getEdgeWeight(dwe));
			}else if (this.grafo.getEdgeWeight(dwe)==max) {
				list.add(""+this.grafo.getEdgeSource(dwe)+" "+this.grafo.getEdgeTarget(dwe)+ " "+this.grafo.getEdgeWeight(dwe));
			}
		}
		return list;
	}

	public Collection<DefaultWeightedEdge> getAllEdges() {
		return this.grafo.edgeSet();
	}
	
	public List<String> percorsoMigliore(String partenza, String arrivo) {
		this.percorsoBest = null ;
		this.pesoBest = 0;
		
		List<String> parziale = new ArrayList<String>() ;
		double pesoParziale = 0;
		parziale.add(partenza) ;
		
		cerca(parziale, 1, arrivo, pesoParziale) ;
		
		return this.percorsoBest ;
	}
	
	private void cerca(List<String> parziale, int livello, String arrivo, double pesoParziale) {
		
		String ultimo = parziale.get(parziale.size()-1) ;
		
		// caso terminale: ho trovato l'arrivo
		if(ultimo.equals(arrivo) && containsAllVertex(parziale)) {
			if(this.percorsoBest==null) {
				this.percorsoBest = new ArrayList<>(parziale) ;
				this.pesoBest=pesoParziale;
				return ;
			} else if( pesoParziale < this.pesoBest ) {
				this.pesoBest=pesoParziale;
				this.percorsoBest = new ArrayList<>(parziale) ;
				return ;
			} else {
				return ;
			}
		}
			
			
			// generazione dei percorsi
			// cerca i successori di 'ultimo'
			for(DefaultWeightedEdge e: this.grafo.edgesOf(ultimo)) {
				String prossimo = Graphs.getOppositeVertex(this.grafo, e, ultimo) ;
					
					
				if(!parziale.contains(prossimo)) { // evita i cicli
					pesoParziale+=this.grafo.getEdgeWeight(e);
					parziale.add(prossimo);
					cerca(parziale, livello + 1, arrivo, pesoParziale);
					parziale.remove(parziale.size()-1) ;
				}
			}	
		}

	private boolean containsAllVertex(List<String> parziale) {
		for (String s: vertexList) {
			if (!parziale.contains(s))
				return false;
		}
		return true;
	}

	public Graph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public double getPesoBest() {
		return pesoBest;
	}
	
	
	
}
