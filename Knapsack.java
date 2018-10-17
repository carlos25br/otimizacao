import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Knapsack {

 private int n, W; 
 private int w[], v[]; 
 private String nome[];
 private int V[][]; 
 private FileWriter fileWriter;
 private BufferedWriter bufferedWriter;

 public Boolean parseInput(String input, int i, Boolean writeToFile) throws IOException {
  String parsedLine[] = input.split(",");
  if (parsedLine.length < 3) {
   System.out.println("Formato incorreto");
   return false;
  }
  nome[i] = parsedLine[0];
  try {
   // checking valid integer using parseInt() method 
   v[i] = Integer.parseInt(parsedLine[1]);
  } catch (NumberFormatException e) {
   System.out.println(parsedLine[1] + " nao e um valor valido");
   return false;
  }
  try {
   // checking valid integer using parseInt() method 
   w[i] = Integer.parseInt(parsedLine[2]);
  } catch (NumberFormatException e) {
   System.out.println(parsedLine[2] + " nao e um valor valido");
   return false;
  }
  if (writeToFile) {
   bufferedWriter.write(input);
   bufferedWriter.newLine();
  }
  return true;
 }

 private void readInput() throws IOException {
  Scanner sc = new Scanner(System.in);
  System.out.print("Quantidade de produtos : ");
  n = sc.nextInt(); //number of items
  System.out.print("Tempo disponivel em horas : ");
  W = sc.nextInt(); //capacity of knapsack
  w = new int[n];
  v = new int[n];
  nome = new String[n];
  System.out.println("Entre com o nome, valor e tempo de producao dos items, separado por virgulas : ");
  sc.nextLine();
  for (int i = 0; i < n; i++) {
   System.out.println("Item: " + (i + 1));
   String parsedLine = sc.nextLine();
   while (!parseInput(parsedLine, i, true)) {
    parsedLine = sc.nextLine();
   }
  }
  bufferedWriter.close();
 }

 private void openFileForInput(String inputFile, String weight) throws IOException {
  W = weight.equals("") ? 20 : Integer.parseInt(weight);
  FileReader fileReader = new FileReader(inputFile);
  String line = null;
  ArrayList < String > readStrings = new ArrayList < String > ();
  BufferedReader bufferedReader = new BufferedReader(fileReader);
  while ((line = bufferedReader.readLine()) != null) {
   readStrings.add(line);
  }
  n = readStrings.size();
  w = new int[n];
  v = new int[n];
  nome = new String[n];
  for (int i = 0; i < n; i++) {
   parseInput(readStrings.get(i), i, false);
  }
 }

 private void initialize(String inputFile, String weight) throws IOException {
  if (inputFile.equals("")) {
   try {
    fileWriter = new FileWriter("savedItems.txt");
    bufferedWriter = new BufferedWriter(fileWriter);
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
  }
  if (inputFile.equals("")) {
   readInput();
  } else {
   openFileForInput(inputFile, weight);
  }
  V = new int[n + 1][W + 1]; //initializing the table to hold results
  for (int i = 0; i <= W; i++) V[0][i] = 0;
 }

 /**
  * Computes the result
  */
 public void knapsack() {
  //table for backtracking to get the items chosen
  int x[][] = new int[n + 1][W + 1];
  //filling tables
  for (int i = 1; i <= n; i++)
   for (int j = 0; j <= W; j++)
    if ((w[i - 1] <= j) && (v[i - 1] + V[i - 1][j - w[i - 1]] > V[i - 1][j])) {
     V[i][j] = v[i - 1] + V[i - 1][j - w[i - 1]];
     x[i][j] = 1;
    }
  else {
   V[i][j] = V[i - 1][j];
   x[i][j] = 0;
  }
  //backtracking
  System.out.printf("Items Chosen\n%5s%7s%7s\n", "Item", "Tempo", "Valor");
  int K = W;
  for (int i = n; i >= 1; i--)
   if (x[i][K] == 1) {
    System.out.printf("%5s%7d%7d\n", nome[i - 1], w[i - 1], v[i - 1]);
    K -= w[i - 1];
   }
  System.out.println("Lucro máximo : " + V[n][W]);
 }

 public static void main(String[] args) throws IOException {
  Knapsack obj = new Knapsack();
  String inputFile = "";
  String weight = "";
  if (args.length > 0) {
   inputFile = args[0];
   if (args.length >= 2) {
    weight = args[1];
   }
  }
  obj.initialize(inputFile, weight);
  obj.knapsack();
 }
}