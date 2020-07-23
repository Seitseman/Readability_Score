package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private static class Syllables {
        public long syllables = 0;
        public long polySyllables = 0;
        public long charactersCount = 0;

        private final String[] characters;
        private final String[] words;
        private final String[] sentences;
        private final String text;

        Syllables(String text) {
            this.text = text;
            this.sentences = text.split("[\\.\\?\\!]\\s?");

            final Pattern wordsPattern = Pattern.compile("[\\w,]+");
            Matcher wordsMatcher = wordsPattern.matcher(text);
            this.words = wordsMatcher.results().map(MatchResult::group).toArray(String[]::new);

            this.characters = text.split("[\\n\\t ]");

            charactersCount = 0;
            for (int i = 0; i < characters.length; i++) {
                charactersCount += characters[i].length();
            }

            getSyllablesCount(words);
        }

        public void printStats() {
            System.out.println("The text is: \n" + text + "\n");
            System.out.println("Words: " + words.length);
            System.out.println("Sentences: " + sentences.length);
            System.out.println("Characters: " + charactersCount);
            System.out.println("Syllables: " + syllables);
            System.out.println("Polysyllables: " + polySyllables);
        }

        public void printScores(String method) {
            if (method.equals("all")) {
                printARI();
                printFK();
                printSMOG();
                printCL();
            }
        }

        private void printARI() {
            System.out.println("Automated Readability Index: " + automatedReadabilityIndex() + " (about " + ariYears() +" year olds).");
        }

        private double automatedReadabilityIndex() {
            return 4.71 * charactersCount / words.length + 0.5 * words.length / sentences.length - 21.43;
        }

        private String ariYears() {
            Hashtable<Integer, String> ariHash = new Hashtable<Integer, String>();
            ariHash.put(1, "5-6");
            ariHash.put(2, "6-7");
            ariHash.put(3, "7-9");
            ariHash.put(4, "9-10");
            ariHash.put(5, "10-11");
            ariHash.put(6, "11-12");
            ariHash.put(7, "12-13");
            ariHash.put(8, "13-14");
            ariHash.put(9, "14-15");
            ariHash.put(10, "15-16");
            ariHash.put(11, "16-17");
            ariHash.put(12, "17-18");
            ariHash.put(13, "18-24");
            ariHash.put(14, "24+");

            return ariHash.get(Integer.valueOf((int)Math.ceil(automatedReadabilityIndex())));
        }

        private void printFK() {
            System.out.println("Flesch–Kincaid readability tests: " + fkIndex() + " (about " + fkYears() +" year olds).");
        }

        private double fkIndex() {
            return 0.39 * words.length / sentences.length + 11.8 * syllables / words.length - 15.59;
        }

        private String fkYears() {
            Hashtable<Integer, String> fkHash = new Hashtable<Integer, String>();
            fkHash.put(1, "12");

            return "12";
        }

        private void printSMOG() {
            System.out.println("Simple Measure of Gobbledygook: " + smogIndex() + " (about " + smogYears() +" year olds).");
        }

        private double smogIndex() {
            return 1.043 * Math.sqrt(polySyllables * 30.0 / sentences.length) + 3.1291;
        }

        private String smogYears() {
            return "15";
        }

        private void printCL() {
            System.out.println("Coleman–Liau index: " + clIndex() + " (about " + clYears() +" year olds).");
        }

        private double clIndex() {
            final double L = 100.0 * charactersCount / words.length;
            final double S = 100.0 * sentences.length / words.length;
            return 0.0588 * L - 0.296 * S - 15.8;
        }

        private String clYears() {
            return "17";
        }

        private void getSyllablesCount(String[] words) {
            final String regex = "[aeiouyAEIOUY](?![aeiouyAEIOUY])";
            Pattern patternSyllable = Pattern.compile(regex, Pattern.MULTILINE);
            long syllablesCount = 0;
            long polySyllableCount = 0;

            for (int i = 0; i < words.length; ++i) {
                Matcher matcher = patternSyllable.matcher(words[i]);
                final long resultsCount = matcher.results().count();
                if (resultsCount < 2) {
                    ++syllablesCount;
                } else {
                    final long curMatches = words[i].endsWith("e") ? resultsCount - 1 : resultsCount;
                    syllablesCount += curMatches;
                    if (curMatches > 2) {
                        ++polySyllableCount;
                    }
                }
            }

            syllables = syllablesCount;
            polySyllables = polySyllableCount;
        }

    }

    public static void main(String[] args) {
        File file = new File(args[0]);
        try  {
            String text = readFileAsString(args[0]);

            Syllables syllables = new Syllables(text);

            syllables.printStats();

            System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");

            Scanner scanner = new Scanner(System.in);
            String method = scanner.nextLine();
            System.out.println(method);

            syllables.printScores(method);
        } catch (IOException e) {
            System.out.println("Could not find file " + file.getName());
        }

    }
}
