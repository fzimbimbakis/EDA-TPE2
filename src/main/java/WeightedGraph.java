import java.util.*;

public class WeightedGraph<T>{

    private final boolean isDirected;
    Map<T, Node> nodes;

    public WeightedGraph(boolean isDirected) {
        this.isDirected = isDirected;
        nodes = new HashMap<>();
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

    public int getCheapestPrice(T src, T dst, int maxEscalas){
        unmarkAllNodes();
        nodes.values().forEach(node -> node.cost = Integer.MAX_VALUE);

        PriorityQueue<PqNode> queue = new PriorityQueue<>();

        Node origen=nodes.get(src);
        Node destino=nodes.get(dst);

        if(origen==null || destino==null)
            return -1;

        origen.cost=0;
        // Agrego el src
        queue.add(new PqNode(origen, origen.cost, -1));
        while (!queue.isEmpty()){
            PqNode pqNode = queue.remove();

            if(pqNode.node.equals(destino))     //si ya lo encuentro, su precio es el minimo posible
                return destino.cost;

            if(pqNode.node.marked)
                continue;

            pqNode.node.mark();
            // Recorro las aristas
            if((pqNode.escalas+1)<=maxEscalas){
                for (Edge edge : pqNode.node.edges) {
                    // Actualizo cost
                    edge.targetNode.cost = Math.min(pqNode.cost + edge.weight,edge.targetNode.cost);
                    // Agrego a la queue
                    queue.add(new PqNode(edge.targetNode, edge.targetNode.cost, pqNode.escalas+1));
                }
            }
        }
        return -1;
    }

    class Node {
        T label;
        Set<Edge> edges;
        boolean marked;
        int cost;

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
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(node.label,label);
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
        int escalas;

        public PqNode(Node node, int cost, int escalas) {
            this.node = node;
            this.cost = cost;
            this.escalas = escalas;
        }

        @Override
        public int compareTo(PqNode other) {
            return Integer.compare(cost, other.cost);
        }
    }
}
