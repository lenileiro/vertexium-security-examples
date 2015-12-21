package com.rgimmy.vertexiumsecurityexamples;

import org.vertexium.Authorizations;
import org.vertexium.Vertex;
import org.vertexium.Visibility;
import org.vertexium.inmemory.InMemoryAuthorizations;
import org.vertexium.inmemory.InMemoryGraph;
import org.vertexium.inmemory.InMemoryGraphConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Example2 {
    public static void main(String[] args) {
        new Example2().run();
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
        Vertex vertex = inMemoryGraph.addVertex("PersonVertex", visibility, higherLevelAuthorizations);
        vertex.setProperty("FirstName", "John", visibility, noAuthorizations);
        vertex.setProperty("LastName", "Johnson", visibility, noAuthorizations);
        vertex.setProperty("SSN", "123-45-6789", highLevelVisibility, higherLevelAuthorizations);
        inMemoryGraph.flush();

        System.out.println("=====reading vertex as regular user======");
        //read vertex as no authorization user
        Vertex readVertex = inMemoryGraph.getVertex("PersonVertex", noAuthorizations);
        System.out.println("ID: " + readVertex.getId());
        System.out.println("FirstName: " + readVertex.getPropertyValue("FirstName"));
        System.out.println("LastName: " + readVertex.getPropertyValue("LastName"));
        System.out.println("SSN: " + readVertex.getPropertyValue("SSN")); // this will be null

        System.out.println("=====reading vertex as elevated user======");

        //read vertex as elevated user
        readVertex = inMemoryGraph.getVertex("PersonVertex", higherLevelAuthorizations);
        System.out.println("ID: " + readVertex.getId());
        System.out.println("FirstName: " + readVertex.getPropertyValue("FirstName"));
        System.out.println("LastName: " + readVertex.getPropertyValue("LastName"));
        System.out.println("SSN: " + readVertex.getPropertyValue("SSN"));
    }
}
