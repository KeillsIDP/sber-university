package dz281223.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class URLReader {
    public static String readContent(String url){
        try{
            URL validatedURL = URI.create(url).toURL();
            URLConnection connection = validatedURL.openConnection();

            String content;

            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();

            return content;
        }
        catch (MalformedURLException e){
            System.out.println("Ссылка не валидна!");
        }
        catch (NullPointerException e){
            System.out.println("Ссылка не может быть пустой!");
        }
        catch (IllegalArgumentException e){
            System.out.println("Ссылка нарушает RFC2396");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "None";
    }
}
