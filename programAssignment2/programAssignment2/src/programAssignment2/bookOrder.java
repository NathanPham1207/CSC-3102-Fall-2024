package programAssignment2;
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class bookOrder {

	public static void main(String [] args) throws FileNotFoundException {
		
		String excelSheet = "src//orders.csv";
		String line = "";
		BufferedReader reader = null;
		AVLTree AVLTree = new AVLTree();
		int lineNumber = 0;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("WELCOME to your bookstore program!! Your AVL Tree will be created from your orders.csv file. "
				+ "\nIf an order ID is below 1, over 10000, already used, blank, and/or is a string, it will not be added."
				+ "\nIf the name of the order is above 100 characters or blank, it will also not be added."
				+ "\nAny errors while importing will be listed below.\n");
		
		try {
			reader = new BufferedReader(new FileReader(excelSheet));
			while((line = reader.readLine()) != null){
				String[] row = line.split(",");
				lineNumber++;
				
				try {
					int orderID = Integer.parseInt(row[0]);
					String name = row[1];
					if(name.length()<=50 && name.length()>0) {
						AVLTree.insert(orderID,name);
						}
					else {
						System.out.println("Line " + lineNumber + " from orders.csv isn't formatted correctly.");
						}
					}
				catch(Exception e) {
					try {	
					if(!(row[0].toUpperCase().equals("ORDERID") && row[1].toUpperCase().equals("NAME"))) {
						System.out.println("Line " + lineNumber + " from orders.csv isn't formatted correctly.");
						}
					}catch(Exception exception) {
						System.out.println("Line " + lineNumber +" is blank.");
					}
				}
				}
			
		}catch (Exception e) {}
		finally {
			try {
				// Checking if the order.cvs is imported into the src folder.
				if(reader != null) {
					reader.close();
				}else {}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		if(AVLTree.Root == null) {
			System.out.println("Your order.cvs was not imported and/or formatted incorrectly"
					+ ". Your AVL Tree is null.");
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
				System.out.println("What is the OrderID? (Can't be a number below 1 or above 10000)");
				String number = scanner.next();
				if(isValid(number) == true) {
					scanner.nextLine();
					System.out.println("What is the Title of the Book? (100 character limit)");
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
					System.out.println("Enter OrderID to remove: (Can't be a number below 1 or above 10000)");
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
					System.out.println("\nYour AVL Tree is null. There is nothing to print.");
				}
				else {
					System.out.println("\nPrinting your orders:");
					AVLTree.InOrder();
				}
				break;
				
			case 4:
				if(AVLTree.Root == null) {
					System.out.println("Your AVL Tree is null. There is nothing to search.");
				}else {
					System.out.println("Enter OrderID to search: (Can't be a number below 1 or above 10000)");
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
<<<<<<< HEAD
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
					scanner.close();
					System.exit(0);
					break;
				case 8:
					int count;
					count = AVLTree.getNodeCount();
					try {
						while(count != 0) {
							AVLTree.remove(AVLTree.Root.orderID);
							System.out.println("Deleted node with OrderID: " + AVLTree.Root.orderID + ", Book Name: " + AVLTree.Root.name);
							count = AVLTree.getNodeCount();
						}	

					}
					catch(NullPointerException e) {
						System.err.print("Delete successfully");
					}

				default:
					System.out.println("Please pick a valid choice.");
					break;
					}
				System.out.println();
				System.out.println("The number of node in the Tree is "+AVLTree.getNodeCount()+".");
				System.out.println("The height of the AVL Tree is "+ AVLTree.getHeightTree()+".");
=======
>>>>>>> d92cf8de8e3aa234cc2487dbbce1b389f3b6b479
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
				if(AVLTree.Root == null) {
					System.out.println("\nYour AVL Tree is null. There is nothing to update.");
				}else {
					System.out.println("\nWhich orderID would you like to update the title for? (Can't be a number below 0 or above 10000)");
					String orderID = scanner.next();
					if(isValid(orderID) == true) {	
						AVLTree.Node found = AVLTree.search(AVLTree.Root,Integer.parseInt(orderID));
						if(found == null) {
							System.out.println("Order was not found.");
						}else {
						System.out.println("What title would you like to have? (100 character limit)");
						String name = scanner.next();
						if(name.length()<=100 && name.length()>0) {
						found.name = name;
						System.out.println("\nOrder "+ orderID +"'s title has been changed to \""+ name+ "\".");
						}else {
							System.out.println("Invalid Character Count.");
						}
					}
					}else {
						System.out.println("OrderID is not valid.");
					}
				}
				break;
				
			case 8:
				if(AVLTree.Root != null) {
					System.out.println("What title do you want to search by? (100 character limit)");
					String title = scanner.next();
					AVLTree.searchByTitle(title);
				}
				else {
					System.out.println("Your AVL Tree is null. There is nothing to search.");
				}
				break;
				
			case 9:
				int count;
				count = AVLTree.getNodeCount();
				try {
					while(count != 0) {
						AVLTree.remove(AVLTree.Root.orderID);
						System.out.println("Deleted node with OrderID: " + AVLTree.Root.orderID + ", Book Name: " + AVLTree.Root.name);
						count = AVLTree.getNodeCount();
					}	
					if(count == 0 ) {
						System.out.println("Your AVL Tree is null. There is nothing to delete.");
					}

				}
				catch(NullPointerException e) {
					System.out.println("\nDeletion successful.");
				}
				break;
				
				
			case 10:
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
<<<<<<< HEAD
		
		private static void printChoices() {
			System.out.print("\n1. Add Order");
			System.out.print("\n2. Remove Order");
			System.out.print("\n3. Print Orders By Ascending OrderID Number (In-Order Traversal)");
			System.out.print("\n4. Find Name of Book by OrderID");
			System.out.print("\n5. Find Oldest Book Order");
			System.out.print("\n6. Find Latest Book Order");
			System.out.println("\n7. Exit");
			System.out.println("\n8. Delete whole AVL tree");

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
=======
	
	private static void printChoices() {
		System.out.print("\nWhat would you like to do?: ");
		System.out.print("\n1. Add Order");
		System.out.print("\n2. Remove Order");
		System.out.print("\n3. Print Orders By Ascending OrderID Number (In-Order Traversal)");
		System.out.print("\n4. Find Name of Book by OrderID");
		System.out.print("\n5. Find Oldest Book Order");
		System.out.print("\n6. Find Latest Book Order");
		System.out.print("\n7. Update The Name Of An Order");
		System.out.print("\n8. Search Orders By Title");
		System.out.print("\n9. Delete Your Tree");
		System.out.print("\n10. Exit \n");
>>>>>>> d92cf8de8e3aa234cc2487dbbce1b389f3b6b479

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


