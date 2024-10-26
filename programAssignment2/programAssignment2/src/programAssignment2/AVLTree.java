import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class BookOrder {

	public static void main(String [] args) throws FileNotFoundException {
		
		String excelSheet = "orders.csv";
		String line = "";
		BufferedReader reader = null;
		AVLTree AVLTree = new AVLTree();
		
		Scanner scanner = new Scanner(System.in);
		
		
		try {
			reader = new BufferedReader(new FileReader(excelSheet));
			while((line = reader.readLine()) != null){
				String[] row = line.split(",");
				
				try {
					int orderID = Integer.parseInt(row[0]);
					String name = row[1];
					AVLTree.insert(orderID,name);
					}
				catch(Exception e) {}
				}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("WELCOME to your bookstore program!! Your AVLTree is created from your orders.csv file. What would you like to do?: ");
		
		while(true) {
			printChoices();
			int input= 0;
			
			boolean isInt = false;
			
			while(isInt == false){
				if(scanner.hasNextInt()){
					input = scanner.nextInt();
					isInt = true;
				}
				else {
				scanner.next();
				break;
				}
			}		
			
			
			switch(input) {
			case 1:
				System.out.println("What is the OrderID? (Can't be a number below 0 or above 1000)");
				String number = scanner.next();
				if(isInt(number) == true) {
					scanner.nextLine();
					System.out.println("What is the Name of the Book? (100 character limit)");
					String title = scanner.nextLine();
					if(title.length()<=100 && title.length()>0) {
						AVLTree.insert(Integer.parseInt(number), title);
						System.out.println("Action Completed.");
					}else {
						System.out.println("Invalid Character Count.");
					}
				}else {
					System.out.println("OrderID is not valid.");
				}
				break;
				
			case 2:
				System.out.println("Enter OrderID to remove: (Can't be a number below 0 or above 1000)");
				String removeID = scanner.next();
				if(isInt(removeID) == true) {
					AVLTree.remove(Integer.parseInt(removeID));
					System.out.println("Action Completed.");
				}else {
					System.out.println("OrderID is not valid.");
				}
					break;
			
			case 3:
				AVLTree.InOrder();
				break;
				
			case 4:
				System.out.println("Enter OrderID to search: (Can't be a number below 0 or above 1000)");
				String searchID = scanner.next();
				if(isInt(searchID) == true) {	
					AVLTree.Node node= AVLTree.search(AVLTree.Root, Integer.parseInt(searchID));
					if(node == null) {
						System.out.println("Order was not found.");
					}else {
						System.out.println("Order "+node.orderID+" was found: "+ node.name);
					}
				}else {
					System.out.println("OrderID is not valid.");
				}
				break;
				
			case 5:
				AVLTree.Node oldest = AVLTree.findMin(AVLTree.Root);
				if(oldest!= null) {
					System.out.println("Oldest Order: (OrderID) "+ oldest.orderID +" (Name) "+oldest.name);
				}else {
					System.out.println("Tree is empty.");
				}
				break;
				
			case 6:
				AVLTree.Node latest = AVLTree.findMax(AVLTree.Root);
				if(latest!= null) {
					System.out.println("Latest Order: (OrderID) "+ latest.orderID +" (Name) "+latest.name);
				}else {
					System.out.println("Tree is empty.");
				}
				break;
				
			case 7:
				System.out.println("Program terminated.");
				System.exit(0);
				break;
				
			default:
				System.out.println("Please pick a valid choice.");
				break;
				}
			System.out.println();
			System.out.println("The number of node in the Tree is "+AVLTree.getNodeCount()+".");
			System.out.println("The height of the AVL Tree is "+ AVLTree.getHeightTree()+".");
			}
		}
	
	private static void printChoices() {
		System.out.print("\n1. Add Order");
		System.out.print("\n2. Remove Order");
		System.out.print("\n3. Print Orders By Ascending OrderID Number (In-Order Traversal)");
		System.out.print("\n4. Find Name of Book by OrderID");
		System.out.print("\n5. Find Oldest Book Order");
		System.out.print("\n6. Find Latest Book Order");
		System.out.println("\n7. Exit");

	}
	
	private static boolean isInt(String str) {
	    try {
	    	if(Integer.parseInt(str) > 0 &&  Integer.parseInt(str) <= 1000) {
	        Integer.parseInt(str);
	        return true;
	    	}
	    	else {
	    		return false;
	    	}
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

}
