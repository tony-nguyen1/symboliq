package reine;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class nReine {
    public static void main(String args[]) {
        Model model = new Model("Zebre");

        int n = 5;
        IntVar[] t = model.intVarArray("x",n,1,n);
        // crée un tableau de n variables entières de domaine [1,n]


        //all dif
//      int[][] tEq= new int[n][2];
//      IntStream.range(0,n).mapToObj(i -> tEq[i] = new int[]{i,i});
//      Toutes les reines sur des colonnes différentes
        model.allDifferent(t).post();

        // Toutes les reines ne sont pas sur la même colonnes
//        for (IntVar aIntVar :
//                t) {
//            for (IntVar bIntVar :
//                    t) {
//                if (!aIntVar.equals(bIntVar)) {
//                    // différent de 5 (la taille du plateau)
////                    model.or(model.distance(aIntVar,bIntVar,"<",5), model.distance(aIntVar,bIntVar,">",5)).post();
////                    model.arithm()
//                    model.distance(aIntVar,bIntVar,"!=",5).post();
//                    model.distance(aIntVar,bIntVar,"!=",10).post();
//                    model.distance(aIntVar,bIntVar,"!=",15).post();
//                    model.distance(aIntVar,bIntVar,"!=",20).post();
//                }
//            }
//        }

        // Toutes les reines sont sur des lignes différentes
        // OK par la modélisation choisit
        // Utiliser model.and
        // une variable libre
//        model.intVar(new int[]{0,1,2,3,4});
//        model.and(model.arithm(t[0],));

        // Toutes les reines ne sout pas sur la même diagonale
//        for (int a = 0; a < n; a++) {
//            for (int b = 0; b < n; b++) {
//                if (a!=b) {
//                    model.distance(t[a],t[b],"!=",);
//                }
//            }
//        }
        model.distance(t[0],t[1],"!=",1);
        model.distance(t[0],t[2],"!=",2);
        model.distance(t[0],t[3],"!=",3);
        model.distance(t[0],t[4],"!=",4);
//        for (int a=0; a<n; a++) {
            for (int b=0; b<n-1; b++) {
                System.out.println("0 " + (b+1) + " " + (b+1));
            }
//        }

        model.distance(t[1],t[0],"!=",1);
        model.distance(t[1],t[2],"!=",1);
        model.distance(t[1],t[3],"!=",2);
        model.distance(t[1],t[4],"!=",3);
        boolean monBool = false;
        for (int b=0; b<n;b++) {
            if (b == 1) { monBool = true; }

            if (b!=2) {
                if (monBool) {
                    System.out.println("1 " + b + " " + (b - 1));
                } else {
                    System.out.println("1 " + b + " " + (1-b) + " " + monBool);
                }
            }
        }

        model.distance(t[2],t[0],"!=",2);
        model.distance(t[2],t[1],"!=",1);
        model.distance(t[2],t[3],"!=",1);
        model.distance(t[2],t[4],"!=",2);
        monBool = false;
        System.out.println("t[2]");
        for (int b=0; b<n;b++) {
            if (b == 2) { monBool = true; }

            if (b!=2) {
                if (monBool) {
                    System.out.println("2 " + b + " " + (b-2));
                } else {
                    System.out.println("2 " + b + " " + (2-b) + " " + monBool);
                }
            }
        }

        for (IntVar aIntVar :
                t) {
            for (IntVar bIntVar :
                    t) {
                if (!aIntVar.equals(bIntVar)) {
                    model.distance(aIntVar,bIntVar,"!=", 0).post(); // FIXME
//                    model.distance(aIntVar,bIntVar,"!=", 6).post();
                }
            }
        }

        // Affichage du réseau de contraintes créé
        System.out.println("*** Réseau Initial ***");
        System.out.println(model);

//        if(model.getSolver().solve()) {
//            System.out.println("\n\n*** Première solution ***");
//            System.out.println(model);
//        }

//        System.out.println("\n\n*** Bilan ***");
//        model.getSolver().printStatistics();
    }
}
