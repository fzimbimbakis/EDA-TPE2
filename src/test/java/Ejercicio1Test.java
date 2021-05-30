import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Ejercicio1Test {

  @Test
  void performanceTest() {
    int[][] flights = new int[][] {
        {11,12,74},
        {1,8,91},
        {4,6,13},
        {7,6,39},
        {5,12,8},
        {0,12,54},
        {8,4,32},
        {0,11,4},
        {4,0,91},
        {11,7,64},
        {6,3,88},
        {8,5,80},
        {11,10,91},
        {10,0,60},
        {8,7,92},
        {12,6,78},
        {6,2,8},
        {4,3,54},
        {3,11,76},
        {3,12,23},
        {11,6,79},
        {6,12,36},
        {2,11,100},
        {2,5,49},
        {7,0,17},
        {5,8,95},
        {3,9,98},
        {8,10,61},
        {2,12,38},
        {5,7,58},
        {9,4,37},
        {8,6,79},
        {9,0,1},
        {2,3,12},
        {7,10,7},
        {12,10,52},
        {7,2,68},
        {12,2,100},
        {6,9,53},
        {7,4,90},
        {0,5,43},
        {11,2,52},
        {11,8,50},
        {12,4,38},
        {7,9,94},
        {2,7,38},
        {3,7,88},
        {9,12,20},
        {12,0,26},
        {10,5,38},
        {12,8,50},
        {0,2,77},
        {11,0,13},
        {9,10,76},
        {2,6,67},
        {5,6,34},
        {9,7,62},
        {5,3,6}
    };
    long startTime = System.nanoTime();
    int ans = Ejercicio1.findCheapestPrice(13, flights, 10, 1, 10);
    long totalTimeMs = (System.nanoTime() - startTime) / 1000000;
    System.out.println("Total time: " + totalTimeMs + "ms");
    assertEquals(-1, ans);
    assertTrue(totalTimeMs < 150);
  }


  @Test
  void testSinEscala(){
    int[][] flights = new int[][]{
            {0, 1, 20},
            {1, 2, 30},
            {0, 2, 100}
    };
    int ans=Ejercicio1.findCheapestPrice(3,flights,0,2,0);
    assertEquals(100, ans);

    ans=Ejercicio1.findCheapestPrice(3,flights,0,2,1);
    assertEquals(50,ans);
    }

    @Test
    void testMaxEscala(){
      int[][] flights = new int[][]{
              {0, 1, 100},
              {2, 1, 200},
              {0, 2, 10},
              {2,3,10},
              {3,4,50},
              {4,1,20}
      };

      int ans=Ejercicio1.findCheapestPrice(5,flights,0,1,3);
      assertEquals(90, ans);

      ans=Ejercicio1.findCheapestPrice(5,flights,0,1,2);
      assertEquals(100, ans);

    }

    @Test
    void test(){
      int[][] flights = new int[][]{
              {0,2,4},
              {0,1,2},
              {1,4,6},
              {2,4,2},
              {2,6,1},
              {3,4,1},
              {4,6,2},
              {5,4,4}
      };

//      assertEquals(-1,Ejercicio1.findCheapestPrice(7,flights,4,2,0));
//      assertEquals(Ejercicio1.findCheapestPrice(7,flights,,));
//      assertEquals(Ejercicio1.findCheapestPrice(7,flights,,));
//      assertEquals(Ejercicio1.findCheapestPrice(7,flights,,));
//      assertEquals(Ejercicio1.findCheapestPrice(7,flights,,));
    }



}
