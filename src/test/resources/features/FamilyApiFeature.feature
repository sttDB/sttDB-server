Feature: Use the family api part
  In order to receive information of families
  As a client
  I want to search them

  Scenario: A user wants to get all families
    Given I have one families in the DataBase
    When I use family route "/families"
    Then The response code is 200
    And The family is correct

  Scenario: A user wants to get all families that contain a description
    Given I have one families in the DataBase
    When I use family route "/families?descriptionKeyword=protein"
    Then The response code is 200
    And The family is correct

  Scenario: A user wants to get all families that contain a description
    Given I have one families in the DataBase
    When I use family route "/families/asd/sequences?page=0"
    Then The response code is 200
    And The partial proteins are correct