# 612-Template
Captains: use this to start new projects and update to your necessities.

# Set-up

In Settings:
- Options - Under the Merge button heading, unchecked "Allow rebase merging", checked "Allow auto-merge" (see notes on this below), checked "Automatically delete head branches" (can restore if necessary for up to 30-Days)
- Branches - Created a branch protection rule with main branch as Branch name pattern with these checked: "Require a pull request before merging", "Require approvals", "Require review from Code Owners", "Require status checks to pass before merging", "Require branches to be up to date before merging", "Require linear history", and "Include administrators"

# Github Actions
- Created a new "Java CI with Gradle" action file. Under main branch, the ```branches:``` option reads ```[main]``` only but under the feature-template branch, the ```branches:``` option reads ```[main, feature-template].``` 
- Created project-setup.yml file for project automation. **Note:** authors must replace ```project``` url with new repository's project's url.
- **Test Cases:** Example test case created in src/test/java/TestTemplate.java. Future test cases that check actual robot code logic should be placed in ```java``` folder here.

# Project Template
- Created a new automated kanban project.
- To-do setup: Move issues here when "Newly added"
- In progress setup: Move issues here when "Reopened", Move pull requests here when "Newly opened", "Reopened" and "Pending approval by reviewer"
- Done setup: Move issues here when "Closed", Move pull requests here when "Merged"
