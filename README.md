# code-notebook
A centralized monorepo containing a collection of modular proof-of-concept (POC) services and technical implementations.
Each service demonstrates a focused concept—such as data transformation, messaging, persistence, or integration—built as
an independent module with its own configuration and isolated scope. Ideal for experimenting with backend patterns,
reusable components, and real-world microservice-inspired architectures.

### Features

- code-vault - Java sample programs
- vertx-mongo - Vert.x with MongoDB setup

### Github squash commits commands
Follow the below mentioned steps to squash the commits before merging PR to main branch. Change the parent branch name,
current branch name, commit message as per the requirement.

```
1. git status
2. git checkout <parent_branch_name>
3. git pull origin <parent_branch_name>
4. git checkout <current_sub-branch_name>
5. git reset --soft <parent_branch_name>
6. git status
7. git commit -m "<commit message>"
8. git push origin <current_sub-branch_name> -f
```

### Wiremock standalone server
Use the below command to run the wiremock server. If using a different version of wiremock jar, change the version number
in the command accordingly.

```
java -jar wiremock-standalone-3.3.1.jar --port 9999
```