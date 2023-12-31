package fr.umontpellier.etu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class Phase {

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
        int nbRes=30;
        int finInterval = 204;
        int debutInterval = 202;
        int pas = 2;
        int tailleTabNbTuples = ((finInterval-debutInterval)/2)+1;
        int[] tabNbTuples = new int[tailleTabNbTuples];
        int j=0;
        for (int i=finInterval; i>=debutInterval;i=i-pas) {
            tabNbTuples[j]=i;
            j++;
        }
        ArrayList<String> filesName = new ArrayList<>(tabNbTuples.length);
        for (int i :
                tabNbTuples) {
            String s = String.format("benchmark/set35_17_249_i_30/csp%d.txt",i);
            filesName.add(s);
        }

        System.out.println("indice;nbVariables;tailleDom;nbConstraints;nbTuples;durete;nbReussite;nbTO;nbEchec;total");
        int i = 0;
        for (String unNomDeFichier :
                filesName) {
            Model[] tabModel = readModels(unNomDeFichier, nbRes);

            int nbVariables, tailleDomaine, nbConstraints;
            nbVariables = tabModel[0].getVars().length;
            tailleDomaine = tabModel[0].getVars()[0].asIntVar().getDomainSize(); // toutes les variables ont un domaine de même taille
            nbConstraints = tabModel[0].getNbCstrs(); // toutes les contraintes ont les mêmes cardinaux


            double tailleDomaineCarre = tailleDomaine * tailleDomaine;
            double durete = (tailleDomaineCarre - tabNbTuples[i])/tailleDomaineCarre;
            int[] res = calcPourcentageBench(tabModel, "30s");


            String s = String.format(Locale.US,"%d;%d;%d;%d;%d;%f;%d;%d;%d;%d",i,nbVariables,tailleDomaine,nbConstraints,tabNbTuples[i],durete,res[0],res[1],res[2],res[3]);
            System.out.println(s);
            i++;
        }
    }

    /**
     * Lis un fichier et nous retourne les CSP contenus à l'intérieur de ce fichier
     *
     * @param fileName
     * @param n nombre de réseau CSP présent dans le fichier
     * @return
     */
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

    /**
     * Calcul pour trouver une solution à un model
     * @param unModel
     * @return 0 si une solution a été trouvé, 2 si il n'y pas de solution, 1 si il n'a pas trouvé de solutio avant le temps limite
     */
    public static int modelHasASolution(Model unModel){
//        boolean foundASolution = false;
        int code = -1;
        Solver solver = unModel.getSolver();

        if(solver.solve()){ // true if at least one solution has been found
//            foundASolution = true;
            code = 0;
        } else if (solver.isStopCriterionMet()) {
//            System.out.println("The solver could not find a solution nor prove that none exists in the given limits");
            code = 1;
        } else {
//            System.out.println("The solver has proved the problem has no solution");
            code = 2;
        }

        return code;
    }

    /**
     * @param models tableau de modèle CSP avec les MÊMES PARAMÈTRES
     * @param duration le temps limit avant timeout
     * @return un tableau contenant le nombre de CSP résolut, le nombre de TO, le nombre de CSP insatisfiable et le nombre de CSP en tout
     */
    public static int[] calcPourcentageBench(Model[] models, String duration){
        int[] result = {0,0,0,models.length}; //nbReussite, nbTimeOut, nbEchec, total

        for (Model m :
                models) {
            m.getSolver().limitTime(duration);
            int code = modelHasASolution(m);
            switch (code) {
                case 0:
                    result[0]++;
                    break;
                case 1:
                    result[1]++;
                    break;
                case 2:
                    result[2]++;
                    break;
            }
        }

        return result;
    }

}
