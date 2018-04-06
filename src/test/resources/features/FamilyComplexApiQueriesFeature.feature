Feature: Use the family api part
  In order to receive information of families
  As a client
  I want to search them using complex query methods

  Scenario: A user wants to get families containing two words in the description
    Given I have some families in the DataBase
    When I use the complex family route "/families?firstKeyword=asd&secondKeyword=bnm"
    Then The response code is 200
    And The family has the correct description "asd bnm" and encountered responses 1

  Scenario: A user wants to get families containing one of the two words in the description
    Given I have some families in the DataBase
    When I use the complex family route "/families?orKeyword=asd&otherOrKeyword=bnm"
    Then The response code is 200
    And The family has the correct description "asd" and encountered responses 4

  Scenario: A user wants to get families containing one word in the description but not the other word
    Given I have some families in the DataBase
    When I use the complex family route "/families?keyword=asd&notKeyword=xcv"
    Then The response code is 200
    And The family has the correct description "asd" and encountered responses 2

  Scenario: A user wants to get families containing two words in the description but not the other word
    Given I have some families in the DataBase
    When I use the complex family route "/families?firstKeyword=asd&secondKeyword=bnm&notKeyword=xcv"
    Then The response code is 200
    And The family has the correct description "asd bnm" and encountered responses 1

  Scenario: A user wants to get families containing two words in the description or the other word
    Given I have some families in the DataBase
    When I use the complex family route "/families?firstKeyword=asd&secondKeyword=bnm&otherKeyword=xcv"
    Then The response code is 200
    And The family has the correct description "xcv" and encountered responses 3

  Scenario: A user wants to get families containing one of the two words in the description but not the other word
    Given I have some families in the DataBase
    When I use the complex family route "/families?orKeyword=asd&otherOrKeyword=bnm&notKeyword=xcv"
    Then The response code is 200
    And The family has the correct description "asd" and encountered responses 3

  Scenario: A user wants to get families containing one of the two words in the description and another word
    Given I have some families in the DataBase
    When I use the complex family route "/families?orKeyword=asd&otherOrKeyword=bnm&andKeyword=xcv"
    Then The response code is 200
    And The family has the correct description "asd xcv" and encountered responses 1