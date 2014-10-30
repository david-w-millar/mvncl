Feature: Maven Central search

  As a maven central user
  In order to get artifact information without resorting to a browser
  I want to be able to search from the command line

  Scenario: The default page size is 20
    When I run `./mvncl search main ratpack`
    Then the output should contain "Results: ( 1 to 20 of"
    And the exit status should be 0

  Scenario: Results contain paging information, version count, ant latest version
    When I run `./mvncl search main -r 5 vertx`
    Then the output should contain "1 to 5"
    And the output should contain "versions, latest"
    And the exit status should be 0

  Scenario Outline: Paging results has correct plural inflection
    When I run `./mvncl search main -r <rows> jbake`
    Then the output should contain "<inflection>"
    And the exit status should be 0

    Examples:
      | rows | inflection |
      | 0    | results    |
      | 1    | result     |
      | 2    | results    |


