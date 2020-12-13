# Automation Tests for Onliner.by project

__Ways to run project tests:__

1) clone the project to your computer: 
`git clone https://github.com/SorokinaLera/Onliner.by` 

2) open project 

3) update project dependencies via Maven 
(you can do that in IDE by clicking the “Update” button in `pom.xml` file  or via terminal command `mvn dependency:resolve`)

4) run project via IDE by clicking the run button or via terminal command `mvn clean test -DsuiteXmlFile=src/test/resources/SmokeTest.xml`


__Dependencies__
- Development IDE: IntelliJ IDEA 2020.1.2
- Recommend using GoogleChome 87 version

__Test Cases__
- https://docs.google.com/spreadsheets/d/1fzwZILoHB2FZgGvLMP9YG4LrMMHLhvZ5grIufJl1qe4/edit?usp=sharing
