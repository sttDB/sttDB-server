Feature: Parse the incoming fasta files
  In order to save the information of a fasta file
  As a client
  I want to send the file to the server

  Scenario: A user sends a fasta file
    Given A user sends a file
    When The parser treats it
    And The data is saved
    Then The database has information about families and DNA