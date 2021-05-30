

public class Ejercicio1 {

  static final int ORIGEN=0,
          DESTINO=1,
          PRECIO=2;

  static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    WeightedGraph<Integer> graph=new WeightedGraph<>(true,k);

    for(int i=0; i < n ; i++){//cargo nodos
      graph.addNode(i);
    }

    for (int i=0; i < flights.length; i++){//cargo aristas
        graph.addEdge(flights[i][ORIGEN],flights[i][DESTINO],flights[i][PRECIO]);
    }

    return graph.getCheapestPrice(src,dst,k);
  }

}
