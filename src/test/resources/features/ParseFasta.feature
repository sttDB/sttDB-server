Feature: Parse the incoming fasta files
  In order to save the information of a fasta file
  As a client
  I want to send the file to the server

  Scenario: A user sends a fasta file
    Given I send a file
    When The parser treats it
    And The data is saved
    Then I get 201 http response
    And The database has information about families and DNA

  Scenario: A user sends a file that is not a fasta
    Given I have a file that is not a fasta
    And I send a file
    When The parser treats it
    Then I get 415 http response