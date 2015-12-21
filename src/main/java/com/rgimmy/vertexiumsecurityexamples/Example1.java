package com.rgimmy.vertexiumsecurityexamples;

import org.vertexium.Authorizations;
import org.vertexium.Vertex;
import org.vertexium.Visibility;
import org.vertexium.inmemory.InMemoryAuthorizations;
import org.vertexium.inmemory.InMemoryGraph;
import org.vertexium.inmemory.InMemoryGraphConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Example1 {
    public static void main(String[] args) {
        new Example1().run();
    }

    private void run() {
        Map<String, Object> config = new HashMap<>();
        InMemoryGraphConfiguration configuration = new InMemoryGraphConfiguration(config);
        InMemoryGraph inMemoryGraph = InMemoryGraph.create(configuration);
        Authorizations authorizations = new InMemoryAuthorizations();
        Visibility visibility = Visibility.EMPTY;

        //create vertex
        Vertex vertex = inMemoryGraph.addVertex("PersonVertex", visibility, authorizations);
        vertex.setProperty("FirstName", "John", visibility, authorizations);
        vertex.setProperty("LastName", "Johnson", visibility, authorizations);
        vertex.setProperty("SSN", "123-45-6789", visibility, authorizations);
        inMemoryGraph.flush();

        //read vertex
        Vertex readVertex = inMemoryGraph.getVertex("PersonVertex", authorizations);
        System.out.println("ID: " + readVertex.getId());
        System.out.println("FirstName: " + readVertex.getPropertyValue("FirstName"));
        System.out.println("LastName: " + readVertex.getPropertyValue("LastName"));
        System.out.println("SSN: " + readVertex.getPropertyValue("SSN"));
    }
}
