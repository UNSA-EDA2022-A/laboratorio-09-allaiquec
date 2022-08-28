package com.example.project;

import java.util.ArrayList;

public class GraphAdjacentList implements Graph {

    private ArrayList<Vertex> vertices;
    private int numVertices;

    public GraphAdjacentList() {
        vertices = new ArrayList<>();
    }

    // Agregar una arista desde un vertice 'from' a un vertice 'to'
    public boolean addEdge(int from, int to) {
        Vertex fromV = null, toV = null;
        for (Vertex v : vertices) {
            if (from == v.data) { // verificando si 'from' existe
                fromV = v;
            } else if (to == v.data) { // verificando si 'to' existe
                toV = v;
            }
            if (fromV != null && toV != null) {
                break; // ambos nodos deben existir, si no paramos
            }
        }
        if (fromV == null) {
            fromV = new Vertex(from);
            vertices.add(fromV);
            numVertices++;
        }
        if (toV == null) {
            toV = new Vertex(to);
            vertices.add(toV);
            numVertices++;
        }        
        return fromV.addAdjacentVertex(toV);
    }

    // Eliminamos la arista del vertice 'from' al vertice 'to'
    public boolean removeEdge(int from, int to) {
        Vertex fromV = null;
        for (Vertex v : vertices) {
            if (from == v.data) {
                fromV = v;
                break;
            }
        }
        if (fromV == null) {
            return false;
        }
        return fromV.removeAdjacentVertex(to);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append("Vertex: ");
            sb.append(v.data);
            sb.append("\n");
            sb.append("Adjacent vertices: ");
            for (Vertex v2 : v.adjacentVertices) {
                sb.append(v2.data);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getNumEdges(){
        int count = 0;
        for(int i = 0; i < this.vertices.size(); i++){
            count += this.vertices.get(i).adjacentVertices.size();
        }
        return count;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public ArrayList<Vertex> depthFirstSearch(Vertex v) {
        return depthFirstSearch(v, new ArrayList<Vertex>());
    }

    public ArrayList<Vertex> depthFirstSearch(Vertex v, ArrayList<Vertex> visited) {
        visited.add(v);  
        for (Vertex vertex : v.adjacentVertices) {  
            if (!vertices.contains(vertex)) 
                depthFirstSearch(vertex, visited);
        }
        return visited;
    }

    public int countConnectedComponents(){
        int c = 1;
        
        ArrayList<Vertex> av = depthFirstSearch(vertices.get(0));   

        for (int i = 1; numVertices > 0 && i < numVertices; i++) {  
            if (!av.contains(vertices.get(i)))  
                c++; 
            
            av = depthFirstSearch(vertices.get(i));
        }
        return c;
    }

    public boolean removeVertex(int vertex){
    	 Vertex aux = null;
         
         for(Vertex v: vertices) {
             if(v.data == vertex ) 
                 aux = v;        
         }
         
         if(aux == null)
             return false;

         while(!aux.adjacentVertices.isEmpty())
             removeEdge(vertex, aux.adjacentVertices.get(0).data);
         
         vertices.remove(aux);
         
         numVertices--;
         
         return true;
    }
    
    public static void main(String args[]) {
        GraphAdjacentList graph = new GraphAdjacentList();
        graph.addEdge(1, 2);
        graph.addEdge(1, 5);
        graph.addEdge(2, 5);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 1);        
        System.out.println(graph);
    }
}
