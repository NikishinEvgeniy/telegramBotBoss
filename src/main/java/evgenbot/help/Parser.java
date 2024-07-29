package evgenbot.help;

public class Parser {
    public static int getParsedNumeric(String str){
        int number;
        try {
             number = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            return -1;
        }
        return number;
    }
}
