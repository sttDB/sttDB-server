Feature: Upload interpro family file
  In order to assign families to my sequences
  As Admin
  I want to upload Trapid family file

  Scenario: Upload file as admin
    Given I have two sequences in the DataBase
#    And I login as Admin
    And There is an experiment named "test"
    And I have a file named "families.txt"
    When I upload the file to experiment "test"
    Then The families are stored
    And The families are assigned to the sequences