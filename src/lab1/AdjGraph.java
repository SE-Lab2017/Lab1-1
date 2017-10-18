package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class AdjGraph {
  private static final int MAX = 100;
  private VertexNode[] vexList;
  private int vertexNum;
  private int[] dis;
  private int[] parentNode;
  private boolean[] isVisited;
  private int[] edgeNum;
  private HashMap<String, Integer> wordsMap;
  private StringBuilder randomPathBuilder;

  public AdjGraph() {
    vexList = null;
    vertexNum = 0;
    // e = 0;
    dis = new int[MAX];
    isVisited = new boolean[MAX];
    parentNode = new int[MAX];
    wordsMap = new HashMap<String, Integer>();
    randomPathBuilder = new StringBuilder();
    edgeNum = new int[MAX];
  }

  public EdgeNode getFirstAdjvex(int vex) {
    return vexList[vex].firstEdge;
  }

  public String getVexData(int vex) {
    return vexList[vex].data;
  }

  public int getIndexOfVex(String str) {
    return wordsMap.get(str);
  }

  public int getNumOfVertex() {
    return vertexNum;
  }

  public int getDistanceOfPath(String end) {
    return dis[wordsMap.get(end)];
  }

  public boolean isEmpty() {
    return vertexNum == 0;
  }

  public boolean containsNode(String vexData) {
    return wordsMap.containsKey(vexData);
  }

  public void insNode(String vexData) {
    if (!wordsMap.containsKey(vexData)) {
      VertexNode[] newVexList;
      newVexList = new VertexNode[vertexNum + 2];
      for (int i = 1; i <= vertexNum + 1; i++) {
        newVexList[i] = new VertexNode();
      }
      for (int i = 1; i <= vertexNum; i++) {
        newVexList[i].data = vexList[i].data;
        newVexList[i].firstEdge = vexList[i].firstEdge;

      }
      newVexList[vertexNum + 1].data = vexData;
      newVexList[vertexNum + 1].firstEdge = null;
      vexList = newVexList;
      wordsMap.put(vexData, vertexNum + 1);
      vertexNum++;
    }
  }

  public void setEdge(int weight, int head, int tail) {
    EdgeNode p;
    p = new EdgeNode();
    p.adjVex = tail;
    p.cost = weight;
    p.next = vexList[head].firstEdge;
    vexList[head].firstEdge = p;
    edgeNum[head]++;
    // e++;
  }

  public void updateEdge(String strHead, String strTail) {
    EdgeNode p;
    int head = wordsMap.get(strHead);
    int tail = wordsMap.get(strTail);
    for (p = vexList[head].firstEdge; p != null; p = p.next) {
      if (p.adjVex == tail) {
        p.cost++;
        break;
      }
    }
    if (p == null) {
      setEdge(1, head, tail);
    }
  }

  public String queryBridgeWords(String word1, String word2) {
    // query bridge words of two words
    if (!wordsMap.containsKey(word1) || !wordsMap.containsKey(word2)) {
      return null;
    }
    EdgeNode p;
    EdgeNode q;
    int mid;
    int start = wordsMap.get(word1);
    int end = wordsMap.get(word2);
    StringBuilder builder = new StringBuilder();
    for (p = vexList[start].firstEdge; p != null; p = p.next) {
      mid = p.adjVex;
      for (q = vexList[mid].firstEdge; q != null; q = q.next) {
        if (q.adjVex == end) {
          builder.append(vexList[mid].data).append(' ');
        }
      }
    }
    String tmp = builder.toString();
    if (tmp.length() > 0) {
      tmp = tmp.substring(0, tmp.length() - 1);
    }
    return tmp;
  }

  public String generateNewText(String inputText) {
    Scanner words = new Scanner(inputText);
    StringBuilder builder = new StringBuilder();
    String preWord = null;
    String curWord = null;
    while (words.hasNext()) {
      curWord = words.next().toLowerCase();
      if (preWord != null) {
        builder.append(preWord).append(' ');
        if (wordsMap.containsKey(preWord) && wordsMap.containsKey(curWord)) {
          String bridgeWord = queryBridgeWords(preWord, curWord);
          if (bridgeWord != null && !bridgeWord.equals("")) {
            String[] tmp = bridgeWord.split(" ");
            int i = (int) (Math.random() * tmp.length);
            builder.append(tmp[i]).append(' ');
          }
        }
      }
      preWord = curWord;
    }
    builder.append(curWord);
    words.close();
    return builder.toString();
  }

  public String calcShortestPath(String word1, String word2) {
    EdgeNode ptr;
    Stack<String> tmp = new Stack<String>();
    int i;
    int sum;
    int k;
    int w;
    int start = wordsMap.get(word1);
    int end = wordsMap.get(word2);
    for (i = 1; i <= vertexNum; i++) {
      dis[i] = 0x7fffffff;
      isVisited[i] = false;
      parentNode[i] = start;
    }
    parentNode[start] = 0;
    for (ptr = vexList[start].firstEdge; ptr != null; ptr = ptr.next) {
      dis[ptr.adjVex] = ptr.cost;
    }
    isVisited[start] = true;
    StringBuilder builder = new StringBuilder();
    for (i = 1; i < vertexNum; i++) {
      w = getVexOfMinCost();
      if (w == end) {
        builder.delete(0, builder.length());
        k = w;
        do {
          k = parentNode[k];
          tmp.push(vexList[k].data);
        } while (k != start);
        while (!tmp.isEmpty()) {
          builder.append(tmp.pop()).append(' ');
        }
        builder.append(vexList[end].data);
        return builder.toString();
      }
      isVisited[w] = true;
      for (ptr = vexList[w].firstEdge; ptr != null; ptr = ptr.next) {
        if (!isVisited[ptr.adjVex]) {
          sum = dis[w] + ptr.cost;
          if (sum < dis[ptr.adjVex] && dis[w] < 0x7fffffff) {
            dis[ptr.adjVex] = sum;
            parentNode[ptr.adjVex] = w;
          }
        }
      }
    }
    return null;

  }

  public String[] calcShortestPath(String word) {
    EdgeNode ptr;
    // Stack<String> path = new Stack<String>();
    int i;
    int sum;
    int k;
    int w;
    int start = wordsMap.get(word);
    for (i = 1; i <= vertexNum; i++) {
      dis[i] = 0x7fffffff;
      isVisited[i] = false;
      parentNode[i] = start;
    }
    parentNode[start] = 0;
    for (ptr = vexList[start].firstEdge; ptr != null; ptr = ptr.next) {
      dis[ptr.adjVex] = ptr.cost;
    }
    isVisited[start] = true;
    for (i = 1; i < vertexNum; i++) {
      w = getVexOfMinCost();
      isVisited[w] = true;
      for (ptr = vexList[w].firstEdge; ptr != null; ptr = ptr.next) {
        if (!isVisited[ptr.adjVex]) {
          sum = dis[w] + ptr.cost;
          if (sum < dis[ptr.adjVex] && dis[w] < 0x7fffffff) {
            dis[ptr.adjVex] = sum;
            parentNode[ptr.adjVex] = w;
          }
        }
      }
    }
    String[] paths = new String[vertexNum + 1];
    StringBuilder builder = new StringBuilder();
    for (i = 1; i <= vertexNum; i++) {
      builder.delete(0, builder.length());
      if (i != start) {
        k = i;
        Stack<String> tmp = new Stack<String>();
        do {
          k = parentNode[k];
          tmp.push(vexList[k].data);
        } while (k != start);

        while (!tmp.isEmpty()) {
          builder.append(tmp.pop()).append(' ');
        }
        builder.append(vexList[i].data);
      }
      paths[i] = builder.toString();
    }
    return paths;
  }

  private int getVexOfMinCost() {
    int temp = 0x7fffffff;
    int w = 0;
    for (int i = 1; i <= vertexNum; i++) {
      if (!isVisited[i] && dis[i] < temp) {
        temp = dis[i];
        w = i;
      }
    }
    return w;
  }

  public String randomWalk() throws FileNotFoundException {
    for (int i = 1; i <= vertexNum; i++) {
      for (EdgeNode p = vexList[i].firstEdge; p != null; p = p.next) {
        p.walkFlag = false;
      }
    }
    randomPathBuilder.delete(0, randomPathBuilder.length());
    int start = (int) (Math.random() * vertexNum + 1);
    randomPathBuilder.append(vexList[start].data).append(' ');
    randomWalk(start);
    String randomPath = randomPathBuilder.toString();
    randomPath = randomPath.substring(0, randomPath.length() - 1);
    File f = new File(System.getProperty("user.home")
        + "\\AppData\\Local\\Temp" + "randomwalk.txt");
    try (PrintStream fps = new PrintStream(f, "UTF-8")) {
      fps.print(randomPath);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return randomPath;
  }

  private void randomWalk(int start) {
    EdgeNode p;
    int i = edgeNum[start];
    int j = 1;
    if (i != 0) {
      int flag = (int) (Math.random() * i + 1);
      for (p = vexList[start].firstEdge; p != null; p = p.next) {
        if (j == flag) {
          if (p.walkFlag) {
            randomPathBuilder.append(vexList[p.adjVex].data).append(' ');
            return;
          }
          p.walkFlag = true;
          randomPathBuilder.append(vexList[p.adjVex].data).append(' ');
          randomWalk(p.adjVex);
        }
        j++;
      }
    }
  }
}
