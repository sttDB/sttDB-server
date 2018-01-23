Feature: Get the files of an experiment
  In order to download a experiment
  As a user
  I want to download its files

  Scenario: Target experiment doesn't exist
    Given There are no experiments
    When I perform a GET to the url "/experiments/missing-exp/files"
    Then The response code is 404
    And The error message is "Experiment 'missing-exp' not found"