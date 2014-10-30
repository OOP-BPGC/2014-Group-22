/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginmodule;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * This the core module of the loginmodule package which connects
 * to moodle server and authenticates the credentials provided by the 
 * user
 * @author nisarg
 */
public class LoginModuleCore {

    public static boolean connect(String usrname, String pwd) {
        
        String username = usrname;
        String password = pwd;
        boolean status = false;
        
        try {
            
            Connection.Response loginForm = Jsoup
                    .connect("http://10.1.1.242/moodle/login/index.php")
                    .method(Connection.Method.GET)
                    .execute();

            Document document = Jsoup
                    .connect("http://10.1.1.242/moodle/login/index.php")
                    .data("username", username)
                    .data("password", password)
                    .cookies(loginForm.cookies())
                    .post();
            
            status = document.body().html().contains("You are logged in as");

        } catch (IOException e) {
            
            System.out.println("IO exception!");
            
        } finally {
           
            return status;
            
        }
    }

}
