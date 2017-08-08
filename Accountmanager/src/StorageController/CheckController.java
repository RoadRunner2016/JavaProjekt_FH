package StorageController;

public class CheckController
{

    public static boolean isNumeric(String value) {
        try
        {

            Double number = Double.parseDouble(value);
            return number < 1000000;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

}


