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

    public int getCheapestPrice(T src, T dst, int k){
        unmarkAllNodes();
        nodes.values().forEach(node -> node.cost = Integer.MAX_VALUE);

        PriorityQueue<PqNode> queue = new PriorityQueue<>();
        Node origen=nodes.get(src);
        Node destino=nodes.get(dst);
        if(origen==null || destino==null)
            return -1;
        // Agrego el src
        origen.cost=0;
        queue.add(new PqNode(origen, origen.cost, -1));
       // int maxEscalas = Math.min(nodes.size(), k);
//        for (int i = 0; i <= maxEscalas; i++) {
        while (!queue.isEmpty()){
            PqNode node = queue.remove();
            if(node.node.equals(destino))
                return destino.cost;
            if(node.node.marked)
                continue;
            // Recorro las aristas
            if((node.escalas+1)<=k){
                for (Edge edge : node.node.edges) {
                    // Actualizo
                    edge.targetNode.cost = Math.min(node.cost + edge.weight,edge.targetNode.cost);
                    // Marco y mando a queue2 si no esta marcado.
//                   if(!edge.targetNode.marked){
//                        edge.targetNode.mark();
                    queue.add(new PqNode(edge.targetNode, edge.targetNode.cost, node.escalas+1));
//                    }
                }
                //node.node.mark();
            }
            node.node.mark();
        }
//            queue1 = queue2;
//            queue2 = new PriorityQueue<>();
//        }
        return -1;
      //return destino.cost==Integer.MAX_VALUE? -1:destino.cost;
    }

    class Node {
        T label;
        Set<Edge> edges;
        boolean marked;
        int cost;
//        int distance;

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
