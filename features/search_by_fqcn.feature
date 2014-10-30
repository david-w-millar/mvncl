Feature: search by fully qualified class name

  As a maven central user
  I want to be able to search artifacts that contain a class, given its fully qualified class name
  In order to narrow my search results down

  Scenario:
    When I run `./mvncl search fqcn spock.lang.Specification`
    Then the output should contain "Results: ( 1 to "
    And the output should contain "org.spockframework:spock-core"
    And the exit status should be 0


