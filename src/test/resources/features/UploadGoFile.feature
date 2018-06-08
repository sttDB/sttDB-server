Feature: Upload go term file
  In order to assign Go terms to my sequences
  As Admin
  I want to upload a Go file

  Scenario: Upload file as admin
    Given I login as "test" with password "password"
    And I have two sequences in the DataBase
    And There is an experiment named "test"
    And I have a file named "go.txt"
    When I upload the file to experiment "test" using the route "/upload/go"
    Then The go terms are stored
    And The go terms are assigned to the sequences