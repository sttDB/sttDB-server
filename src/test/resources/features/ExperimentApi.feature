Feature: Submit petitions to the experiment API
  In order to receive information about the experiments
  As a user
  I want to query the API

  Scenario: Query all experiments
    Given There are 2 experiments in the Database
    When I get all experiments
    Then I see the experiments "Experiment-1" and "Experiment-2"

  Scenario: Can't POST a experiment
    Given I create a experiment with name "MyExperiment"
    When I POST the experiment
    Then The response code is 405