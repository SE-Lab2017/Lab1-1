// 3.5 by h
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.lang.String;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Integer;

public class AdjGraph {
    private static final int MAX = 100;
    private VertexNode[] vexList;
    private int n;
    private int[] d, p;
    private boolean[] s;
    private int[] edgeNum;
    private HashMap<String, Integer> wordsMap;
    private StringBuilder randomPathBuilder;
    public AdjGraph() {
        vexList = null;
        n = 0;
        // e = 0;
        d = new int[MAX];
        s = new boolean[MAX];
        p = new int[MAX];
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
        return n;
    }

    public int getDistanceOfPath(String end) {
        return d[wordsMap.get(end)];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public boolean containsNode(String vexData) {
        return wordsMap.containsKey(vexData);
    }

    public void insNode(String vexData) {
        if (wordsMap.containsKey(vexData))
            return;
        else {
            VertexNode[] newVexList;
            newVexList = new VertexNode[n + 2];
            for (int i = 1; i <= n + 1; i++) {
                newVexList[i] = new VertexNode();
            }
            for (int i = 1; i <= n; i++) {
                newVexList[i].data = vexList[i].data;
                newVexList[i].firstEdge = vexList[i].firstEdge;

            }
            newVexList[n + 1].data = vexData;
            newVexList[n + 1].firstEdge = null;
            vexList = newVexList;
            wordsMap.put(vexData, n + 1);
            n++;
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
        if (p == null)
            setEdge(1, head, tail);
    }

    public String queryBridgeWords(String word1, String word2) {
        if (!wordsMap.containsKey(word1) || !wordsMap.containsKey(word2))
            return null;
        EdgeNode p, q;
        int mid;
        int start = wordsMap.get(word1);
        int end = wordsMap.get(word2);
        StringBuilder builder = new StringBuilder();
        for (p = vexList[start].firstEdge; p != null; p = p.next) {
            mid = p.adjVex;
            for (q = vexList[mid].firstEdge; q != null; q = q.next) {
                if (q.adjVex == end) {
                    builder.append(vexList[mid].data+" ");
                }
            }
        }
        String tmp = builder.toString();
        if (tmp.length() > 0) tmp = tmp.substring(0, tmp.length()-1);
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
                builder.append(preWord + " ");
                if (wordsMap.containsKey(preWord) && wordsMap.containsKey(curWord)) {
                    String bridgeWord = queryBridgeWords(preWord, curWord);
                    if (bridgeWord != null && !bridgeWord.equals("")) {
                        String[] tmp = bridgeWord.split(" ");
                        int i = (int) (Math.random() * tmp.length);
                        builder.append(tmp[i] + " ");
                    }      
                }
            }
            preWord = curWord;
        }
        builder.append(curWord);
        words.close();
        String newText = builder.toString();
        return newText;
    }

    public String calcShortestPath(String word1, String word2) {
        EdgeNode ptr;
        Stack<String> tmp = new Stack<String>();
        int i, sum = 0, k, w;
        int start = wordsMap.get(word1);
        int end = wordsMap.get(word2);
        for (i = 1; i <= n; i++) {
            d[i] = 0x7fffffff;
            s[i] = false;
            p[i] = start;
        }
        p[start] = 0;
        for (ptr = vexList[start].firstEdge; ptr != null; ptr = ptr.next) {
            d[ptr.adjVex] = ptr.cost;
        }
        s[start] = true;
        for (i = 1; i < n; i++) {
            w = getVexOfMinCost();
            if (w == end) {
                StringBuilder builder = new StringBuilder();
                k = w;
                do {
                    k = p[k];
                    tmp.push(vexList[k].data);
                } while (k != start);
                while (!tmp.isEmpty()) {
                    builder.append(tmp.pop() + " ");
                }
                builder.append(vexList[end].data);
                return builder.toString();
            }
            s[w] = true;
            for (ptr = vexList[w].firstEdge; ptr != null; ptr = ptr.next) {
                if (!s[ptr.adjVex]) {
                    sum = d[w] + ptr.cost;
                    if (sum < d[ptr.adjVex] && d[w] < 0x7fffffff) {
                        d[ptr.adjVex] = sum;
                        p[ptr.adjVex] = w;
                    }
                }
            }
        }
        return null;

    }

    public String[] calcShortestPath(String word) {
        EdgeNode ptr;
        // Stack<String> path = new Stack<String>();
        int i, sum = 0, k, w;
        int start = wordsMap.get(word);
        for (i = 1; i <= n; i++) {
            d[i] = 0x7fffffff;
            s[i] = false;
            p[i] = start;
        }
        p[start] = 0;
        for (ptr = vexList[start].firstEdge; ptr != null; ptr = ptr.next) {
            d[ptr.adjVex] = ptr.cost;
        }
        s[start] = true;
        for (i = 1; i < n; i++) {
            w = getVexOfMinCost();
            s[w] = true;
            for (ptr = vexList[w].firstEdge; ptr != null; ptr = ptr.next) {
                if (!s[ptr.adjVex]) {
                    sum = d[w] + ptr.cost;
                    if (sum < d[ptr.adjVex] && d[w] < 0x7fffffff) {
                        d[ptr.adjVex] = sum;
                        p[ptr.adjVex] = w;
                    }
                }
            }
        }
        String[] paths = new String[n + 1];
        StringBuilder[] builder = new StringBuilder[n + 1];
        for (i = 1; i <= n; i++) {
            paths[i] = new String();
            builder[i] = new StringBuilder();
        }
        for (i = 1; i <= n; i++) {
            if (i != start) {
                k = i;
                Stack<String> tmp = new Stack<String>();
                do {
                    k = p[k];
                    tmp.push(vexList[k].data);
                } while (k != start);

                while (!tmp.isEmpty()) {
                    builder[i].append(tmp.pop() + " ");
                }
                builder[i].append(vexList[i].data);
            }
            paths[i] = builder[i].toString();
        }
        return paths;

    }

    private int getVexOfMinCost() {
        int temp = 0x7fffffff, w = 0;
        for (int i = 1; i <= n; i++)
            if (!s[i] && d[i] < temp) {
                temp = d[i];
                w = i;
            }
        return w;
    }

    public String randomWalk() throws FileNotFoundException {
        for (int i = 1; i <= n; i++) {
            for (EdgeNode p = vexList[i].firstEdge; p != null; p = p.next) {
                p.walkFlag = false;
            }
        }
        randomPathBuilder.delete(0, randomPathBuilder.length());
        int start = (int) (Math.random() * n + 1);
        randomPathBuilder.append(vexList[start].data + " ");
        randomWalk(start);
        String randomPath = randomPathBuilder.toString();
        randomPath = randomPath.substring(0, randomPath.length() - 1);
        File f = new File("C:\\Users\\zhang\\Desktop\\randomwalk.txt");
        PrintStream fps = new PrintStream(f);
        fps.print(randomPath);
        fps.close();
        return randomPath;
    }

    private void randomWalk(int start) {
        EdgeNode p;
        int i = edgeNum[start], j = 1;
        if (i != 0) {
            int flag = (int) (Math.random() * i + 1);
            for (p = vexList[start].firstEdge; p != null; p = p.next) {
                if (j == flag) {
                    if (p.walkFlag) {
                        randomPathBuilder.append(vexList[p.adjVex].data + " ");
                        return;
                    }
                    p.walkFlag = true;
                    randomPathBuilder.append(vexList[p.adjVex].data + " ");
                    randomWalk(p.adjVex);
                }
                j++;
            }
        }
    }
}