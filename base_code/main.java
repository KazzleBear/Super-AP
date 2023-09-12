import pkg.*;
import java.util.*;
import java.io.*;       

class main {        // This should be all that is in your main.java file.
	public static void main(String args[]) throws FileNotFoundException {
        BBoard myBoard = new BBoard("Steph's Amazing BBoard");          // Feel free to change the name.
        myBoard.loadUsers("/Users/skol5905/Downloads/2023-2024-super-class-main/001_Review/base_code/users.txt");
        myBoard.run();

        // Feel free to add code for testing purposes. 

        // Examine data.txt for example Messages displayed from the BBoard

        // Examine users.txt for the format of users and their passwords. 
	}
}
