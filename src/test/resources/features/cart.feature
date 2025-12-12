Feature: Shopping Cart Functionality
  As a user with items in cart
  I want to manage my shopping cart
  So that I can proceed to checkout

  Background:
    Given user is logged in as "standard_user" with password "secret_sauce"

  @Cart @Smoke @project20
  Scenario: View empty cart
    When user clicks on cart icon
    Then cart page should be displayed
    And cart should be empty

  @Cart @Smoke @project20
  Scenario: Add single item to cart from products page
    When user adds product 1 to cart
    Then product 1 should be added to cart
    And cart count should be 1
    When user clicks on cart icon
    Then cart page should be displayed
    And user should see 1 item in cart
    And item "Sauce Labs Backpack" should be in cart

  @Cart @project20
  Scenario: Add multiple items to cart
    When user adds product 1 to cart
    And user adds product 2 to cart
    Then cart count should be 2
    When user clicks on cart icon
    Then cart page should be displayed
    And user should see 2 items in cart

  @Cart @project20
  Scenario: Remove item from cart from products page
    Given user adds product 1 to cart
    When user removes product 1 from cart
    Then cart count should be 0

  @Cart
  Scenario: Remove item from cart page
    Given user adds product 1 to cart
    And user adds product 2 to cart
    When user clicks on cart icon
    And user removes item 1 from cart
    Then user should see 1 item in cart
    And item "Sauce Labs Bike Light" should be in cart

  @Cart @project20
  Scenario: Continue shopping from cart page
    Given user adds product 1 to cart
    When user clicks on cart icon
    And user clicks continue shopping
    Then products page should be displayed

@Cart @Redis
  Scenario: Store and retrieve cart data from Redis
    Given user adds product 1 to cart
    And user adds product 2 to cart
    When user clicks on cart icon
    And cart data is stored in Redis for user "standard_user"
    Then cart data should be retrievable from Redis for user "standard_user"

  @Cart @Smoke
  Scenario: Proceed to checkout from cart
    Given user adds product 1 to cart
    When user clicks on cart icon
    And user proceeds to checkout
    Then checkout information page should be displayed

  @Cart
  Scenario: Verify cart items details
    Given user adds product 1 to cart
    And user adds product 2 to cart
    When user clicks on cart icon
    Then cart item 1 should have name "Sauce Labs Backpack"
    And cart item 1 should have price "$29.99"
    And cart item 2 should have name "Sauce Labs Bike Light"
    And cart item 2 should have price "$9.99"

  @Cart
  Scenario: Clear all items from cart
    Given user adds product 1 to cart
    And user adds product 2 to cart
    And user adds product 3 to cart
    When user clicks on cart icon
    And user removes all items from cart
    Then cart should be empty

  @Cart
  Scenario: Update cart quantity (if applicable)
    Given user adds product 1 to cart
    When user clicks on cart icon
    Then cart item quantity should be 1
    # Note: SauceDemo doesn't support quantity changes, but we can verify initial quantity

  @Cart @DataDriven
  Scenario Outline: Add different products to cart
    Given user is on product "<product_number>"
    When user adds the product to cart
    Then cart should contain "<product_name>"

    Examples:
      | product_number | product_name              |
      | 1              | Sauce Labs Backpack       |
      | 2              | Sauce Labs Bike Light     |
      | 3              | Sauce Labs Bolt T-Shirt   |
      | 4              | Sauce Labs Fleece Jacket  |

  @Cart
  Scenario: Navigate to product details from cart
    Given user adds product 1 to cart
    When user clicks on cart icon
    And user clicks on cart item 1 name
    Then product details page should be displayed for "Sauce Labs Backpack"

  @Cart
  Scenario: Cart persists after logout and login
    Given user adds product 1 to cart
    And user adds product 2 to cart
    When user logs out
    And user logs in with credentials from excel row 1
    Then cart count should be 0
    # Note: SauceDemo clears cart on logout

  @Cart
  Scenario: Cart badge displays correct count
    Given user adds product 1 to cart
    Then cart badge should display "1"
    When user adds product 2 to cart
    Then cart badge should display "2"
    When user removes product 1 from cart
    Then cart badge should display "1"
    When user removes product 2 from cart
    Then cart badge should not be displayed