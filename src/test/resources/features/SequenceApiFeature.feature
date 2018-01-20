Feature: Use the sequence api part
  In order to receive information of sequences
  As a client
  I want to search them

  Scenario: A user wants to get all sequences
    Given I have two sequences in the DataBase
    When I use route "/sequences/"
    Then The response code is 200
    And The sequences are correct

  Scenario: A user wants to get the sequences of one experiment
    Given I have two sequences in the DataBase
    When I use route "/sequences?experiment=test"
    Then The response code is 200
    And The sequences are correct

  Scenario: A user wants to get all sequences with one trinity
    Given I have two sequences in the DataBase
    When I use route "/sequences?trinityId=asd"
    Then The response code is 200
    And The sequences are correct

  Scenario: A user wants to get all sequences with a trinity and experiment
    Given I have two sequences in the DataBase
    When I use route "/sequences?trinityId=asd&experiment=test"
    Then The response code is 200
    And The sequence is correct