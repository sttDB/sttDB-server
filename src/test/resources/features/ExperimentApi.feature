Feature: Submit petitions to the experiment API
  In order to receive information about the experiments
  As a user
  I want to query the API

  Scenario: Query GET all experiments
    Given There are 2 experiments in the Database
    When I get all experiments
    Then I see the experiments "Experiment-1" and "Experiment-2"

  Scenario: Can't POST a experiment
    Given I create a experiment with name "MyExperiment"
    When I POST the experiment
    Then The response code is 405

  Scenario: Can't DELETE a experiment
    Given There are 1 experiments in the Database
    When I DELETE the experiment with ID "Experiment-1"
    Then The response code is 405

  Scenario: Can't PUT a experiment
    Given There are 1 experiments in the Database
    When I modify the experiment with new name "newName"
    Then The response code is 405