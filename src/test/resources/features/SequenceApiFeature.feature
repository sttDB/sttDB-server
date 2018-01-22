Feature: Use the sequence api part
  In order to receive information of sequences
  As a client
  I want to search them

  Scenario: A user wants to get all sequences
    Given I have two sequences in the DataBase
    When I use sequence route "/sequences/"
    Then The response code is 200
    And The sequences are correct

  Scenario: A user wants to get the sequences of one experiment
    Given I have two sequences in the DataBase
    When I use sequence route "/sequences?experiment=test"
    Then The response code is 200
    And The sequences are correct

  Scenario: A user wants to get all sequences with one trinity
    Given I have two sequences in the DataBase
    When I use sequence route "/sequences?trinityId=asd"
    Then The response code is 200
    And The sequences are correct

  Scenario: A user wants to get all sequences with a trinity and experiment
    Given I have two sequences in the DataBase
    When I use sequence route "/sequences?trinityId=asd&experiment=test"
    Then The response code is 200
    And The sequence is correct

  Scenario: Can't POST a new Sequence
    Given I create a sequence with trinirtyId "comp_1234" and transcript "ACTG" in experiment "exp-A"
    When I POST the sequence
    Then The response code is 405

  Scenario: Can't DELETE a Sequence
    Given I have two sequences in the DataBase
    When I DELETE a sequence with trinityId "asd" and experiment "test"
    Then The response code is 405

  Scenario: Can't PUT a Sequence
    Given I have two sequences in the DataBase
    When I modify the sequence with new trinityId "<>"
    Then The response code is 405