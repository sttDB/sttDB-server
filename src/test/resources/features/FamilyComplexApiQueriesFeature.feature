Feature: Use the family api part
  In order to receive information of families
  As a client
  I want to search them using complex query methods

  Scenario: A user wants to get families containing two words in the description
    Given I have some families in the DataBase
    When I use the complex family route "/families?firstKeyword=asd&secondKeyword=bnm"
    Then The response code is 200
    And The family has the correct description "asd bnm"

  Scenario: A user wants to get families containing one of the two words in the description
    Given I have some families in the DataBase
    When I use the complex family route "/families?orKeyword=asd&otherOrKeyword=bnm"
    Then The response code is 200
    And The family has the correct description "asd"

  Scenario: A user wants to get families containing one word in the description but not the other word
    Given I have some families in the DataBase
    When I use the complex family route "/families?keyword=asd&notKeyword=xcv"
    Then The response code is 200
    And The family has the correct description "asd"