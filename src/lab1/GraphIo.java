package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphIo {
  AdjGraph adjGraph;
  String fileUrl;

  public void read() throws FileNotFoundException {
    Scanner scan = new Scanner(new File(fileUrl), "UTF-8");
    StringBuilder builder = new StringBuilder();
    while (scan.hasNext()) {
      String str = scan.nextLine();
      str = str.replaceAll("[\\pP]", " ");
      str = str.replaceAll("[^a-zA-Z ]", "") + " ";
      str = str.toLowerCase();
      builder.append(str);
    }
    String newtext = builder.toString();
    System.out.println(newtext);
    scan.close();

    adjGraph = new AdjGraph();
    String pre = null;
    scan = new Scanner(newtext);
    while (scan.hasNext()) {
      String str = scan.next();
      System.out.println(str);
      adjGraph.insNode(str);
      if (pre != null) {
        adjGraph.updateEdge(pre, str);
      }
      pre = str;
    }
    scan.close();
  }

  public void showDirectedGraph(AdjGraph graph, int pattern, String str) {
    EdgeNode p;
    GraphViz gv = new GraphViz();
    gv.addln(gv.startGraph());
    String[] strSplit = str.split(" ");
    if (pattern == 2 && strSplit.length > 0) {

      for (int i = 1; i <= graph.getNumOfVertex(); i++) {
        if (graph.getVexData(i).equals(strSplit[0])) {
          gv.addln(graph.getVexData(i) + "[style=filled,fillcolor=green]" + ";");
        } else if (graph.getVexData(i).equals(strSplit[strSplit.length - 1])) {
          gv.addln(graph.getVexData(i) + "[style=filled,fillcolor=green]" + ";");
        } else {
          for (int j = 1; j < strSplit.length - 1; j++) {
            if (graph.getVexData(i).equals(strSplit[j])) {
              gv.addln(graph.getVexData(i) + "[style=filled,fillcolor=lightblue]" + ";");
              break;
            }
          }
        }
        for (p = graph.getFirstAdjvex(i); p != null; p = p.next) {
          if (str.contains(graph.getVexData(i) + " " + graph.getVexData(p.adjVex))) {
            gv.addln(graph.getVexData(i) + "->" + graph.getVexData(p.adjVex) + "[label=" + p.cost
                + ",color=blue,fontcolor=blue]" + ";");
          } else {
            gv.addln(graph.getVexData(i) + "->" + graph.getVexData(p.adjVex)
                      + "[label=" + p.cost + "];");
          }
        }
      }
    } else if (pattern == 1 && strSplit.length > 0) {
      for (int i = 1; i <= graph.getNumOfVertex(); i++) {
        int k = -1;
        if (graph.getVexData(i).equals(strSplit[0])) {
          k = 0;
          gv.addln(graph.getVexData(i) + "[style=filled,fillcolor=green]" + ";");
        } else if (graph.getVexData(i).equals(strSplit[strSplit.length - 1])) {
          gv.addln(graph.getVexData(i) + "[style=filled,fillcolor=green]" + ";");
        } else {
          for (int j = 1; j < strSplit.length - 1; j++) {
            if (graph.getVexData(i).equals(strSplit[j])) {
              k = j;
              gv.addln(graph.getVexData(i) + "[style=filled,fillcolor=lightblue]" + ";");
              break;
            }
          }
        }
        for (p = graph.getFirstAdjvex(i); p != null; p = p.next) {
          if (k >= 1 && graph.getVexData(p.adjVex).equals(strSplit[strSplit.length - 1])) {
            gv.addln(graph.getVexData(i) + "->" + graph.getVexData(p.adjVex) + "[label=" + p.cost
                + ",color=blue,fontcolor=blue]" + ";");
          } else if (k == 0) {
            for (int j = 1; j < strSplit.length - 1; j++) {
              if (graph.getVexData(p.adjVex).equals(strSplit[j])) {
                gv.addln(graph.getVexData(i) + "->" + graph.getVexData(p.adjVex)
                          + "[label=" + p.cost + ",color=blue,fontcolor=blue]" + ";");
                break;
              }
            }
          } else {
            gv.addln(graph.getVexData(i) + "->" + graph.getVexData(p.adjVex)
                      + "[label=" + p.cost + "];");
          }
        }
      }
    } else {
      for (int i = 1; i <= graph.getNumOfVertex(); i++) {
        for (p = graph.getFirstAdjvex(i); p != null; p = p.next) {
          gv.addln(graph.getVexData(i) + "->" + graph.getVexData(p.adjVex)
                    + "[label = " + p.cost + "];");
        }
      }
    }
    gv.addln(gv.endGraph());
    System.out.println(gv.getDotSource());
    String type = "gif";
    File out = new File(System.getProperty("user.home")
                        + "\\AppData\\Local\\Temp" + "\\out." + type); // Windows
    gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
  }
}
