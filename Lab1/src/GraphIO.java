// 3.5 by h
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphIO {
    AdjGraph G;
    String fileUrl;
    public void read() throws FileNotFoundException {
        // read text from file
        Scanner scan = new Scanner(new File(fileUrl));
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
        // generate graph
        G = new AdjGraph();
        String pre = null;
        scan = new Scanner(newtext);
        while (scan.hasNext()) {
            String str = scan.next();
            System.out.println(str);
            G.insNode(str);
            if (pre != null) {
                G.updateEdge(pre, str);
            }
            pre = str;
        }
        /*// query Bridge Words
        String s = G.queryBridgeWords("new","and");
        System.out.println(s);
        // generate new text
        s = G.generateNewText("Seek to explore new and exciting synergies");
        System.out.println(s);
        // calculate Shortest Path
        System.out.print(G.calcShortestPath("to", "worlds") + " ");
        System.out.print(G.getDistanceOfPath("worlds"));
        System.out.print("\n");

        String[] paths = G.calcShortestPath("to");
        for (int i = 1; i <= G.getNumOfVertex(); i++) {
            if (i != G.getIndexOfVex("to")) {
                System.out.print(paths[i] + " ");
                System.out.print(G.getDistanceOfPath(G.getVexData(i)));
                System.out.print("\n");
            }

        }
        // random walk
        System.out.print(G.randomWalk());

        // show graph
        showDirectedGraph(G, G.calcShortestPath("to", "worlds"));*/
        scan.close();

    }

    public void showDirectedGraph(AdjGraph G, int pattern, String str) {
        EdgeNode p;
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        String[] strSplit = str.split(" ");
        if (pattern == 2 && strSplit.length > 0) {
            
            for (int i = 1; i <= G.getNumOfVertex(); i++) {          
                if (G.getVexData(i).equals(strSplit[0])) {     
                    gv.addln(G.getVexData(i) + "[style=filled,fillcolor=green]" + ";");
                }
                else if (G.getVexData(i).equals(strSplit[strSplit.length - 1])) {
                    gv.addln(G.getVexData(i) + "[style=filled,fillcolor=green]" + ";");
                }
                else{
                    for (int j=1;j<strSplit.length-1;j++)
                    {
                        if(G.getVexData(i).equals(strSplit[j])) {
                            gv.addln(G.getVexData(i) + "[style=filled,fillcolor=lightblue]" + ";");
                            break;
                        }
                    }
                } 
                for (p = G.getFirstAdjvex(i); p != null; p = p.next) {
                    if (str.indexOf(G.getVexData(i) + " " + G.getVexData(p.adjVex))!=-1) {
                        gv.addln(G.getVexData(i) + "->" + G.getVexData(p.adjVex) + "[label=" + p.cost
                                + ",color=blue,fontcolor=blue]" + ";");
                    } else {
                        gv.addln(G.getVexData(i) + "->" + G.getVexData(p.adjVex) + "[label=" + p.cost + "];");
                    }
                }
            }
        } 
        else if(pattern == 1 && strSplit.length > 0) { 
            for (int i = 1; i <= G.getNumOfVertex(); i++) { 
                int k = -1;
                if (G.getVexData(i).equals(strSplit[0])) {
                    k = 0;
                    gv.addln(G.getVexData(i) + "[style=filled,fillcolor=green]" + ";");
                }
                else if (G.getVexData(i).equals(strSplit[strSplit.length - 1])) {
                    gv.addln(G.getVexData(i) + "[style=filled,fillcolor=green]" + ";");
                }
                else{
                    for (int j=1;j<strSplit.length-1;j++)
                    {
                        if(G.getVexData(i).equals(strSplit[j])) {
                            k = j;
                            gv.addln(G.getVexData(i) + "[style=filled,fillcolor=lightblue]" + ";");
                            break;
                        }
                    }
                } 
                for (p = G.getFirstAdjvex(i); p != null; p = p.next) {
                    if (k>=1 && G.getVexData(p.adjVex).equals(strSplit[strSplit.length - 1])) {
                        gv.addln(G.getVexData(i) + "->" + G.getVexData(p.adjVex) + "[label=" + p.cost
                                + ",color=blue,fontcolor=blue]" + ";");
                    }
                    else if(k==0) {
                        for (int j=1;j<strSplit.length-1;j++)
                        {
                            if(G.getVexData(p.adjVex).equals(strSplit[j])) {
                                gv.addln(G.getVexData(i) + "->" + G.getVexData(p.adjVex) + "[label=" + p.cost
                                        + ",color=blue,fontcolor=blue]" + ";");
                                break;
                            }
                        }
                    }
                    else {
                        gv.addln(G.getVexData(i) + "->" + G.getVexData(p.adjVex) + "[label=" + p.cost + "];");
                    }
                }
            }
        }
        else{
            for (int i = 1; i <= G.getNumOfVertex(); i++) {
                for (p = G.getFirstAdjvex(i); p != null; p = p.next) {
                    gv.addln(G.getVexData(i) + "->" + G.getVexData(p.adjVex) + "[label = " + p.cost + "];");
                }
            }
        }
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());
        String type = "gif";
        File out = new File("C:\\Users\\zhang\\Desktop\\out." + type); // Windows
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }
}
