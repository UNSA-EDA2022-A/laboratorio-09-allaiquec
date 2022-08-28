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
        return this.depthFirstSearch(v, new ArrayList<Vertex>());
    }

    public ArrayList<Vertex> depthFirstSearch(Vertex n, ArrayList<Vertex> visited) {
        visited.add(n);  
        for (Vertex vertex : n.adjacentVertices) { 
            if (!vertices.contains(vertex))  
                depthFirstSearch(vertex, visited);
        }
        return visited;
    }

    public int countConnectedComponents(){
        int c = 1;
        ArrayList<Vertex> aV = depthFirstSearch(vertices.get(0));   

        for (int i = 1; 0 < numVertices && i < numVertices; i++) {   
            if (!aV.contains(vertices.get(i)))   
                c++; 
            
            if (aV.contains(vertices.get(i))) 
            	continue;  
            
            aV = depthFirstSearch(vertices.get(i));
        }
        return c;
    }

    public boolean removeVertex(int vertex){
        int aux = 0;
        
        for (int i = 0; i < vertices.size(); i++) { 
            Vertex v = vertices.get(i);  

            if (vertex == vertices.get(i).data) 
            	aux = i; 

            for (int j = 0; j < v.adjacentVertices.size(); j++) {
                if (vertex == v.adjacentVertices.get(j).data) 
                    v.removeAdjacentVertex(vertex);
            }
        }
        
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
