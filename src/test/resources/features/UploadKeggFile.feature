Feature: Upload kegg term file
  In order to assign Kegg terms to my sequences
  As Admin
  I want to upload a Kegg file

  Scenario: Upload file as admin
    Given I login as "test" with password "password"
    And I have two sequences in the DataBase
    And There is an experiment named "test"
    And I have a file named "kegg.txt"
    When I upload the file to experiment "test" using the route "/upload/kegg"
    Then The kegg terms are stored
    And The kegg terms are assigned to the sequences

  Scenario: Upload wrong file as admin
    Given I login as "test" with password "password"
    And I have two sequences in the DataBase
    And There is an experiment named "test"
    And I have a file named "wrong-kegg.txt"
    When I upload the file to experiment "test" using the route "/upload/kegg"
    Then An exception has ocurred