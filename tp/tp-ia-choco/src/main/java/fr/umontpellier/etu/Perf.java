package fr.umontpellier.etu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.maven.surefire.shared.lang3.function.TriFunction;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class Perf {

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


    public static final int NBRESEAU =2;
    public static void mainTony(String[] args){
        traiterDonnees((tabModel,nbTuples,index) -> {
            int nbVariables = tabModel[0].getVars().length;
            int tailleDomaine = tabModel[0].getVars()[0].asIntVar().getDomainSize(); // toutes les variables ont un domaine de même taille
            int nbConstraints = tabModel[0].getNbCstrs(); // toutes les contraintes ont les mêmes cardinaux
            double durete = (tailleDomaine*tailleDomaine - nbTuples)/tailleDomaine*tailleDomaine;

            String[] res = Stream.of(calcEffectifsBench(tabModel,"30s"))
                    .map(String::valueOf).toArray(String[]::new);


            return Stream.of(String.format(Locale.US,"%d;%d;%d;%d;%d;%f;",index,nbVariables,tailleDomaine,nbConstraints,nbTuples,durete,
                    String.join(";",res)),"\n");
        },"indice;nbVariables;nbConstraints;nbTuples;NBRESEAU;durete;%ayantaumoins1sol");
    }
    public static void main(String[] args){
        traiterDonnees((tabModels,nbTuples,index) -> Stream.of(tabModels).map(
                model -> {
                    Solver s = model.getSolver();
                    s.limitTime(30000);
                    TimeInfo timeInfo = temps(() -> (! s.solve()) && s.hasReachedLimit(), 30000000,5);
                    return String.valueOf(index) + ";" + nbTuples + ";" + String.join (";",timeInfo.toStrings()) + "\n";
                }
        ),"indice;nbTuples;tempsReel;tempsCPU;tempsUser;tempsSys;nbTimeOut");
    }
    public static void traiterDonnees(TriFunction<Model[],Integer,Integer,Stream<String>> toPrint,String index){
        int finInterval = 211;
        int debutInterval = 178;
        int pas = 3;
        int tailleTabNbTuples = ((finInterval-debutInterval)/pas)+1;
        int[] tabNbTuples = new int[tailleTabNbTuples];
        int j=0;
        for (int i=finInterval; i>=debutInterval;i=i-pas) {
            tabNbTuples[j]=i;
            j++;
        }
        ArrayList<String> filesName = new ArrayList<>(tabNbTuples.length);
        for (int i :
                tabNbTuples) {
            String s = String.format("set35_17_249_i_30/csp%d.txt",i);
            filesName.add(s);
        }

        System.out.println(index);
        int i = 0;
        for (String unNomDeFichier :
                filesName) {
            Model[] tabModel = readModels(unNomDeFichier, NBRESEAU);

            toPrint.apply(tabModel,tabNbTuples[i],i).forEach(System.out::print);
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
    //au moins 1 solution
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

    public static int[] calcEffectifsBench(Model[] models, String duration){
        int nbReseauTotal = models.length;
        int nbReseauQuiPossedeAuMoinsUneSolution = 0;
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
//            if (modelHasASolution(m)) {
//                nbReseauQuiPossedeAuMoinsUneSolution++;
//            }
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

        return result;//nbReseauQuiPossedeAuMoinsUneSolution/nbReseauTotal;
    }

    public static TimeInfo temps(Supplier<Boolean> isTimedOut, long maxTime, int nbTests){
        final long [] userTime = new long[nbTests];
        final long [] cpuTime = new long[nbTests];
        final long [] realTime = new long[nbTests];
        boolean timedOutOnce = false;
        double realTimeMax = 0;
        double realTimeMin = maxTime;
        int iMax = 0;
        int iMin = 0;
        for (int i = 0; i < nbTests; i++) {
            ThreadMXBean thread = ManagementFactory.getThreadMXBean();
            long startTime = System.nanoTime();
            long startCpuTime = thread.getCurrentThreadCpuTime();
            long startUserTime = thread.getCurrentThreadUserTime();
            boolean timedOut = isTimedOut.get() ;
            userTime [i] = thread.getCurrentThreadUserTime() - startUserTime;
            cpuTime[i] = thread.getCurrentThreadCpuTime() - startCpuTime;
            realTime[i] = System.nanoTime() - startTime;
            if (realTime[i] > realTimeMax){
                iMax = i;
                realTimeMax = realTime[i];
            }
            if (realTime[i] < realTimeMin){
                iMin = i;
                realTimeMin = realTime[i];
            }

            //convention : si on est timed out pour la seconde fois on prends comme temps moyens les derniers temps
            if (timedOut && timedOutOnce) return new TimeInfo(userTime[i], cpuTime[i], realTime[i], 2);

            timedOutOnce = timedOut;
        }
        double userSum = 0;
        double cpuSum = 0;
        double realSum = 0;
        for (int i = 0 ; i < nbTests; i++){
            if (i == iMax || i == iMin) continue;
            userSum += userTime[i];
            cpuSum += cpuTime[i];
            realSum += realTime[i];


        }
        int n = nbTests - 2;
        return new TimeInfo(userSum/n, cpuSum/n, realSum/n, timedOutOnce ? 1 : 0);

    }


}
