package pkg;
import java.util.*;
import java.io.*;

//done----------------------------------------------------done
public class BBoard {		// This is your main file that connects all classes.
	// Think about what your global variables need to be.
	String title;
   	User currentU;
   	ArrayList<Message> Messages;
   	ArrayList<User> Users;
   	Scanner sc;
	// Default constructor that creates a board with a defaulttitle, empty user and message lists,
	// and no current user
	public BBoard() {
		this.sc = new Scanner(System.in);
      	this.title = "Default";
      	this.currentU = null;
      	this.Messages = new ArrayList();
      	this.Users = new ArrayList();
	}

	// Same as the default constructor except it sets the title of the board
	public BBoard(String ttl) {	
		this.sc = new Scanner(System.in);
     	this.title = ttl;
     	this.currentU = null;
      	this.Messages = new ArrayList();
      	this.Users = new ArrayList();
	}

	// Gets a filename of a file that stores the user info in a given format (Users.txt)
	// Opens and reads the file of all authorized Users and passwords
	// Constructs a User object from each name/password pair, and populates the Users ArrayList.
	public void loadUsers(String inputFile) throws FileNotFoundException {
		File file = new File(inputFile);
		Scanner filescan = new Scanner(file);
  
		while(filescan.hasNextLine()) 
		{
		   String a = filescan.nextLine();
		   String b = a.substring(0, a.indexOf(" "));
		   String c = a.substring(a.indexOf(" ") + 1);
		   this.Users.add(new User(b, c));
		}
  
	 
	}

	// Asks for and validates a user/password. 
	// This function asks for a username and a password, then checks the Users ArrayList for a matching User.
	// If a match is found, it sets currentU to the identified User from the list
	// If not, it will keep asking until a match is found or the user types 'q' or 'Q' as username to quit
	// When the Users chooses to quit, sayu "Bye!" and return from the login function
	public void login(){
		System.out.println(this.title);

      	while(true) 
		{
         	System.out.print("Enter username (q to quit): ");
         	String username = this.sc.nextLine();
         	if (username.equalsIgnoreCase("q")) 
			{
            	System.exit(0);
         	}

         	System.out.print("Enter password: ");
         	String password = this.sc.nextLine();

         	for(int i = 0; i < this.Users.size(); i++) 
			{
            	if (((User)this.Users.get(i)).check(username, password)) 
				{
               		this.currentU = (User)this.Users.get(i);
               		System.out.println();
               		System.out.println(this.currentU.getUsername() + " is currently logged in");
               		System.out.println();
               		return;
            	}
         	}

         	System.out.println("Invalid Username/Password");
         	System.out.println();
      	}
	}
	
	// Contains main loop of Bulletin Board
	// IF and ONLY IF there is a valid currentU, enter main loop, displaying menu items
	// --- Display Messages ('D' or 'd')
	// --- Add New Topic ('N' or 'n')
	// --- Add Reply ('R' or 'r')
	// --- Change Password ('P' or 'p')
	// --- Quit ('Q' or 'q')
	// With any wrong input, user is asked to try again
	// Q/q should reset the currentU to 0 and then end return
	// Note: if login() did not set a valid currentU, function must immediately return without showing menu
	public void run(){
		this.login();
      		if (this.currentU != null) 
			{
         		System.out.println("Menu");
         		System.out.println("  - Display messages (d)");
         		System.out.println("  - Add new topic (n)");
         		System.out.println("  - Add new reply to a Topic (r)");
         		System.out.println("  - Change password (p)");
         		System.out.println("  - Quit (q)");
         		String action = this.sc.nextLine();
         		System.out.println("");

         		while(!action.equalsIgnoreCase("Q")) 
				{
            		if (!action.equalsIgnoreCase("D")) 
					{
               			if (!action.equalsIgnoreCase("N")) 
						{
                  			if (!action.equalsIgnoreCase("R")) 
							{
                     			if (!action.equalsIgnoreCase("P")) 
								{
                     	   			System.out.println("Wrong input, try again: ");
                     			} 
								else 
								{
                        			this.setPassword();
                     			}
                  			} 
							else 
							{
                     			this.addReply();
                  			}
               			} 
						else 
						{
                  			this.addTopic();
               			}
            		} 
					else 
					{
               			this.display();
            		}

            		System.out.println("Menu");
         			System.out.println("  - Display messages (d)");
         			System.out.println("  - Add new topic (n)");
         			System.out.println("  - Add new reply to a Topic (r)");
         			System.out.println("  - Change password (p)");
         			System.out.println("  - Quit (q)");
            		action = this.sc.nextLine();
            		System.out.println("");
         		}

      		}
	}

	// Traverse the BBoard's message list, and invote the print function on Topic objects ONLY
	// It will then be the responsibility of the Topic object to invoke the print function recursively on its own replies
	// The BBoard display function will ignore all reply objects in its message list
	private void display(){
		if (this.Messages.size() == 0) 
		{
			System.out.println("No messages");
		}
   
		for(int i = 0; i < this.Messages.size(); ++i) 
		{
			if (!((Message)this.Messages.get(i)).isReply()) 
			{
			   	System.out.println("---------------");
			   	PrintStream a = System.out;
			   	Object b = this.Messages.get(i);
			   	a.print("Message " + ((Message)b).getId() + ": ");
			   	((Message)this.Messages.get(i)).print(0);
			   	System.out.println("---------------");
			}
		}
	}


