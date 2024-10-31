package programAssignment2;
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class bookOrder {

	public static void main(String [] args) throws FileNotFoundException {
		
		String excelSheet = "src\\orders.csv";
		String line = "";
		BufferedReader reader = null;
		AVLTree AVLTree = new AVLTree();
		
	
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("WELCOME to your bookstore program!! Your AVL Tree will be created from your orders.csv file. "
				+ "\nIf an order ID is below 1, over 10000, already used, blank, and/or is a string, it will not be added."
				+ "\nAny errors while importing will be listed below.\n");
		
		
		try {
			reader = new BufferedReader(new FileReader(excelSheet));
			while((line = reader.readLine()) != null){
				String[] row = line.split(",");
				lineNumber++;
				
				try {
					int orderID = Integer.parseInt(row[0]);
					String name = row[1];
					AVLTree.insert(orderID,name);
					}
				catch(Exception e) {
				if(!(row[0].toUpperCase().equals("ORDERID") && row[1].toUpperCase().equals("NAME"))) {
					System.out.println("Line " + lineNumber + " from orders.csv isn't formatted correctly.");
					
					}
				}
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
		
		if(AVLTree.Root == null) {
			System.out.println("\nYou don't have anything in your order.csv, your AVLTree is null.");
		}
		
		
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
				System.out.println("What is the OrderID? (Can't be a number below 0 or above 10000)");
				String number = scanner.next();
				if(isValid(number) == true) {
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
				if(AVLTree.Root == null) {
					System.out.println("Your AVL Tree is null. There is nothing to remove.");
				}else {
					System.out.println("Enter OrderID to remove: (Can't be a number below 0 or above 10000)");
					String removeID = scanner.next();
					if(isValid(removeID) == true) {
						AVLTree.remove(Integer.parseInt(removeID));
						System.out.println("Action Completed.");
					}else {
						System.out.println("OrderID is not valid.");
					}
				}
					break;
			
			case 3:
				if(AVLTree.Root == null) {
					System.out.println("Your AVL Tree is null. There is nothing to print.");
				}
				else {
					System.out.println("Printing your orders:");
					AVLTree.InOrder();
				}
				break;
				
			case 4:
				if(AVLTree.Root == null) {
					System.out.println("Your AVL Tree is null. There is nothing to search.");
				}else {
					System.out.println("Enter OrderID to search: (Can't be a number below 0 or above 10000)");
					String searchID = scanner.next();
					if(isValid(searchID) == true) {	
						AVLTree.Node node= AVLTree.search(AVLTree.Root, Integer.parseInt(searchID));
						if(node == null) {
							System.out.println("Order was not found.");
						}else {
							System.out.println("Order "+node.orderID+" was found: "+ node.name);
						}
					}else {
						System.out.println("OrderID is not valid.");
					}
				}
				break;
				
			case 5:
				AVLTree.Node oldest = AVLTree.findMin(AVLTree.Root);
				if(oldest!= null) {
					System.out.println("Oldest Order: (OrderID) "+ oldest.orderID +" (Name) "+oldest.name);
				}
				break;
				
			case 6:
				AVLTree.Node latest = AVLTree.findMax(AVLTree.Root);
				if(latest!= null) {
					System.out.println("Latest Order: (OrderID) "+ latest.orderID +" (Name) "+latest.name);
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
		System.out.print("\nWhat would you like to do?: ");
		System.out.print("\n1. Add Order");
		System.out.print("\n2. Remove Order");
		System.out.print("\n3. Print Orders By Ascending OrderID Number (In-Order Traversal)");
		System.out.print("\n4. Find Name of Book by OrderID");
		System.out.print("\n5. Find Oldest Book Order");
		System.out.print("\n6. Find Latest Book Order");
		System.out.println("\n7. Exit");

	}
	
	private static boolean isValid(String str) {
	    try {
	    	if(Integer.parseInt(str) > 0 &&  Integer.parseInt(str) <= 10000) {
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

	
	
}

