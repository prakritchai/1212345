Feature: Create employee

  Background:
    * url baseUrl

  Scenario: Create a employee

    Given path 'employee'
    And header Content-Type = 'application/json'
    And request { "id": "1234", "name": "chuck"}
    When method POST
    Then status 200
    And match $ == {name: "chuck", message: "Successfully created employee"}

  Scenario Outline: Create multiple users

    Given path 'employee'
    And header Content-Type = 'application/json'
    And request { "id": '<id>', "name": '<name>'}
    When method POST
    Then status 200
    And match $ == {name: '<name>', message: "Successfully created employee"}

    Examples:

      | id | name   |
      | 2  | jesse  |
      | 3  | walter |
      | 4  | saul   |