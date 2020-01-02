import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ayush Bandil on 31/12/2019.
 */
public class ExtractCSV {
    private static String inputCSV = "D:\\Projects\\Cloud\\GoogleContacts\\files\\contactsOrig.csv";
    String outputCSV = "";
    private static List<String> allNumbers = new ArrayList<>();
    private static List<String> allPossibleNumbers = new ArrayList<>();
    private static List<String> allPNonNumbers = new ArrayList<>();

    // all correct indian numbers; start with +91 and of length 13
    private static List<String> cat1Numbers = new ArrayList<>();
    // all correct US numbers; start with +1 and of length 12
    private static List<String> cat2Numbers = new ArrayList<>();
    // all correct indian numbers having +91 missing; length 10 and starts with 6-9
    private static HashMap<String, String> cat3Numbers = new HashMap<>();
    // all indian numbers start with 0; length 11 and starts with 0
    private static HashMap<String, String> cat4Numbers = new HashMap<>();
    // all random numbers; length less than 10 and don't have any +
    private static List<String> cat5Numbers = new ArrayList<>();
    // concatinated numbers
    private static List<String> cat6Numbers = new ArrayList<>();

    // all remaining
    private static List<String> cat7Numbers = new ArrayList<>();


    public static void main(String[] args) {
        String line = "";
        String cvsSplitBy = ",";
        File file = new File(inputCSV.replace("contactsOrig.csv", "contactsOutput.csv"));
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputCSV));
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            List<String[]> outputList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] input = line.split(cvsSplitBy);
                String[] output = performTransformation(input);
                outputList.add(output);
            }
            writer.writeAll(outputList);
            writer.close();
            System.out.println("Output file contactsOutput successully generated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] performTransformation(String[] input) {
        for (int i = 0; i < input.length; i++) {
            String element = input[i].replaceAll(" ", "").replaceAll("-", "").replaceAll("\\*", "");
            element = element.replaceAll("\\(", "").replaceAll("\\)", "");
            if (element.equals("") || input[i].contains("http") || input[i].contains("@")) {
                continue;
            }
            if (isNumeric(element) || (element.contains(":::") && !element.matches(".*[a-zA-Z]+.*"))) {
                allNumbers.add(element);
                switch (getCat(element)) {
                    case 1:
                        cat1Numbers.add(element);
                        break;
                    case 2:
                        cat2Numbers.add(element);
                        break;
                    case 3:
                        cat3Numbers.put(input[0],element);
                        input[i] = "+91" + element;
                        break;
                    case 4:
                        cat4Numbers.put(input[0],element);
                        input[i] = "+91" + element.substring(1, 11);
                        break;
                    case 5:
                        cat5Numbers.add(element);
                        break;
                    case 6:
                        cat6Numbers.add(element);
                        String[] elements = element.split(":::");
                        for (String element1 : elements) {
                            if (getCat(element1) == 3) {
                                element = element.replaceAll(element1, "+91" + element1);
                            } else if (getCat(element1) == 4) {
                                element = element.replaceAll(element1, "+91" + element1.substring(1, 11));
                            }
                        }
                        input[i] = element;
                        break;
                    default:
                        cat7Numbers.add(element);
                }
            } else {
                if (!input[i].matches(".*[a-zA-Z]+.*")) {
                    allPossibleNumbers.add(input[i]);
                } else if (input[i].matches(".*\\d.*")) {
                    allPNonNumbers.add(input[i]);
                }
            }
        }
        return input;
    }

    private static boolean cat6Check(String element) {
        return element.contains(":::");
    }

    private static boolean cat5Check(String element) {
        return element.length() < 10;
    }

    private static int getCat(String element) {
        if (cat1Check(element)) {
            return 1;
        } else if (cat2Check(element)) {
            return 2;
        } else if (cat3Check(element)) {
            return 3;
        } else if (cat4Check(element)) {
            return 4;
        } else if (cat5Check(element)) {
            return 5;
        } else if (cat6Check(element)) {
            return 6;
        } else {
            return 7;
        }
    }

    private static boolean cat4Check(String element) {
        return element.length() == 11 && element.charAt(0) == '0';
    }

    private static boolean cat3Check(String element) {
        return element.length() == 10 && (element.charAt(0) == '6' || element.charAt(0) == '7' || element.charAt(0) == '8' || element.charAt(0) == '9');
    }

    private static boolean cat2Check(String element) {
        return element.contains("+1") && element.length() == 12;
    }

    private static boolean cat1Check(String element) {
        return element.contains("+91") && element.length() == 13;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
