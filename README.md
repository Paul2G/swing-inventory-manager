# Inventory Management Application

This is a simple inventory management application developed using Java Swing. The application allows you to manage loans to personnel, capturing inventory items, employees, and departments. All changes can be saved locally through Java object serialization.

## Features

- **Inventory Management**: Capture inventory items with their name, quantity, and price.
- **Employee Management**: Capture employee information and check their loan history, including the cost of lost items.
- **Department Management**: Capture departments and include employee in them.
- **Data Persistence**: Save all changes locally using Java object serialization.

## Usage

### Inventory Management

1. Navigate to the Inventory section.
   ![Inventory list](/docs/assets//inventory-list.png)
2. See all of the inventory movements listed.
   ![History list](/docs/assets/history-list.png)
3. Add new inventory items by entering their name, quantity, and price.
   ![New item](/docs//assets//new-item.png)
4. View, edit, or delete existing inventory items.
   ![Item view](/docs/assets/item-view.png)

### Employees Management

1. Navigate to the employees section.
   ![Employees list](/docs/assets/employees-list.png)
2. Add new employees by entering their details.
   ![Employee update](/docs/assets/employee-update.png)
3. View a employee's loan history and the cost of any lost items.
   ![Employee view](/docs/assets//emplyee-view.png)

### Department Management

1. Navigate to the Departments section.
2. Add new departments and assign workers to them.
3. View, edit, or delete departments and their associated workers.

### Data Persistence

All changes made in the application are saved locally through Java object serialization. This ensures that your data is preserved between sessions.
