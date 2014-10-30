Feature: search by id

  As a maven central user
  I want to be able to search by id
  In order to narrow my search results

  Scenario:
    When I run `./mvncl search id org.millarts:groovy-pluralize-en`
    Then the output should contain "Results: ( 1 to "


