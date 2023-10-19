import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.constraints.extension.Tuples;


public class ZebreIntension {

    public static void main(String[] args) {

        // Création du modele
        Model model = new Model("Zebre");


        // Création des variables
        IntVar blu = model.intVar("Blue", 1, 5);	// blu est une variable entière dont le nom est "Blue" est le domaine [1,5]
        IntVar gre = model.intVar("Green", 1, 5);
        IntVar ivo = model.intVar("Ivory", 1, 5);
        IntVar red = model.intVar("Red", 1, 5);
        IntVar yel = model.intVar("Yellow", 1, 5);

        IntVar eng = model.intVar("English", 1, 5);
        IntVar jap = model.intVar("Japanese", 1, 5);
        IntVar nor = model.intVar("Norwegian", 1, 5);
        IntVar spa = model.intVar("Spanish", 1, 5);
        IntVar ukr = model.intVar("Ukrainian", 1, 5);

        IntVar cof = model.intVar("Coffee", 1, 5);
        IntVar mil = model.intVar("Milk", 1, 5);
        IntVar ora = model.intVar("Orange Juice", 1, 5);
        IntVar tea = model.intVar("Tea", 1, 5);
        IntVar wat = model.intVar("Water", 1, 5);

        IntVar dog = model.intVar("Dog", 1, 5);
        IntVar fox = model.intVar("Fox", 1, 5);
        IntVar hor = model.intVar("Horse", 1, 5);
        IntVar sna = model.intVar("Snail", 1, 5);
        IntVar zeb = model.intVar("Zebra", 1, 5);

        IntVar che = model.intVar("Chesterfield", 1, 5);
        IntVar koo = model.intVar("Kool", 1, 5);
        IntVar luc = model.intVar("Lucky Strike", 1, 5);
        IntVar old = model.intVar("Old Gold", 1, 5);
        IntVar par = model.intVar("Parliament", 1, 5);


        // Création des contraintes
        int [][] tEq = new int[][] {{1,1},{2,2},{3,3},{4,4},{5,5}};
        Tuples tuplesAutorises = new Tuples(tEq,true);		// création de Tuples de valeurs autorisés
        Tuples tuplesInterdits = new Tuples(tEq,false);		// création de Tuples de valeurs interdits

        model.table(new IntVar[]{blu,gre}, tuplesInterdits).post();
        // création d'une contrainte en extension de portée <blu,gre>
        // dont les tuples autorisés/interdits sont données par tuplesInterdits
        model.table(new IntVar[]{blu,ivo}, tuplesInterdits).post();
        model.table(new IntVar[]{blu,red}, tuplesInterdits).post();
        model.table(new IntVar[]{blu,yel}, tuplesInterdits).post();
        model.table(new IntVar[]{gre,ivo}, tuplesInterdits).post();
        model.table(new IntVar[]{gre,red}, tuplesInterdits).post();
        model.table(new IntVar[]{gre,yel}, tuplesInterdits).post();
        model.table(new IntVar[]{ivo,red}, tuplesInterdits).post();
        model.table(new IntVar[]{ivo,yel}, tuplesInterdits).post();
        model.table(new IntVar[]{red,yel}, tuplesInterdits).post();

        model.table(new IntVar[]{eng,jap}, tuplesInterdits).post();
        model.table(new IntVar[]{eng,nor}, tuplesInterdits).post();
        model.table(new IntVar[]{eng,spa}, tuplesInterdits).post();
        model.table(new IntVar[]{eng,ukr}, tuplesInterdits).post();
        model.table(new IntVar[]{jap,nor}, tuplesInterdits).post();
        model.table(new IntVar[]{jap,spa}, tuplesInterdits).post();
        model.table(new IntVar[]{jap,ukr}, tuplesInterdits).post();
        model.table(new IntVar[]{nor,spa}, tuplesInterdits).post();
        model.table(new IntVar[]{nor,ukr}, tuplesInterdits).post();
        model.table(new IntVar[]{spa,ukr}, tuplesInterdits).post();

        model.table(new IntVar[]{cof,mil}, tuplesInterdits).post();
        model.table(new IntVar[]{cof,ora}, tuplesInterdits).post();
        model.table(new IntVar[]{cof,tea}, tuplesInterdits).post();
        model.table(new IntVar[]{cof,wat}, tuplesInterdits).post();
        model.table(new IntVar[]{mil,ora}, tuplesInterdits).post();
        model.table(new IntVar[]{mil,tea}, tuplesInterdits).post();
        model.table(new IntVar[]{mil,wat}, tuplesInterdits).post();
        model.table(new IntVar[]{ora,tea}, tuplesInterdits).post();
        model.table(new IntVar[]{ora,wat}, tuplesInterdits).post();
        model.table(new IntVar[]{tea,wat}, tuplesInterdits).post();

        model.table(new IntVar[]{dog,fox}, tuplesInterdits).post();
        model.table(new IntVar[]{dog,hor}, tuplesInterdits).post();
        model.table(new IntVar[]{dog,sna}, tuplesInterdits).post();
        model.table(new IntVar[]{dog,zeb}, tuplesInterdits).post();
        model.table(new IntVar[]{fox,hor}, tuplesInterdits).post();
        model.table(new IntVar[]{fox,sna}, tuplesInterdits).post();
        model.table(new IntVar[]{fox,zeb}, tuplesInterdits).post();
        model.table(new IntVar[]{hor,sna}, tuplesInterdits).post();
        model.table(new IntVar[]{hor,zeb}, tuplesInterdits).post();
        model.table(new IntVar[]{sna,zeb}, tuplesInterdits).post();

        model.table(new IntVar[]{che,koo}, tuplesInterdits).post();
        model.table(new IntVar[]{che,luc}, tuplesInterdits).post();
        model.table(new IntVar[]{che,old}, tuplesInterdits).post();
        model.table(new IntVar[]{che,par}, tuplesInterdits).post();
        model.table(new IntVar[]{koo,luc}, tuplesInterdits).post();
        model.table(new IntVar[]{koo,old}, tuplesInterdits).post();
        model.table(new IntVar[]{koo,par}, tuplesInterdits).post();
        model.table(new IntVar[]{luc,old}, tuplesInterdits).post();
        model.table(new IntVar[]{luc,par}, tuplesInterdits).post();
        model.table(new IntVar[]{old,par}, tuplesInterdits).post();


        /************************************************************************
         *                                                                      *
         * Compléter en ajoutant les contraintes modélisant les phrases 2 à 15  *
         *                                                                      *
         ************************************************************************/

        //C2
        model.arithm(eng,"=",red).post();
        //C3
        model.arithm(spa,"=",dog).post();
        //C4
        model.arithm(cof,"=",gre).post();
        //C5
        model.arithm(ukr,"=",tea).post();
        //C6
        model.arithm(gre,"=",ivo,"+",1).post();
        //C7
        model.arithm(old,"=",sna,"+",1).post();
        //C8
        model.arithm(koo,"=",yel,"+",1).post();
        //C9
        model.absolute(mil, model.intVar(3)).post();
        //C10
        model.absolute(nor, model.intVar(1)).post();
        //C11
        model.table(new IntVar[]{che,fox}, new Tuples(new int[][]{{1,2},{2,3},{2,1},{3,2},{3,4},{4,3},{4,5}},true)).post();
//        model.distance(che,fox,"=",1).post(); // y a qql chose qui ne va pas avec celle là
//        model.or(model.arithm(che,"=",fox,"+",1),model.arithm(fox,"=",che,"+",1)).post();
        //C12
//        model.distance(koo,hor,"=",1).post();
        model.or(model.arithm(koo,"=",hor,"+",1),model.arithm(hor,"=",koo,"+",1)).post();
        //C13
        model.arithm(luc,"=",ora).post();
        //C14
        model.arithm(jap,"=",par).post();
        //C15
        model.or(model.arithm(nor,"=",blu,"+",1),model.arithm(blu,"=",nor,"+",1)).post();


        // Affichage du réseau de contraintes créé
        System.out.println("*** Réseau Initial ***");
        System.out.println(model);


        // Calcul de la première solution
//        if(model.getSolver().solve()) {
//            System.out.println("\n\n*** Première solution ***");
//            System.out.println(model);
//        }


        // Calcul de toutes les solutions
//        System.out.println("\n\n*** Autres solutions ***");
        while(model.getSolver().solve()) {
//            System.out.println("Sol "+ model.getSolver().getSolutionCount()+"\n"+model);
        }


        // Affichage de l'ensemble des caractéristiques de résolution
        System.out.println("\n\n*** Bilan ***");
        model.getSolver().printStatistics();
    }
}
