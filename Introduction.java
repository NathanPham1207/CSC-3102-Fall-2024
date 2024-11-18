import java.io.BufferedReader;
import java.io.FileReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Introduction {

	static SocialNetwork socialNetwork = new SocialNetwork();

	public static void main(String args[]) {

		HashMap<Integer, SocialNetwork.Vertex> vList = new HashMap<Integer, SocialNetwork.Vertex>();

		Scanner scanner = new Scanner(System.in);

		BufferedReader networkReader = null;
		BufferedReader studentReader = null;

		String networkSheet = "src\\network.csv";
		String studentSheet = "src\\students.csv";

		try {
			// Used hashmap to easily match enrollment number to weight, so it can be called
			// later.
			HashMap<Integer, Integer> weightMap = new HashMap<>();
			studentReader = new BufferedReader(new FileReader(studentSheet));

			String line2 = "";

			while ((line2 = studentReader.readLine()) != null) {
				String[] row2 = line2.split(",");
				try {
					int enrollmentNum = Integer.parseInt(row2[0]);
					int weight = Integer.parseInt(row2[3]);
					weightMap.put(enrollmentNum, weight);
				} catch (Exception e) {
				}
			}

			networkReader = new BufferedReader(new FileReader(networkSheet));

			String line1 = "";

			while ((line1 = networkReader.readLine()) != null) {
				String[] row = line1.split(",");
				try {
					int enrollmentNum = Integer.parseInt(row[0]);
					String firstName = row[1];
					String lastName = row[2];
					System.out.println("First Name: " + firstName);
					System.out.println("Last name: " + lastName);

					SocialNetwork.Vertex vertex = socialNetwork.new Vertex(enrollmentNum, firstName, lastName);

					// Putting the vertices in a hashmap based on enrollment number.
					vList.put(enrollmentNum, vertex);

					socialNetwork.addVertex(enrollmentNum);

					for (int i = 3; i < row.length; i++) {
						// Adding the weight from the hashmap.
						int weight = weightMap.getOrDefault(enrollmentNum, 0);
						SocialNetwork.Edge edge = socialNetwork.new Edge(enrollmentNum, Integer.parseInt(row[i]),
								weight);
						socialNetwork.addEdge(edge);
					}

				} catch (Exception e) {
				}
			}
			// socialNetwork.print();

		} catch (Exception e) {
		} finally {
			try {
				if (networkReader != null)
					networkReader.close();
				if (studentReader != null)
					studentReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (true) {

			printStatments();

			int input = 0;

			boolean isInt = false;

			while (isInt == false) {
				if (scanner.hasNextInt()) {
					input = scanner.nextInt();
					isInt = true;
				} else {
					scanner.next();
					break;
				}
			}

			switch (input) {

			case 1:
				System.out.println("Enter the enrollment number or the full name of the student (e.g., 'John Doe'): ");
				scanner.nextLine(); // Consume any leftover newline
				String inputPerson = scanner.nextLine();

				if (isInt(inputPerson)) {
					int student = Integer.parseInt(inputPerson);
					if (socialNetwork.adjList.get(student) != null) {
						System.out.println("\n" + student + "'s network: ");
						for (SocialNetwork.Edge edge : socialNetwork.adjList.get(student)) {
							System.out.print(edge.getTo() + " -> ");
						}
						System.out.println();
					} else {
						System.out.println("The student with enrollment number " + student + " does not exist.");
					}
				} else if (isString(inputPerson)) {
					String[] nameParts = inputPerson.split(" ");
					if (nameParts.length < 2) {
						System.out.println("Invalid input. Please enter both first and last names.");
						break;
					}

					String firstName = nameParts[0];
					StringBuilder lastNameBuilder = new StringBuilder();
					for (int i = 1; i < nameParts.length; i++) {
						if (i > 1) {
							lastNameBuilder.append(" ");
						}
						lastNameBuilder.append(nameParts[i]);
					}
					String lastName = lastNameBuilder.toString();

					boolean found = false;
					for (SocialNetwork.Vertex vertex : vList.values()) {
						if (vertex.getFirstName().equalsIgnoreCase(firstName)
								&& vertex.getLastName().equalsIgnoreCase(lastName)) {
							System.out.println("\n" + firstName + " " + lastName + "'s network: ");
							if (socialNetwork.adjList.get(vertex.getEnrollmentNum()) != null) {
								for (SocialNetwork.Edge edge : socialNetwork.adjList.get(vertex.getEnrollmentNum())) {
									System.out.print(edge.getTo() + " -> ");
								}
								System.out.println();
							} else {
								System.out.println("No connections found for " + firstName + " " + lastName + ".");
							}
							found = true;
							break;
						}
					}
					if (!found) {
						System.out.println("Student with name " + firstName + " " + lastName + " does not exist.");
					}
				} else {
					System.out.println("Invalid input.");
				}
				break;

			case 2:
				System.out.println("Student A: ");
				scanner.nextLine();
				String studentID = scanner.nextLine();

				if (isInt(studentID) == true) {
					int studentA = Integer.parseInt(studentID);
					System.out.println("Student B: ");
					String studenta = scanner.next();
					if (isInt(studenta) == true) {
						int studentB = Integer.parseInt(studenta);

						socialNetwork.dijkstra(studentA, studentB);

					} else {
						System.out.println("Student doesn't exist");
					}
				} else if (isString(studentID) == true) {
					int studentA = 0;
					int studentB1 = 0;

					String[] nameParts = studentID.split(" ");
					if (nameParts.length != 2) {
						System.out.println("Invalid input.");
					} else {
						String firstName = nameParts[0];
						String lastName = nameParts[1];

						for (SocialNetwork.Vertex vertex : vList.values()) {
							if (vertex.getFirstName().equalsIgnoreCase(firstName)
									&& vertex.getLastName().equalsIgnoreCase(lastName)) {
								studentA = vertex.getEnrollmentNum();
								System.out.println(vertex.getEnrollmentNum());

							}

							System.out.println("Student B: ");
							String studentB = scanner.nextLine();
							if (isString(studentB) != true) {
								String[] nameParts2 = studentID.split(" ");
								if (nameParts2.length == 2) {
									System.out.println("Invalid input.");
								} else {
									String firstName2 = nameParts[0];
									String lastName2 = nameParts[1];

									for (SocialNetwork.Vertex v : vList.values()) {
										if (vertex.getFirstName().equalsIgnoreCase(firstName2)
												&& vertex.getLastName().equalsIgnoreCase(lastName2)) {
											studentB1 = v.getEnrollmentNum();
											System.out.println(v.getEnrollmentNum());

										}

									}
								}
							}
							socialNetwork.dijkstra(studentA, studentB1);

						}
					}
				}

				break;

			case 3:

				System.out.println("Enter the enrollment number of the student A (source): ");
				int source = scanner.nextInt();
				System.out.println("Enter the enrollment number of the student B (target): ");
				int target = scanner.nextInt();

				if (socialNetwork.adjList.containsKey(source)) {
					boolean removed = false;
					List<SocialNetwork.Edge> edges = socialNetwork.adjList.get(source);
					if (edges != null) {
						Iterator<SocialNetwork.Edge> iterator = edges.iterator();
						while (iterator.hasNext()) {
							SocialNetwork.Edge edge = iterator.next();
							if (edge.getTo() == target) {
								iterator.remove();
								removed = true;
								break;
							}
						}
					}
					if (removed) {
						System.out.println("Connection from " + source + " to " + target + " removed.");
					} else {
						System.out.println("No connection exists from " + source + " to " + target + ".");
					}
				} else {
					System.out.println("Student A does not exist in the network.");
				}
				break;

			case 4:

				System.out.println("Enter the enrollment number of the student to increase wait days: ");
				int student = scanner.nextInt();
				System.out.println("Enter the number of days to increase: ");
				int increase = scanner.nextInt();

				if (socialNetwork.adjList.containsKey(student)) {
					for (SocialNetwork.Edge edge : socialNetwork.adjList.get(student)) {
						edge.setWeight(edge.getWeight() + increase);
					}
					System.out.println("Wait days increased by " + increase + " for student " + student + ".");
				} else {
					System.out.println("Student does not exist in the network.");
				}
				break;

			case 5:

				System.out.println("Enter the enrollment number of the student to decrease wait days: ");
				student = scanner.nextInt();
				System.out.println("Enter the number of days to decrease: ");
				int decrease = scanner.nextInt();

				if (socialNetwork.adjList.containsKey(student)) {
					for (SocialNetwork.Edge edge : socialNetwork.adjList.get(student)) {
						edge.setWeight(Math.max(0, edge.getWeight() - decrease));
					}
					System.out.println("Wait days decreased by " + decrease + " for student " + student + ".");
				} else {
					System.out.println("Student does not exist in the network.");
				}
				break;

			case 6:
				scanner.close();
				System.exit(0);
				break;

			}
		}
	}

	public static void printStatments() {
		System.out.println("\n1. Print network list for who: ");
		System.out.println("2. Dijkstra's Algor: ");
		System.out.println("3. Disconnect: ");
		System.out.println("4. Increase number of wait days: ");
		System.out.println("5. Decrease number of wait days: ");
		System.out.println("6. Exit.");
	}

	public static boolean isInt(String str) {
		try {
			if (Integer.parseInt(str) > 0 && Integer.parseInt(str) <= socialNetwork.adjList.size()) {
				Integer.parseInt(str);
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

	}

	public static boolean isString(String str) {
		try {
			String.valueOf(str);
			return true;

		} catch (NumberFormatException e) {
			return false;
		}

	}

}