# Inventory Management System
### Corban, Daniel, John, Luke

## Vision Statement
A functional inventory management system to keep track of items for a user.
User interface should start with a home view of the most general categories (department, or categories). 
On first startup, program looks for a save file, and if none is found it shows a configuration screen that allows the user to create categories, then add unlimited subcategories.
User should be able to press a button to view all stock at the current selected category level (for example company wide or department wide), and filter and sort it.
GUI should be simple to navigate, with an expandable toolbar to quickly change between categories and subcategories, and a back button to return to the previous view.

## MVP Feature List
- Add & remove stock
- Define attributes for each item 
  - Name
  - Description (optional)
  - Amt
- Sort & filter stock
  - Name
  - Attributes
  - Number 
- Create categories
- Maintain program state and behavior

## Stretch Goals Feature List
- Edit item attributes
- Change the name of the company
- Unlimited subcategories
- User defined attributes (color, size, ect)
- Themes
  - Dark mode as a paid feature (mwa-ha-ha)
- Storing stock in a file for saving across program launches? (XML, JSON) 
