# Chronos Couriers Java CLI Application

## Description
A simple courier dispatch system that manages riders, packages, assignments, and logs — all in-memory, using Java and the command-line.

# Chronos Couriers Java CLI Application

## Description
Chronos Couriers is a simple Java command-line application for managing a courier dispatch system. It supports rider management, package placement, assignment, delivery tracking, and audit logging — all in-memory.

## Requirements

- Java Development Kit (JDK) 8 or higher
- Windows, macOS, or Linux terminal

## Folder Structure

```
Chronos-Couriers/
  chronos/
    ChronosApp.java
    service/
      DispatchCenter.java
    model/
      Rider.java
      Package.java
      AuditLog.java
    util/
      PackageComparator.java
```

## How to Compile and Run

1. **Open a terminal in the project root:**
  ```
   cd path/to/Chronos-Couriers
   ```
2. **Compile and run using the batch file:**
   ```
   ./runChronos.bat
   ```
   Or double-click `runChronos.bat` in the project folder.

                         OR
 **Manually Compile all Java files:**
   ```
   javac chronos\ChronosApp.java chronos\service\DispatchCenter.java chronos\model\*.java chronos\util\*.java
   ```
 **Run the application:**
   ```
   java -cp . chronos.ChronosApp
   ```
   

## Sample Commands

Type these commands at the prompt after starting the app:

- `help`  
  Show all available commands.

- `place-order EXPRESS`  
  Place a new express package order.

- `place-order STANDARD fragile`  
  Place a new standard package order marked as fragile.

- `update-rider R1 online`  
  Set rider R1 as online.

- `update-rider R2 offline`  
  Set rider R2 as offline.

- `assign-packages`  
  Assign pending packages to available riders.

- `complete-delivery [packageId]`  
  Mark a package as delivered.

- `get-status rider R1`  
  Show status of rider R1.

- `get-status package [packageId]`  
  Show status of a package.

- `get-deliveries-by-rider R1 24`  
  Show deliveries by rider R1 in the last 24 hours.

- `get-missed-express`  
  List express packages that missed their delivery deadline.

- `exit`  
  Exit the application.

## Example Usage

```
update-rider R1 online
place-order EXPRESS
assign-packages
get-status rider R1
get-status package PKG1
complete-delivery PKG1
get-deliveries-by-rider R1 24
exit
```

## Notes

- All data is stored in memory and will be lost when the application exits.
- Make sure to compile after any code changes.
- If you encounter `ClassNotFoundException`, ensure you are compiling and running from the project root and using the correct classpath or you can use ./runChronos.bat command inside project directory to run