	// This function asks the user to create a new Topic (i.e. the first message of a new discussion "thread")
	// Every Topic includes a subject (single line), and body (single line)

	/* 
	Subject: "Thanks"
	Body: "I love this bulletin board that you made!"
	*/

	// Each Topic also stores the username of currentU; and message ID, which is (index of its Message + 1)

	// For example, the first message on the board will be a Topic who's index will be stored at 0 in the Messages ArrayList,
	// so its message ID will be (0+1) = 1
	// Once the Topic has been constructed, add it to the Messages
	// This should invoke your inheritance of Topic to Message
	private void addTopic(){
		System.out.print("Subject: ");
      	String subject = this.sc.nextLine();
      	System.out.print("Body: ");
      	String body = this.sc.nextLine();
      	Topic top = new Topic(this.currentU.getUsername(), subject, body, this.Messages.size() + 1);
      	this.Messages.add(top);
	}

	// This function asks the user to enter a reply to a given Message (which may be either a Topic or a Reply, so we can handle nested replies).
	//		The addReply function first asks the user for the ID of the Message to which they are replying;
	//		if the number provided is greater than the size of Messages, it should output and error message and loop back,
	// 		continuing to ask for a valid Message ID number until the user enters it or -1.
	// 		(-1 returns to menu, any other negative number asks again for a valid ID number)
	
	// If the ID is valid, then the function asks for the body of the new message, 
	// and constructs the Reply, pushing back the Reply on to the Messages.
	// The subject of the Reply is a copy of the parent Topic's subject with the "Re: " prefix.
	// e.g., suppose the subject of message #9 was "Thanks", the user is replying to that message:


	/*
			Enter Message ID (-1 for Menu): 9
			Body: It was a pleasure implementing this!
	*/

	// Note: As before, the body ends when the user enters an empty line.
	// The above dialog will generate a reply that has "Re: Thanks" as its subject
	// and "It was a pleasure implementing this!" as its body.

	// How will we know what Topic this is a reply to?
	// In addition to keeping a pointer to all the Message objects in BBoard's Messages ArrayList
	// Every Message (wheather Topic or Reply) will also store an ArrayList of pointers to all of its Replies.
	// So whenever we build a Reply, we must immediately store this Message in the parent Message's list. 
	// The Reply's constructor should set the Reply's subject to "Re: " + its parent's subject.
	// Call the addChild function on the parent Message to push back the new Message (to the new Reply) to the parent's childList ArrayList.
	// Finally, push back the Message created to the BBoard's Messages. 
	// Note: When the user chooses to return to the menu, do not call run() again - just return fro mthis addReply function. 
	private void addReply(){
		boolean fal = false;
      	System.out.print("Enter a message id (-1 for menu): ");
      	int id = this.sc.nextInt();
      	this.sc.nextLine();
      	if (id != -1) 
		{
         	while(id != -1); 
			{
            	if (id > 0 && id <= this.Messages.size()) 
				{
               		System.out.print("Body: ");
               		String body = this.sc.nextLine();
               		Reply rep = new Reply(this.currentU.getUsername(), ((Message)this.Messages.get(id - 1)).getSubject(), body, this.Messages.size() + 1);
               		this.Messages.add(rep);

               		for(int i = 0; i < this.Messages.size(); ++i) 
					{
                  		if (((Message)this.Messages.get(i)).getId() == id) 
						{
                     		((Message)this.Messages.get(i)).addChild(rep);
                  		}
               		}

               		return;
            	}

            	System.out.println("Invalid id, try again: ");
            	System.out.print("Enter a message id (-1 for menu): ");
            	id = this.sc.nextInt();
            	this.sc.nextLine();
         	} 
			

      	}
	}

	// This function allows the user to change their current password.
	// The user is asked to provide the old password of the currentU.
	// 		If the received password matches the currentU password, then the user will be prompted to enter a new password.
	// 		If the received password doesn't match the currentU password, then the user will be prompted to re-enter the password. 
	// 		The user is welcome to enter 'c' or 'C' to cancel the setting of a password and return to the menu.
	// Any password is allowed except 'c' or 'C' for allowing the user to quit out to the menu. 
	// Once entered, the user will be told "Password Accepted." and returned to the menu.
	private void setPassword(){
		String oldp = "";
      	String newp = "";

      	while(true) 
		{
         	System.out.print("Enter your previous password: ");
         	oldp = this.sc.nextLine();
         	if (oldp.equalsIgnoreCase("c")) 
			{
            	return;
         	}

         	if (this.currentU.check(this.currentU.getUsername(), oldp)) 
			{
            	System.out.print("Enter a new password: ");
				
            	for(newp = this.sc.nextLine(); newp.equalsIgnoreCase("C") || newp.equalsIgnoreCase(""); newp = this.sc.nextLine()) 
				{
					if(newp.equalsIgnoreCase(""))
					{
						System.out.println("Password cannot be empty \n");
					}
					else
					{
						System.out.println("Choose something that is not \"c\" or \"C\" \n");
					}
					System.out.print("Enter a new password: ");
               		
            	}

            	System.out.println("Password has been changed");
            	this.currentU.setPassword(oldp, newp);
            	return;
         	}

         	System.out.println("Invalid password");
         	System.out.println("");
      	}
	}

}
