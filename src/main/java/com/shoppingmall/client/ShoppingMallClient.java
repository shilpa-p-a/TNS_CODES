package com.shoppingmall.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ShoppingMallClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println(" Welcome to the Shopping Mall Console APP ");
        System.out.println("==========================================");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Login");
            System.out.println("2. Sign Up (Create User)");
            System.out.println("3. View Approved Shops & Catalog");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    signup();
                    break;
                case "3":
                    viewShops();
                    break;
                case "4":
                    System.out.println("Exiting terminal client...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void signup() {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (Admin, ShopOwner, Customer, Employee): ");
        String role = scanner.nextLine();

        String jsonBody = String.format("{\"name\": \"%s\", \"password\": \"%s\", \"type\": \"%s\"}", username,
                password, role);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/signup"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("\n[SUCCESS] " + response.body());
            } else {
                System.out.println("\n[FAILED] Sign up failed. Status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Connection failed. Is the server running?");
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String jsonBody = String.format("{\"name\": \"%s\", \"password\": \"%s\"}", username, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("\n[SUCCESS] Logged in successfully.");

                // Extract user ID roughly
                String body = response.body();
                int idIndex = body.indexOf("\"id\":");
                String idStr = "?";
                if (idIndex != -1) {
                    int commaIndex = body.indexOf(",", idIndex);
                    if (commaIndex == -1)
                        commaIndex = body.indexOf("}", idIndex);
                    idStr = body.substring(idIndex + 5, commaIndex).trim();
                }

                // Extract role roughly
                String role = "Customer";
                if (body.contains("\"type\":\"Admin\""))
                    role = "Admin";
                if (body.contains("\"type\":\"ShopOwner\""))
                    role = "ShopOwner";

                System.out.println(">> Your User ID is: " + idStr + " (Role: " + role + ")");
                loggedInMenu(idStr, role);
            } else {
                System.out.println("\n[FAILED] Invalid credentials. Ensure you sign up first! (Status: "
                        + response.statusCode() + ")");
                System.out.println("Error details: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Could not connect to the server. Is Spring Boot running?");
        }
    }

    private static void loggedInMenu(String userId, String role) {
        if ("Admin".equalsIgnoreCase(role)) {
            adminMenu(userId);
            return;
        }

        while (true) {
            System.out.println("\n--- USER MENU (" + role + ") ---");
            System.out.println("1. View Approved Shops & Catalog");
            System.out.println("2. Place an Order (Add to Cart / Checkout)");
            System.out.println("3. Request a New Shop (Shop Owner)");
            System.out.println("4. Add Item to Shop (Shop Owner)");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewShops();
                    break;
                case "2":
                    placeOrder(userId);
                    break;
                case "3":
                    requestShop();
                    break;
                case "4":
                    addItem();
                    break;
                case "5":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void adminMenu(String adminId) {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View Pending Shop Requests");
            System.out.println("2. Approve a Shop");
            System.out.println("3. Reject a Shop");
            System.out.println("4. View Guest Book");
            System.out.println("5. Add Entry to Guest Book");
            System.out.println("6. Send Notification to Shop Owner");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewPendingShops();
                    break;
                case "2":
                    approveShop();
                    break;
                case "3":
                    rejectShop();
                    break;
                case "4":
                    viewGuestBook();
                    break;
                case "5":
                    addGuestBookEntry();
                    break;
                case "6":
                    sendNotification();
                    break;
                case "7":
                    System.out.println("Logging out from Admin console...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void viewShops() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/shops")).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\n--- Shops and Catalog ---");
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void placeOrder(String customerId) {
        System.out.print("Enter Shop ID to buy from: ");
        String shopId = scanner.nextLine();
        System.out.print("Enter total order amount (e.g. 25.50): ");
        String total = scanner.nextLine();
        System.out.print("Enter payment mode (e.g. Credit Card): ");
        String pMode = scanner.nextLine();

        String json = String.format("{\"total\": %s, \"paymentMode\": \"%s\"}", total, pMode);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/orders/" + customerId + "/shop/" + shopId))
                .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200 || res.statusCode() == 201)
                System.out.println("[SUCCESS] Order Placed!");
            else
                System.out.println("[FAILED] " + res.body());
        } catch (Exception e) {
            System.out.println("[ERROR]");
        }
    }

    private static void requestShop() {
        System.out.print("Enter Shop Reference ID (e.g. 101): ");
        String refId = scanner.nextLine();
        System.out.print("Enter Shop Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Category (e.g. Electronics): ");
        String cat = scanner.nextLine();

        String json = String.format("{\"shopId\": %s, \"shopName\": \"%s\", \"shopCategory\": \"%s\"}", refId, name,
                cat);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/shops")).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();

        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[Status " + res.statusCode() + "] " + res.body());
        } catch (Exception e) {
            System.out.println("[ERROR]");
        }
    }

    private static void approveShop() {
        System.out.print("Enter Pending Shop ID to approve (Database ID): ");
        String shopId = scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/shops/" + shopId + "/approve"))
                .PUT(HttpRequest.BodyPublishers.noBody()).build();

        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[Status " + res.statusCode() + "] Shop Approved!");
        } catch (Exception e) {
            System.out.println("[ERROR]");
        }
    }

    private static void addItem() {
        System.out.print("Enter Approved Shop ID to add Item to: ");
        String shopId = scanner.nextLine();
        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Item Price: ");
        String price = scanner.nextLine();
        System.out.print("Enter Category: ");
        String cat = scanner.nextLine();

        String json = String.format("{\"name\": \"%s\", \"price\": %s, \"category\": \"%s\"}", name, price, cat);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/shops/" + shopId + "/items")).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();

        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[Status " + res.statusCode() + "] " + res.body());
        } catch (Exception e) {
            System.out.println("[ERROR]");
        }
    }

    private static void viewPendingShops() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/admin/shops/pending")).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\n--- Pending Shops ---");
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void rejectShop() {
        System.out.print("Enter Pending Shop ID to reject (Database ID): ");
        String shopId = scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/shops/" + shopId + "/reject"))
                .PUT(HttpRequest.BodyPublishers.noBody()).build();

        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("[Status " + res.statusCode() + "] Shop Rejected!");
        } catch (Exception e) {
            System.out.println("[ERROR]");
        }
    }

    private static void viewGuestBook() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/admin/guestbook")).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("\n--- Guest Book Entries ---");
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void addGuestBookEntry() {
        System.out.print("Enter Guest Name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter Message: ");
        String message = scanner.nextLine();

        String json = String.format("{\"guestName\": \"%s\", \"message\": \"%s\"}", guestName, message);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/guestbook")).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();

        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200 || res.statusCode() == 201)
                System.out.println("[SUCCESS] Guest Book Entry Added!");
            else
                System.out.println("[FAILED] " + res.body());
        } catch (Exception e) {
            System.out.println("[ERROR]");
        }
    }

    private static void sendNotification() {
        System.out.print("Enter target Shop ID to notify: ");
        String shopId = scanner.nextLine();
        System.out.print("Enter Notification Message: ");
        String message = scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/admin/notifications/" + shopId))
                .header("Content-Type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString(message)).build();

        try {
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() == 200 || res.statusCode() == 201)
                System.out.println("[SUCCESS] Notification Sent!");
            else
                System.out.println("[FAILED] " + res.body());
        } catch (Exception e) {
            System.out.println("[ERROR]");
        }
    }
}
