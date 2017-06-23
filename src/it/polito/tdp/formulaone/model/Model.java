package it.polito.tdp.formulaone.model;


import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;
//import it.polito.tdp.formulaone.model.Driver;

public class Model {

	private FormulaOneDAO dao ;
	private List <Season> stagioni ;
	private List<Race> gare ;
	private Map <Integer, Drivers> drivers ;
	private SimpleDirectedWeightedGraph<Drivers, DefaultWeightedEdge> grafo ;
	private List<Drivers> usati;
	private List<Drivers> best ;
	private List<Drivers> parziale ;
	private int sconfittaB;
	
	
	public Model(){
		dao = new FormulaOneDAO() ;
		this.grafo = null ;
	}
	public List<Season> getSeasons() {
		if(stagioni == null){
			stagioni = new ArrayList<Season> () ;
			stagioni = dao.getAllSeasons() ;
		}
		return this.stagioni ;
	}
	public Drivers getMigliore(Season s) {
		this.grafo = new SimpleDirectedWeightedGraph<Drivers, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		this.drivers = new TreeMap <Integer, Drivers> () ;
		this.gare = dao.getRacesYear(Integer.parseInt(s.getYear().toString()));
		
		for(Race r : gare){
			for(Drivers d :dao.getDriversForRace(r)){
				drivers.put(d.getDriverId(), d);
				}
			}
		Graphs.addAllVertices(grafo, drivers.values()) ;
		for(Drivers d1 : drivers.values()){
			int vittorie = 0 ;
			for(Drivers d2 : drivers.values()){
				if(!d1.equals(d2)){
					vittorie = dao.getVittorie(d1, d2, Integer.parseInt(s.getYear().toString()));
					Graphs.addEdgeWithVertices(grafo, d1, d2, vittorie) ;
					d2.addVincente(d1);
//					System.out.println(vittorie);
					
			
				}
			}
	
		}
		System.out.println(grafo); 
		Drivers best = null ;
		int max = 0 ;
		int uscenti = 0 ;
		int entranti = 0 ;
		
		for(Drivers d : grafo.vertexSet()){
			for(DefaultWeightedEdge a : grafo.outgoingEdgesOf(d)){
				uscenti+=grafo.getEdgeWeight(a);
			}
			for(DefaultWeightedEdge a : grafo.incomingEdgesOf(d)){
				entranti+=grafo.getEdgeWeight(a);
			}
			
			
			int vittorie = uscenti - entranti  ;
			System.out.println(vittorie);
			if(vittorie>max){
				max = vittorie;
				best = d ;
			}
		}
		
		return best;
	}
	public List<Drivers> getDreamTeam(int k ) {
		
		if(grafo== null)
			return null ;
		// se non funziona mettere best e parziale fuori
		best = new ArrayList<Drivers> () ;
		parziale = new ArrayList<Drivers> () ;
		usati = new ArrayList<Drivers> () ; 
		sconfittaB = 0 ;
		
		this.recursive(best, parziale, k) ;
		
		
		return best;
	}
	private void recursive(List<Drivers> best, List<Drivers> parziale, int k) {
	
		// condzione di terminazione
		if(parziale.size()==k){
			if(best.size()==0){
				best.addAll(parziale);
				sconfittaB = this.getTassoSconfitta(best) ;
				return;}
			int sconfittaP = this.getTassoSconfitta(parziale) ;
			if(sconfittaP<sconfittaB){
				best.clear();
				best.addAll(parziale) ;
				sconfittaB = this.getTassoSconfitta(best) ;
				return;
			}
			return ;
		}
		
		for(Drivers d : this.grafo.vertexSet()){
			if(!usati.contains(d)){
				usati.add(d) ;
				parziale.add(d) ;
				this.recursive(best, parziale, k);
				parziale.remove(d) ;
			}
		}
		
		
		
	}
	private int getTassoSconfitta(List<Drivers> parziale) {
		int sconfitte = 0 ;
		
		for(Drivers perdente : parziale){
			for(Drivers vincente : grafo.vertexSet()){
				if(!parziale.contains(vincente)){
					sconfitte+=grafo.getAllEdges(vincente, perdente).size() ;
				}
//					sconfitte+=perdente.getSconfitte(vincente) ;
			}
			
		}
		
		return sconfitte ;
	}


}
