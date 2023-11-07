package fr.umontpellier.etu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

import static java.lang.Math.pow;

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


    public static void main(String[] args){
        int nbRes=3000;
        int[] tabNbTuples = {4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 30, 32, 34, 36, 38, 40, 45, 50, 60, 70, 80};
        ArrayList<String> filesName = new ArrayList<>(tabNbTuples.length);
        for (int i :
                tabNbTuples) {
            String s = String.format("csp%d.txt",i);
            filesName.add(s);
        }

        System.out.println("indice;nbVariables;nbConstraints;nbTuples;nbRes;durete;%ayantaumoins1sol");
        int i = 0;
        for (String unNomDeFichier :
                filesName) {
            Model[] tabModel = readModels(unNomDeFichier, nbRes);

            int nbVariables, tailleDomaine, nbConstraints;
            nbVariables = tabModel[0].getVars().length;
            tailleDomaine = tabModel[0].getVars()[0].asIntVar().getDomainSize(); // toutes les variables ont un domaine de même taille
            nbConstraints = tabModel[0].getNbCstrs(); // toutes les contraintes ont les mêmes cardinaux


            double durete = (tailleDomaine*tailleDomaine - tabNbTuples[i])/tailleDomaine*tailleDomaine;
            double res = calcPourcentageBench(tabModel, "30s");


            String s = String.format(Locale.US,"%d;%d;%d;%d;%d;%f;%.4f",i,nbVariables,tailleDomaine,nbConstraints,tabNbTuples[i],durete,res);
            System.out.println(s);
            i++;
        }
    }

    public static Model[] readModels(String fileName, int n) {
        String ficName = fileName;
        int nbRes=n;
        Model[] tabModel = new Model[nbRes];
        try {

            BufferedReader readFile = new BufferedReader(new FileReader(ficName));
            for(int nb=1 ; nb<=nbRes; nb++) {
                Model model=lireReseau(readFile);
                if(model==null) {
//                    System.out.println("Problème de lecture de fichier !\n");
                    throw new RuntimeException("Problème de lecture de fichier !\n");
                }
//            while(model.getSolver().solve()) {}
                tabModel[nb-1] = model;

//            System.out.println("Réseau lu "+nb+" ("+model.getSolver().getSolutionCount()+" nb solutions) :\n"+/*model+*/"\n\n");
            }
        } catch (Exception e) {
            System.err.println("Problème dans readModeles");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        assert tabModel.length!=0;

        return tabModel;
    }

    //au moins 1 solution
    public static boolean modelHasASolution(Model unModel){
        boolean foundASolution = false;
        Solver solver = unModel.getSolver();

        if(solver.solve()){ // true if at least one solution has been found
            foundASolution = true;
        } else if (solver.isStopCriterionMet()) {
//            System.out.println("The solver could not find a solution nor prove that none exists in the given limits");
        } else {
//            System.out.println("The solver has proved the problem has no solution");
        }

        return foundASolution;
    }

    public static double calcPourcentageBench(Model[] models, String duration){
        double nbReseauTotal = models.length;
        double nbReseauQuiPossedeAuMoinsUneSolution = 0;

        for (Model m :
                models) {
            m.getSolver().limitTime(duration);
            if (modelHasASolution(m)) {
                nbReseauQuiPossedeAuMoinsUneSolution++;
            }
        }

//        System.out.println(nbReseauQuiPossedeAuMoinsUneSolution +"/"+nbReseauTotal);
//
//        double a = nbReseauQuiPossedeAuMoinsUneSolution;
//        double b = nbReseauTotal;
//
//        System.out.println(a/b);
//
//        double c, d;
//        c = nbReseauQuiPossedeAuMoinsUneSolution;
//        d = nbReseauTotal;
//        System.out.println(c/d);

        return nbReseauQuiPossedeAuMoinsUneSolution/nbReseauTotal;
    }

}
