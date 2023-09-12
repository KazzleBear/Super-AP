package pkg;
import java.util.*;
import java.io.*;

//done-------------------done
public class Topic extends Message {

    String username;
    String password;
    // Default Constructor
    public Topic(){

    }

    // Parameterized constructor
    public Topic(String auth, String subj, String bod, int i) {
        super(auth, subj, bod, i);
    }

    // Returns if it's a reply (false)
    public boolean isReply(){
        return false;
    }
}
