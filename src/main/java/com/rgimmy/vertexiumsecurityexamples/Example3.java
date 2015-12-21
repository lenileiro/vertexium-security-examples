package com.rgimmy.vertexiumsecurityexamples;

import org.vertexium.Authorizations;
import org.vertexium.Vertex;
import org.vertexium.Visibility;
import org.vertexium.inmemory.InMemoryAuthorizations;
import org.vertexium.inmemory.InMemoryGraph;
import org.vertexium.inmemory.InMemoryGraphConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Example3 {
    public static void main(String[] args) {
        new Example3().run();
    }

    private void run() {
        Map<String, Object> config = new HashMap<>();
        InMemoryGraphConfiguration configuration = new InMemoryGraphConfiguration(config);
        InMemoryGraph inMemoryGraph = InMemoryGraph.create(configuration);

        String securityTag = "manager";
        Authorizations noAuthorizations = new InMemoryAuthorizations();
        Authorizations higherLevelAuthorizations = new InMemoryAuthorizations(securityTag);
        Visibility visibility = Visibility.EMPTY;
        Visibility highLevelVisibility = new Visibility(securityTag);

        //create vertex
        inMemoryGraph.addVertex("PersonVertex1", visibility, noAuthorizations);
        inMemoryGraph.addVertex("PersonVertex2", highLevelVisibility, higherLevelAuthorizations);
        inMemoryGraph.flush();

        System.out.println("=====reading all vertices as regular user======");
        Iterable<Vertex> vertices = inMemoryGraph.getVertices(noAuthorizations);
        for(Vertex vertex : vertices){
            System.out.println(vertex.getId());
        }

        System.out.println("=====reading all vertices as elevated user======");
        vertices = inMemoryGraph.getVertices(higherLevelAuthorizations);
        for(Vertex vertex : vertices){
            System.out.println(vertex.getId());
        }
    }
}
