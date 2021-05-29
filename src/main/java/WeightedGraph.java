import java.util.*;

public class WeightedGraph<T>{

    private final boolean isDirected;
    Map<T, Node> nodes;
    int maxDistnace;

    public WeightedGraph(boolean isDirected, int maxDistnace) {
        this.isDirected = isDirected;
        nodes = new HashMap<>();
        this.maxDistnace=maxDistnace;
    }

    void addNode(T label) {
        nodes.putIfAbsent(label, new Node(label));
    }

    void addEdge(T label1, T label2, int weight) {
        Node node1 = nodes.get(label1);
        Node node2 = nodes.get(label2);
        if (node1 == null || node2 == null) {
            return;
        }
        node1.edges.add(new Edge(node2, weight));
        if (!isDirected) {
            node2.edges.add(new Edge(node1, weight));
        }
    }

    private void unmarkAllNodes() {
        nodes.values().forEach(Node::unmark);
    }

    public void printDijkstra(T startingLabel) {
        unmarkAllNodes();
        nodes.values().forEach(node -> node.cost = Integer.MAX_VALUE);

        PriorityQueue<PqNode> queue = new PriorityQueue<>();
        queue.add(new PqNode(nodes.get(startingLabel), 0));

        while (!queue.isEmpty()) {
            PqNode pqNode = queue.remove();
            if (pqNode.node.marked) continue;
            pqNode.node.mark();
            System.out.println(pqNode.node.label + ": " + pqNode.cost);

            for (Edge edge : pqNode.node.edges) {
                int targetNodeCost = pqNode.cost + edge.weight;
                if (targetNodeCost < edge.targetNode.cost) {
                    edge.targetNode.cost = targetNodeCost;
                    queue.add(new PqNode(edge.targetNode, targetNodeCost));
                }
            }
        }
    }

    class Node {
        T label;
        Set<Edge> edges;
        boolean marked;
        int cost;
        int distance;

        public Node(T label) {
            this.label = label;
            edges = new HashSet<>();
        }

        void mark() {
            marked = true;
        }

        void unmark() {
            marked = false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(label, node.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }
    }

    class Edge {
        Node targetNode;
        int weight;

        public Edge(Node targetNode, int weight) {
            this.targetNode = targetNode;
            this.weight = weight;
        }
    }

    class PqNode implements Comparable<PqNode> {
        Node node;
        int cost;

        public PqNode(Node node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compareTo(PqNode other) {
            return Integer.compare(cost, other.cost);
        }
    }
}
