Feature: Register a new user on HHS
  Scenario: When a new user wants to sign up as a new patient onto HHS
    Given I provide my name as "John Doe"
    And I provide my mobile phone number as "012345678901"
    And I provide my Date Of Birth as 02/05/1997
    And I provide my postcode as "BA1 1AB"
    And I provide my email as "ayedid@hhs.org"
    And I have not previously registered
    When I apply for the first time
    Then register me as a new patient

  Scenario: When a new user wants to sign up again onto HHS
    Given I provide my name as "Belle Sebastian"
    And I provide my mobile phone number as "09876543210"
    And I provide my Date Of Birth as 01/01/1970
    And I provide my postcode as "B11 11B"
    And I provide my email as "belle@hhs.org"
    And I have not previously registered
    When I apply for the first time
    Then register me as a new patient
    When I provide my name as "Belle Sebastian"
    And I provide my mobile phone number as "09876543210"
    And I provide my Date Of Birth as 01/01/1970
    And I provide my postcode as "B11 11B"
    And I provide my email as "belle@hhs.org"
    And I have previously submitted an application
    When I apply again within 24 hours
    Then Do not register me as a new patient
