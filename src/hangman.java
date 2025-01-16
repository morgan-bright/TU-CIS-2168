import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class hangman {

    // Create word lists
    public static Map<Integer, List<String>> createWordLists() throws FileNotFoundException {
        List<String> dictList = new ArrayList<String>();
        String filePath = "C:\\Users\\HP\\IdeaProjects\\cheatingComputers\\out\\dictionary.txt"; // Absolute file path

        File dictionary = new File(filePath);
        Scanner fileScanner = new Scanner(dictionary);

        while (fileScanner.hasNextLine()) {
            dictList.add(fileScanner.nextLine());
        }

        Map<Integer, List<String>> wordLists = new HashMap<>();

        for (String word : dictList) {
            int wordLength = word.length();
            List<String> wordsOfCertainLength = wordLists.get(wordLength);
            if (wordsOfCertainLength == null) {
                wordsOfCertainLength = new ArrayList<>();
            }
            wordsOfCertainLength.add(word);
            wordLists.put(wordLength, wordsOfCertainLength);
        }

        return wordLists;
    }

    //create word families
    public static Map<String, List<String>> createWordFamilies(List<String> wordList, Set<Character> guessed){
        Map<String, List<String>> wordFamilies = new HashMap<>();

        for(String word: wordList){
            String wordFamily = findWordFamily(word, guessed);
            List<String> wordsInFamily = wordFamilies.get(wordFamily);

            if (wordsInFamily == null) {
                wordsInFamily = new ArrayList<>();
            }

            wordsInFamily.add(word);
            wordFamilies.put(wordFamily, wordsInFamily);
        }

        return wordFamilies;
    }

    //figure out word families for a string
    //go through every letter in the word list: if its been guessed, add letter to the string family,
    // if its not been guessed, add an underscore instead
    public static String findWordFamily(String word, Set<Character> guessed){
        String family = "";

        word = word.toLowerCase();
        for(int i = 0; i<word.length(); i++){
            if(guessed.contains(word.charAt(i))){
                family += word.charAt(i);
            }else{
                family += "_";
            }
        }

        return family;
    }

    //choose biggest word family and get a key
    //this is where we get how to cheat
    public static String getBestFamily(Map<String, List<String>> wordFamilies){
        String bestKey = "";
        int size = Integer.MIN_VALUE;
        for(String family: wordFamilies.keySet()){
            List<String> thisFam = wordFamilies.get(family);
            if(thisFam.size() > size){
                size = thisFam.size();
                bestKey = family;
            }
        }
        return bestKey;
    }



    public static void main(String[] args)  throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        Scanner fileScanner = null;

        System.out.println("Enter word length: ");
        int wordLength = scanner.nextInt();

        System.out.println("How many wrong guesses?: ");
        int numWrongGuessesPossible = scanner.nextInt();



        //guessed -- set of guessed chars
        Set<Character> guessed = new HashSet<Character>();

        //wordList -- map of all words in dict sorted by length
        Map<Integer, List<String>> wordList = createWordLists();

        //wordsOfCertainLength -- list of words of chosen length
        List<String> wordsOfCertainLength = wordList.get(wordLength);

        //wordFamilies -- map of wordFamilies created based on letter correctly guessed sorted by position of correct letter
        Map<String, List<String>> wordFamilies = createWordFamilies(wordsOfCertainLength, guessed);

        //bestKey -- hashCode for the longest wordFamily
        String bestKey = getBestFamily(wordFamilies);

        //currWordFamily -- list of longest word family
        List<String> currWordFamily = wordFamilies.get(bestKey);
        System.out.println(Arrays.toString(new List[]{currWordFamily}));

        Random random = new Random();

        //currCorrect -- current word chosen
        String currCorrect = currWordFamily.get(random.nextInt(currWordFamily.size()));
        System.out.println(currCorrect);



        int numWrongGuesses = 0;
        boolean gameOver = false;
        while(!gameOver) {
            System.out.println(currCorrect);
            String lastBestKey = bestKey;
            System.out.println("Enter first guess as a lowercase character a-z: ");
            Character guess = scanner.next().charAt(0);

            guessed.add(guess);
            boolean guessedCorrect = false;

            for (int i = 0; i < currCorrect.length(); i++) {
                if (currCorrect.charAt(i) == guess) {
                    guessedCorrect = true;
                }
            }
            if (guessedCorrect){
                wordFamilies = createWordFamilies(currWordFamily, guessed);
                bestKey = getBestFamily(wordFamilies);
                currWordFamily = wordFamilies.get(bestKey);
                currCorrect = currWordFamily.get(random.nextInt(currWordFamily.size()));
            }


            System.out.println(bestKey);
            if(!bestKey.contains("_")){
                System.out.println("GameOver: You Win!");
                gameOver = true;
            }else if(bestKey.contains("_") && !lastBestKey.equals(bestKey)){
                numWrongGuesses += 1;
            }

            if(numWrongGuesses >= numWrongGuessesPossible){
                System.out.println("GameOver: You Lose :(");
                gameOver = true;
            }

        }
        scanner.close();





    }
}
