package com.bayer.aoc2021.day12;

import com.bayer.aoc2021.Utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;

public class NodeMap {
    public static class Node {
        String name;
        boolean bigNode;
        List<Node> edges = new ArrayList<>();

        public Node(String name) {
            bigNode = Character.isUpperCase(name.charAt(0));
            this.name = name;
        }
    }

    Map<String, Node> nodeList = new HashMap<>();

    public Node getOrAdd(String name) {
        Node foundNode;
        if (nodeList.containsKey(name)) {
            foundNode = nodeList.get(name);
        } else {
            foundNode = new Node(name);
            nodeList.put(name, foundNode);
        }
        return foundNode;
    }

    public void addEdge(String nameA, String nameB) {
        Node nodeA = getOrAdd(nameA);
        Node nodeB = getOrAdd(nameB);
        nodeA.edges.add(nodeB);
        nodeB.edges.add(nodeA);
    }

    public void addEdges(List<String> edges) {
        for (String edge: edges) {
            String[] names = edge.split("-");
            addEdge(names[0], names[1]);
        }
    }

    private int countPathsHelper(List<Node> pathSoFar, BiFunction<List<Node>,Node, Boolean> validator) {
        Node fromNode = pathSoFar.get(pathSoFar.size() - 1);
        int paths = 0;
        for (Node toNode: fromNode.edges) {
            if (toNode.name.equals("end")) {
                paths +=1;
            } else if (validator.apply(pathSoFar, toNode)) {
                List<Node> nextPath = new ArrayList<>(pathSoFar);
                nextPath.add(toNode);
                paths += countPathsHelper(nextPath, validator);
            }
        }
        return paths;
    }

    public int countPaths() {
        return countPathsHelper(List.of(nodeList.get("start")),
                (pathSoFar, nextNode) -> nextNode.bigNode || !pathSoFar.contains(nextNode));
    }

    public int countPaths2() {
        return countPathsHelper(List.of(nodeList.get("start")), NodeMap::isValid2);
    }

    private static boolean isValid2(List<Node> pathSoFar, Node nextNode) {
        boolean isValid;
        if (nextNode.bigNode) isValid = true;
        else if (nextNode.name.equals("start")) isValid = false;
        else {
            boolean hasTwoSmallNodes = pathSoFar.stream().filter(n -> !n.bigNode).anyMatch(n -> hasTwo(pathSoFar, n));
            if (hasTwoSmallNodes) {
                isValid = !pathSoFar.contains(nextNode);
            } else isValid = true;
        }
        return isValid;
    }

    private static boolean hasTwo(List<Node> pathSoFar, Node node) {
        int count = 0;
        for (Node n: pathSoFar) {
            if (node == n) count++;
        }
        return count >= 2;
    }


    public static void main(String[] args) throws Exception {
        Path dataPath = new Utils().getLocalPath("day12");
        NodeMap map = new NodeMap();
        map.addEdges(Files.lines(dataPath).toList());
        System.out.println(map.countPaths());

        map = new NodeMap();
        map.addEdges(Files.lines(dataPath).toList());
        System.out.println(map.countPaths2());
    }
}
