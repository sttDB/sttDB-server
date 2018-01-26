Feature: Get the files of an experiment
  In order to download a experiment
  As a user
  I want to download its files

  Scenario: Target experiment doesn't exist
    Given There are no experiments
    When I perform a GET to the url "/experiments/missing-exp/files"
    Then The response code is 404
    And The error message is "Experiment 'missing-exp' not found"

  Scenario: Experiment exists and has files
    Given There is an experiment named "Experiment-1"
    And The file "families.txt" is stored under experiment "Experiment-1"
    When I perform a GET to the url "/experiments/Experiment-1/files"
    Then The response code is 200
    And The received list of files contains the file "families.txt"