package mastermind;

import java.util.Arrays;

/** Tests de MastermindGame.evaluer, sans dépendance externe : java -ea ... EvaluationTest */
public class EvaluationTest {

    private static int echecs = 0;

    private static void verifier(String secret, String essai, int bien, int mal) {
        int[] r = MastermindGame.evaluer(secret.split(" "), essai.split(" "));
        boolean ok = r[0] == bien && r[1] == mal;
        if (!ok) echecs++;
        System.out.printf("%s  secret=%-28s essai=%-28s -> %s (attendu [%d, %d])%n",
                ok ? "OK  " : "ECHEC", secret, essai, Arrays.toString(r), bien, mal);
    }

    public static void main(String[] args) {
        verifier("Rouge Vert Bleu Jaune", "Rouge Vert Bleu Jaune", 4, 0);
        verifier("Rouge Vert Bleu Jaune", "Jaune Bleu Vert Rouge", 0, 4);
        verifier("Rouge Vert Bleu Jaune", "Orange Orange Mauve Mauve", 0, 0);
        // couleurs répétées : chaque pion du secret ne compte qu'une fois
        verifier("Rouge Vert Bleu Jaune", "Rouge Rouge Rouge Rouge", 1, 0);
        verifier("Rouge Vert Vert Vert", "Vert Rouge Rouge Rouge", 0, 2);
        verifier("Rouge Rouge Bleu Bleu", "Bleu Rouge Vert Vert", 1, 1);
        verifier("Rouge Rouge Bleu Bleu", "Bleu Bleu Rouge Rouge", 0, 4);
        if (echecs > 0) {
            System.out.println(echecs + " test(s) en échec");
            System.exit(1);
        }
        System.out.println("Tous les tests passent.");
    }
}
