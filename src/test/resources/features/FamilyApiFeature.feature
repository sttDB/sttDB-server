Feature: Use the family api part
  In order to receive information of families
  As a client
  I want to search them

  Scenario: A user wants to get all families
    Given I have one families in the DataBase
    When I use family route "/families"
    Then The response code is 200
    And The family is correct
