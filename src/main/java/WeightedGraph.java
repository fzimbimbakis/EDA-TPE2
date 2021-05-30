import java.util.*;

public class WeightedGraph<T>{

    private final boolean isDirected;
    Map<T, Node> nodes;
    int maxDistance;

    public WeightedGraph(boolean isDirected, int maxDistance) {
        this.isDirected = isDirected;
        nodes = new HashMap<>();
        this.maxDistance=maxDistance;
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

    public int getCheapestPrice(T src, T dst, int k){
        unmarkAllNodes();
        nodes.values().forEach(node -> node.cost = Integer.MAX_VALUE);

        PriorityQueue<PqNode> queue1 = new PriorityQueue<>();
        PriorityQueue<PqNode> queue2 = new PriorityQueue<>();

        // Agrego el src
        queue1.add(new PqNode(nodes.get(src), 0));
        nodes.get(src).mark();

        for (int i = 0; i <= k; i++) {
            while (!queue1.isEmpty()){
                PqNode node = queue1.poll();
                // Recorro las aristas
                for (Edge edge : node.node.edges) {
                    // Actualizo
                    edge.targetNode.cost = Math.min(node.cost + edge.weight,edge.targetNode.cost);
                    // Marco y mando a queue2 si no esta marcado.
                    if(!edge.targetNode.marked){
                        edge.targetNode.mark();
                        queue2.add(new PqNode(edge.targetNode, edge.targetNode.cost));
                    }
                }
                queue1 = queue2;
                queue2 = new PriorityQueue<>();
            }
        }

      return nodes.get(dst).cost==Integer.MAX_VALUE? -1:nodes.get(dst).cost;

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
