Feature: search by gavp

  As a maven central user
  I want to be able to search by combinations of group, artifact name, version, and packaging
  In order to narrow my search results down

  @wip
  Scenario Outline:
    When I run `./mvncl search gavp <args>`
    Then the output should contain "Results: ( 1 to "
    And the output should contain "org.gebish:geb-gradle"

    Examples:
      | args                 |
      | -g org.gebish        |
      | -a geb               |
      | -g org.gebish -a geb |

