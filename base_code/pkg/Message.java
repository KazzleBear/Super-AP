package pkg;
import java.util.*;
import java.io.*;

//done
public class Message {

	String author;
	String subject;
	String body;
	int id;
	ArrayList<Message> childList;

	// Default Constructor
	public Message() {
		this.author = "";
		this.subject = "";
		this.body = "";
		this.id = 0;
		this.childList = new ArrayList<Message>();
	}
	
	// Parameterized Constructor
	public Message(String auth, String subj, String bod, int i) {
		this.author = auth;
		this.subject = subj;
		this.body = bod;
		this.id = i;
		this.childList = new ArrayList<Message>();
	}

	// This function is responsbile for printing the Message
	// (whether Topic or Reply), and all of the Message's "subtree" recursively:

	// After printing the Message with indentation n and appropriate format (see output details),
	// it will invoke itself recursively on all of the Replies inside its childList, 
	// incrementing the indentation value at each new level.

	// Note: Each indentation increment represents 2 spaces. e.g. if indentation ==  1, the reply should be indented 2 spaces, done
	// if it's 2, indent by 4 spaces, etc. done
	public void print(int indentation){
		String ind = "";
		String rep = "";
		if(this.subject.equals("") && this.author.equals(""))
		{
			System.out.print("Nothing to display");
		}

		for(int i = 0; i < indentation; i++)
		{
			ind += " ";
			if(i % 2 == 0){
				rep += "Re: ";
			}
		}

		System.out.print(":id: " + this.id + "\n");
		System.out.print(":subject: " + rep + this.subject + "\n");
		System.out.print(ind + ":from: " + this.author + "\n");


		if(!this.childList.isEmpty())
		{
			System.out.print(ind);
			for(int j = 0; j < this.childList.size(); j++)
			{
				System.out.print("\n");
				System.out.print(ind + ":id: " + (((Message)this.childList.get(j)).getId() + "\n"));
				((Message)this.childList.get(j)).print(indentation + 2);
			}
		}

		
	}

	// Default function for inheritance
	public boolean isReply(){
		return false;
	}

	// Returns the subject String
	public String getSubject(){
		return this.subject;
	} 

	// Returns the ID
	public int getId(){
		return this.id;
	}

	// Adds a child pointer to the parent's childList.
	public void addChild(Message child){
		this.childList.add(child);
	}

}
