Feature: main help usage information

  As a cli user
  In order to comprehend the usage
  I want to see help information when the command is run with no arguments or a help argument

  Scenario: Run cli with no arguments
    When I run `./mvncl`
    Then the output should contain "usage: mvncl"
    And the output should contain "Display help information"
    And the output should contain "Search Maven Central for Artifacts"
    And the exit status should be 0

  Scenario: Run cli with a 'help' argument
    When I run `./mvncl help`
    Then the output should contain "usage: mvncl"
    And the output should contain "Display help information"
    And the output should contain "Search Maven Central for Artifacts"
    And the exit status should be 0

  Scenario Outline: Search help is available
    When I run `./mvncl help <type>`
    Then the output should contain "NAME"
    Then the output should contain "SYNOPSIS"
    Then the output should contain "OPTIONS"
    And the exit status should be 0

    Examples:
      | type        |
      | search      |
      | search main |
      | search gavp |
      | search id   |
      | search fqcn |

