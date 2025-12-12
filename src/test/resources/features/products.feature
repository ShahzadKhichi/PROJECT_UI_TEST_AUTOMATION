Feature: Products Functionality
  As a logged in user
  I want to browse and interact with products
  So that I can add items to my cart

  Background:
    Given user is logged in as "standard_user" with password "secret_sauce"

  @Products @Smoke @project20
  Scenario: View products list
    Then products page should be displayed
    And user should see products listed

  @Products @project20
  Scenario: Sort products by name A to Z
    When user sorts products by "Name (A to Z)"
    Then products should be sorted by "Name (A to Z)"

  @Products @project20
  Scenario: Sort products by price low to high
    When user sorts products by "Price (low to high)"
    Then products should be sorted by "Price (low to high)"

  @Products @project20
  Scenario: Add single product to cart
    When user adds product 1 to cart
    Then product 1 should be added to cart
    And cart count should be 1

  @Products @project20
  Scenario: Add multiple products to cart
    When user adds product 1 to cart
    And user adds product 2 to cart
    Then cart count should be 2

  @Products
  Scenario: View product details
    When user opens product details for product 1
    Then product details page should be displayed

  @Products
  Scenario: Remove product from cart from products page
    Given user adds product 1 to cart
    When user removes product 1 from cart
    Then cart count should be 0