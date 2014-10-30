Feature: Support various dependency formats

  As a maven central user
  In order to integrate with my preferred build tooling
  I want to format my dependencies for that build tool

  Scenario: Default format is gradle / grails
    When I run `./mvncl search main -r 1 grails`
    Then the output should match /compile '.*:.*:.*'/
    And the exit status should be 0

  Scenario: cli supports maven
    When I run `./mvncl -f maven search main -r 1 griffon`
    Then the output should match:
    """
      <dependency>
        <groupId>.*</groupId>
        <artifactId>.*</artifactId>
        <version>.*</version>
      </dependency>
    """
    And the exit status should be 0

  Scenario: cli supports buildr
    When I run `./mvncl -f maven search main -r 1 groovy`
    Then the output should match /.*:.*:.*/
    And the exit status should be 0

  Scenario: cli supports ivy
    When I run `./mvncl -f ivy search main -r 1 airline`
    Then the output should match /<dependency org=".*" name=".*" rev=".*"/
    And the exit status should be 0

  Scenario: cli supports grape
    When I run `./mvncl -f grape search main -r 1 cucumber`
    Then the output should match /@Grab\('.*:.*:.*'\)/
    And the exit status should be 0

  Scenario: cli supports sbt
    When I run `./mvncl -f sbt search main -r 1 gpars`
    Then the output should match /libraryDependencies \+= '.*' % '.*' % '.*'/
    And the exit status should be 0

  Scenario: cli supports leiningen
    When I run `./mvncl -f leiningen search main -r 1 boot`
    Then the output should match /\[.*\]/
    And the exit status should be 0

