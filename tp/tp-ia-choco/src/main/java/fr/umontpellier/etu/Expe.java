package fr.umontpellier.etu;

import java.io.BufferedReader;
import java.io.FileReader;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.search.SearchState;
import org.chocosolver.solver.variables.IntVar;

public class Expe {

    private static Model lireReseau(BufferedReader in) throws Exception{
        Model model = new Model("Expe");
        int nbVariables = Integer.parseInt(in.readLine());				// le nombre de variables
        int tailleDom = Integer.parseInt(in.readLine());				// la valeur max des domaines
        IntVar []var = model.intVarArray("x",nbVariables,0,tailleDom-1);
        int nbConstraints = Integer.parseInt(in.readLine());			// le nombre de contraintes binaires
        for(int k=1;k<=nbConstraints;k++) {
            String chaine[] = in.readLine().split(";");
            IntVar portee[] = new IntVar[]{var[Integer.parseInt(chaine[0])],var[Integer.parseInt(chaine[1])]};
            int nbTuples = Integer.parseInt(in.readLine());				// le nombre de tuples
            Tuples tuples = new Tuples(new int[][]{},true);
            for(int nb=1;nb<=nbTuples;nb++) {
                chaine = in.readLine().split(";");
                int t[] = new int[]{Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1])};
                tuples.add(t);
            }
            model.table(portee,tuples).post();
        }
        in.readLine();
        return model;
    }


    public static void main(String[] args) throws Exception{
        String ficName = "benchSatisf.txt";
        int nbRes=3;
        BufferedReader readFile = new BufferedReader(new FileReader(ficName));
        for(int nb=1 ; nb<=nbRes; nb++) {
            Model model=lireReseau(readFile);
            if(model==null) {
                System.out.println("Problème de lecture de fichier !\n");
                return;
            }
            while(model.getSolver().solve()) {}

            System.out.println("Réseau lu "+nb+" ("+model.getSolver().getSolutionCount()+" nb solutions) :\n"+/*model+*/"\n\n");
        }
        return;
    }

    //au moins 1 solution
    public boolean modelHasASolution(Solver solver) throws Exception {

        boolean foundASolution = false;

        if(solver.solve()){ // true if at least one solution has been found
            // do something, e.g. print out variable values
            foundASolution = true;
        } else
//        solver.getSearchState();
//        SearchState.

        if(solver.getSearchState()==SearchState.STOPPED){
            System.out.println("The solver could not find a solution nor prove that none exists in the given limits");
        }else if(solver.getSearchState()==SearchState.TERMINATED){
            System.out.println("The solver has proved the problem has no solution");
        } else {
            throw new Exception("");
        }



        return foundASolution;
    }

}
