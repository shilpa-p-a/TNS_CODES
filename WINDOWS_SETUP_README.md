# Shopping Mall Management - Windows Setup Guide

This guide explains exactly how to set up the Shopping Mall Management application from scratch on a Windows machine using Eclipse and PostgreSQL.

## 1. Install PostgreSQL on Windows

Since this project relies on a database to store users and shops, you must install PostgreSQL first.

1. Go to the [Official PostgreSQL Download Page for Windows](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads).
2. Download the installer for version 15 or 16 (these are standard and stable).
3. Run the installer and click **Next** through the default settings.
4. **IMPORTANT:** When prompted to create a password for the database superuser (`postgres`), enter `postgres` as the password. This matches the exact configuration this Java project needs.
5. Leave the Port at the default `5432`.
6. Finish the installation.

## 2. Create the Database

Spring Boot will automatically create all your tables (Users, Shops, etc.), but it needs an empty database to connect to first.

1. Open **pgAdmin 4** (this was installed automatically alongside PostgreSQL).
2. Look at the left panel, and click **Servers**. Enter the `postgres` password when prompted.
3. Expand the **PostgreSQL 15** (or whichever version you installed) folder.
4. Right-click on **Databases** > **Create** > **Database...**.
5. In the **Database** name field, type exactly: `mall_db`.
6. Click **Save**.

Your database is now ready to be connected to!

## 3. Import and Run the Project in Eclipse

Now we need to load the Java code into your Eclipse IDE.

1. Open **Eclipse**.
2. Go to **File** > **Import...**
3. Select **Maven** > **Existing Maven Projects** and click **Next**.
4. Click **Browse** and select the folder where you extracted/downloaded this `ShoppingMallManagement` project.
5. Make sure `pom.xml` is checked, and click **Finish**.
   *(Note: Look at the bottom right of Eclipse. It will take a minute or two to download the required Spring Boot files from the internet).*

### Running the Server

1. In the Eclipse **Project Explorer** on the left, navigate to:
   `src/main/java` > `com.shoppingmall`
2. Right-click on **`ShoppingMallApplication.java`**.
3. Select **Run As** > **Java Application**.
4. The Eclipse Console will pop up. You should see the Spring Boot logo appear, followed by a message like `Started ShoppingMallApplication`.
   *(At this point, Spring Boot has connected to your `mall_db` and created all the necessary tables).*

### Running the Interactive Console Client

1. Leave the server running.
2. In the **Project Explorer**, navigate to:
   `src/main/java` > `com.shoppingmall.client`
3. Right-click on **`ShoppingMallClient.java`**.
4. Select **Run As** > **Java Application**.
5. The Console view will now switch to the Client! You can click inside the console window and use your keyboard to sign up, log in, approve shops, and interact with the application.

---

## FAQ

**Q: Do I need Postman to use this?**
No, Postman is **not required**. The `ShoppingMallClient.java` file provides a fully functional, interactive menu straight in your Eclipse terminal. You can use it to create users, place orders, and test all Admin features. You only need Postman if you specifically want to manually trigger raw web HTTP requests for debugging purposes.

**Q: Connection Refused Error?**
If the server crashes on startup with `Connection Refused` to `5432`, your Windows PostgreSQL service is not running. Search for "Services" in the Windows start menu, find "postgresql-x64", right-click it, and click "Start".
