
./urbcsp 10 15 10 80 3 > benchSatisf.txt

./urbcsp 10 15 15 20 3 > benchInsat.txt


mv $(find ./ -type f -name "csp*.txt") benchmark/10_15_10/


./generateurBench.sh
/usr/lib/jvm/java-1.11.0-openjdk-amd64/bin/java -javaagent:/snap/intellij-idea-community/464/lib/idea_rt.jar=42407:/snap/intellij-idea-community/464/bin -Dfile.encoding=UTF-8 -classpath /home/tony/M1/firstSemester/symboliq/tp/tp-ia-choco/target/classes:/home/tony/.m2/repository/org/choco-solver/choco-solver/4.10.2/choco-solver-4.10.2.jar:/home/tony/.m2/repository/org/choco-solver/choco-sat/1.0.2/choco-sat-1.0.2.jar:/home/tony/.m2/repository/org/choco-solver/cutoffseq/1.0.5/cutoffseq-1.0.5.jar:/home/tony/.m2/repository/net/sf/trove4j/trove4j/3.0.3/trove4j-3.0.3.jar:/home/tony/.m2/repository/dk/brics/automaton/automaton/1.11-8/automaton-1.11-8.jar:/home/tony/.m2/repository/org/jgrapht/jgrapht-core/1.3.1/jgrapht-core-1.3.1.jar:/home/tony/.m2/repository/org/jheaps/jheaps/0.10/jheaps-0.10.jar:/home/tony/.m2/repository/com/github/cp-profiler/cpprof-java/1.3.0/cpprof-java-1.3.0.jar:/home/tony/.m2/repository/com/google/protobuf/protobuf-java/2.6.1/protobuf-java-2.6.1.jar:/home/tony/.m2/repository/org/knowm/xchart/xchart/3.5.4/xchart-3.5.4.jar:/home/tony/.m2/repository/de/erichseifert/vectorgraphics2d/VectorGraphics2D/0.13/VectorGraphics2D-0.13.jar:/home/tony/.m2/repository/org/apache/maven/surefire/surefire-shared-utils/3.0.0/surefire-shared-utils-3.0.0.jar fr.umontpellier.etu.Expe > result_10_15_20_i_3000.csv
ctrl+o dans openoffice pour import fichier .csv