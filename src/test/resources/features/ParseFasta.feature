Feature: Parse the incoming fasta files
  In order to save the information of a fasta file
  As a client
  I want to send the file to the server

  Scenario: A user sends a fasta file
    Given I login as "test" with password "password"
    And I have a file named "tests.fasta"
    When I send the file as Multipart file to "/upload/fasta"
    Then The response code is 200
    And The database has information about DNA

  Scenario: A user sends a file that is not a fasta
    Given I login as "test" with password "password"
    And I have a file named "tests.txt"
    When I send the file as Multipart file to "/upload/fasta"
    Then The response code is 400