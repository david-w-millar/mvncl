Feature: Usage errors show useful information about the error

  As a cli user
  When I enter a malformed command
  I want to see useful information about the error and the usage information

  @wip
  Scenario: Unexpected parameters yield useful error message
    When I run `./mvncl `
    Then the output should contain "OPTIONS"
    And the exit status should not be 0


