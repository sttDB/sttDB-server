Feature: Download sequence result as file
  In order to store the resultant information of my query
  As a user
  I want to download the result as a file

  Scenario: Information exist



  Scenario: Information does not exist
    Given I login as "test" with password "password"
    And I have two sequences in the DataBase
    When I perform a GET to the url "/download/fasta?trinityId=asd&experiment=test"
    Then The response code is 200
    And The received list of files contains a file