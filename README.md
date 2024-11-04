Book Order Management System

Overview
This Java application is meant to simulate a bookstore inventory. It uses an AVL Tree to manage book orders. The program is capable of reading orders from an external file if formatted correctly. Within the program, the user can add orders, remove orders, edit orders, search for orders etc.

Setting Up the Program
  1. Create a project within your IDE (if using an IDE)
  2. Download the Java files
  3. Place the files within the src directory
  4. Place the "orders.csv" file within the src directory
  5. Run the file labeled “BookOrder.java”

Guidelines for Importing a File
  - File must be named "orders.csv"
  - Orders must be seperated by a new line
  - Orders must follow the following format: OrderID,Name

Running the Program
  - The program will insert properly formated orders from a properly formatted orders.csv file into the AVL Tree in the form of nodes.
  - The user will then be prompted to enter a number that corresponds to the instruction the users wishs to execute.
  - They user may then choose from 1 of 10 options:
       1. ADD ORDER (adds an order in the form of a node to the AVL Tree)
       2. REMOVE ORDER (deletes an order in the form of a node from the AVL Tree)
       3. PRINT ORDERS (In Ascending Order) (prints orders in reverse chronological order)
       4. FIND NAME OF BOOK BY ORDERID (uses the OrderID provided to find the name of a book)
       5. FIND OLDEST BOOK ORDER (returns the earliest order placed)
       6. FIND LATEST BOOK ORDER (returns the most recently placed order
       7. UPDATE THE NAME OF AN ORDER (uses the OrderID provided to allow the user to change the name of the associated book)
       8. SEARCH ORDERS BY TITLE (returns all orders with the same title)
       9. DELETE TREE (deletes the entire AVL Tree)
       10. EXIT PROGRAM (terminates the program)

Error Handling & Formatting Constraints
  - OrderID Limitations:
      - Order IDs can only exist in the form of an integer
      - Order IDs must be in the range of 1 to 10,000
      - Duplicate Order IDs are not permitted
  - Book Title Limitations:
      - There exists a 100 character limit
  - Formatting Erros:
      - Blank lines in orders.csv will be skipped and will prompt an error message
      - Any incorrectly formatted line in orders.csv will prompt an error message
