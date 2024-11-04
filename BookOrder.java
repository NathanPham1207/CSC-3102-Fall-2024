
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BookOrder {

    /* Reads orders from a CSV file
     * Builds an AVL tree
     * Presents a menu for interacting with the tree */
    public static void main(String [] args) throws FileNotFoundException, InterruptedException {

        String excelSheet = "src//orders.csv";
        String line = "";
        BufferedReader reader = null;
        AVLTree AVLTree = new AVLTree();
        int lineNumber = 0;

        Scanner scanner = new Scanner(System.in);
        Thread.sleep(750);
        System.out.println("\nWELCOME to your bookstore program!!");
        Thread.sleep(750);
        System.out.println("Your AVL Tree is being generated from your orders.csv file.");
        Thread.sleep(1500);

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
                        Thread.sleep(350);
                    }
                }
                catch(Exception e) {
                    try {
                        if(!(row[0].toUpperCase().equals("ORDERID") && row[1].toUpperCase().equals("NAME"))) {
                            System.out.println("Line " + lineNumber + " from orders.csv isn't formatted correctly.");
                            Thread.sleep(350);
                        }
                    }catch(Exception exception) {
                        System.out.println("Line " + lineNumber +" is blank.");
                        Thread.sleep(350);
                    }
                }
            }

        }catch (Exception e) {}
        finally {
            try {
                /* Checking if the order.cvs is imported into the src folder. */
                if(reader != null) {
                    reader.close();
                }else {}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(AVLTree.Root == null) {
            System.out.println("\nYour order.cvs was not imported and/or formatted incorrectly.");
            Thread.sleep(750);
            System.out.println("Your AVL Tree is null.");
            Thread.sleep(750);
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
                /* Code to add a book order */
                case 1:
                    Thread.sleep(750);
                    System.out.println("Please enter the OrderID:");
                    Thread.sleep(750);
                    System.out.println("REMINDER: Order IDs must be in the range of 1 to 10,000.");
                    Thread.sleep(750);
                    System.out.println("REMINDER: Duplicate Order IDs are not permitted.");
                    Thread.sleep(750);
                    System.out.println("REMINDER: Blank Order IDs are not permitted.");
                    Thread.sleep(750);
                    System.out.println("REMINDER: Order IDs can only exist in the form of an integer.");
                    Thread.sleep(1000);

                    String number = scanner.next();
                    if(isValid(number) == true) {
                        scanner.nextLine();
                        System.out.println("Please enter the title of the book:");
                        Thread.sleep(750);
                        System.out.println("REMINDER: There exists a 100 character limit.");
                        Thread.sleep(1000);
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

                /* Code to remove a book order */
                case 2:
                    Thread.sleep(750);
                    if(AVLTree.Root == null) {
                        System.out.println("Your AVL Tree is null. There is nothing to remove.");
                    }else {
                        System.out.println("Please enter the ID of the order you wish to remove:");
                        Thread.sleep(750);
                        System.out.println("REMINDER: Order IDs must be in the range of 1 to 10,000.");
                        Thread.sleep(750);
                        System.out.println("REMINDER: Order IDs can only exist in the form of an integer.");
                        Thread.sleep(1000);
                        String removeID = scanner.next();
                        if(isValid(removeID) == true) {
                            AVLTree.remove(Integer.parseInt(removeID));
                            System.out.println("Action Completed.");
                        }else {
                            System.out.println("OrderID is not valid.");
                        }
                    }
                    break;

                /* Code to print orders in ascending order */
                case 3:
                    Thread.sleep(750);
                    if(AVLTree.Root == null) {
                        System.out.println("\nYour AVL Tree is null. There is nothing to print.");
                    }
                    else {
                        System.out.println("\nPrinting your orders:");
                        AVLTree.InOrder();
                    }
                    break;

                /* Code to find a book by OrderID */
                case 4:
                    Thread.sleep(750);
                    if(AVLTree.Root == null) {
                        System.out.println("Your AVL Tree is null. There is nothing to search.");
                    }else {
                        System.out.println("Please enter the ID of the book you wish to find the name of:");
                        Thread.sleep(750);
                        System.out.println("REMINDER: Order IDs must be in the range of 1 to 10,000.");
                        Thread.sleep(1000);
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

                /* Code to find the oldest book order */
                case 5:
                    Thread.sleep(750);
                    AVLTree.Node oldest = AVLTree.findMin(AVLTree.Root);
                    if(oldest!= null) {
                        System.out.println("Oldest Order: (OrderID) "+ oldest.orderID +" (Name) "+oldest.name);
                    }
                    break;

                /* Code to find the latest book order */
                case 6:
                    Thread.sleep(750);
                    AVLTree.Node latest = AVLTree.findMax(AVLTree.Root);
                    if(latest!= null) {
                        System.out.println("Latest Order: (OrderID) "+ latest.orderID +" (Name) "+latest.name);
                    }
                    break;

                /* Code to update a book order's name */
                case 7:
                    Thread.sleep(750);
                    if(AVLTree.Root == null) {
                        System.out.println("\nYour AVL Tree is null. There is nothing to update.");
                    }else {
                        System.out.println("Please enter the ID of the order you wish to update the name of:");
                        Thread.sleep(750);
                        System.out.println("REMINDER: Order IDs must be in the range of 1 to 10,000.");
                        Thread.sleep(750);
                        System.out.println("REMINDER: Order IDs can only exist in the form of an integer.");
                        Thread.sleep(1000);
                        String orderID = scanner.next();
                        if(isValid(orderID) == true) {
                            AVLTree.Node found = AVLTree.search(AVLTree.Root,Integer.parseInt(orderID));
                            if(found == null) {
                                System.out.println("Order was not found.");
                            }else {
                            	scanner.nextLine();
                                System.out.println("Please enter the new name of the order:");
                                Thread.sleep(750);
                                System.out.println("REMINDER: There exists a 100 character limit.");
                                Thread.sleep(1000);
                                String name = scanner.nextLine();
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

                /* Code to search orders by title */
                case 8:
                    Thread.sleep(750);
                    if(AVLTree.Root != null) {
                        System.out.println("Please enter the title you wish to search by:");
                        Thread.sleep(750);
                        System.out.println("REMINDER: There exists a 100 character limit.");
                        Thread.sleep(1000);
                        scanner.nextLine();
    					String title = scanner.nextLine();
    					if(title.length()<=100 && title.length()>0) {
    						AVLTree.searchByTitle(title);
    					}else {
                            System.out.println("Invalid Character Count.");
                        }
                    }
                    else {
                        System.out.println("Your AVL Tree is null. There is nothing to search.");
                    }
                    break;

                /* Code to delete all nodes in the AVL tree */
                case 9:
                    Thread.sleep(750);
                    int count;
                    count = AVLTree.getNodeCount();
                    try {
                        while(count != 0) {
                            AVLTree.remove(AVLTree.Root.orderID);
                            System.out.println("Deleted OrderID: " + AVLTree.Root.orderID + ", Book Name: " + AVLTree.Root.name);
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
                    Thread.sleep(750);
                    System.out.println("Program terminated.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    Thread.sleep(750);
                    System.out.println("Please pick a valid choice.");
                    break;
            }
            System.out.println();
            Thread.sleep(750);
            System.out.println("The number of node in the Tree is "+AVLTree.getNodeCount()+".");
            Thread.sleep(750);
            System.out.println("The height of the AVL Tree is "+ AVLTree.getHeightTree()+".");
        }
    }

    /* Displays the menu choices to the user */
    private static void printChoices() throws InterruptedException {
        System.out.print("\nHow would you like to proceed?");
        Thread.sleep(1000);
        System.out.print("\nPlease enter the number corresponding to the instruction you wish to execute:");
        Thread.sleep(1000);
        System.out.println("\nREMINDER: Input must be in the range of 1 to 10.");
        Thread.sleep(1000);

        System.out.print("\n1. Add Order");
        Thread.sleep(250);
        System.out.print("\n2. Remove Order");
        Thread.sleep(250);
        System.out.print("\n3. Print Orders By Ascending OrderID Number (In-Order Traversal)");
        Thread.sleep(250);
        System.out.print("\n4. Find Name of Book by OrderID");
        Thread.sleep(250);
        System.out.print("\n5. Find Oldest Book Order");
        Thread.sleep(250);
        System.out.print("\n6. Find Latest Book Order");
        Thread.sleep(250);
        System.out.print("\n7. Update The Name Of An Order");
        Thread.sleep(250);
        System.out.print("\n8. Search Orders By Title");
        Thread.sleep(250);
        System.out.print("\n9. Delete Your Tree");
        Thread.sleep(250);
        System.out.print("\n10. Exit \n");
        Thread.sleep(250);

    }

    /* Checks if a given string represents a valid OrderID within the range */
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