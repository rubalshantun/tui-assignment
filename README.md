# TUI REST API For Github Data Assignment Project

This Project expose a single REST EndPoint to list all his github repositories, which are not forks for a given username in Request.

## How to Run

Application can be run by following the steps below

* Clone this repository
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar target/assignment-0.0.1-SNAPSHOT.war
or
        mvn spring-boot:run
```
* Check the stdout or boot_example.log file to make sure no exceptions are thrown

## Rest EndPoints

GET api/v1/user-github-repos-info
Request Params : username (String)
Accept: application/json
Content-Type: application/json
Example : /api/v1/user-github-repos-info?username=rubal

## Sample Response Body
[
    {
        "repoName": "repo1",
        "ownerLogin": "rubal",
        "repoBranchDetails": [
            {
                "branchName": "master",
                "lastCommitSHA": "a24b47679a04afe68a4e7856f401ce4cc8ed1399"
            }
        ]
    },
    {
        "repoName": "repo2",
        "ownerLogin": "rubal",
        "repoBranchDetails": [
            {
                "branchName": "develop",
                "lastCommitSHA": "64df875b013cfdfd3472820adbb52d31552791c9"
            },
            {
                "branchName": "lithium",
                "lastCommitSHA": "217832200f17be70e0cd4bf9a0c864ab851ee393"
            }
        ]
    }
]


### Info About the API

This project exposes single REST GET Endpoint mentioned above whose behaviour is described below for different scenarios :
    ** For a Valid username it will fetch all the non-fork repos name along with their owner login . In the same response it will fetch all the
       branches( their name) of a repo and the latest commit SHA with it . Valid JSON Response body is also mentioned above under Sample Response.

    ** For a Invalid username i.e. username which doesn't exit the API with return 404 Not Found Response along with a Custom JSON Response.
        e.g.
        {
            "status": 404,
            "message": "Given Username doesn't exits on GitHub."
        }

     ** For a Accept Header "application/xml" the API will return 406 Not Acceptable Response along with a Custom JSON Response.
        e.g.
        {
            "status": 406,
            "message": "Requested Response data format is not acceptable. Supported format is JSON only"
        }


## Developer Contact
    Email - rubalshantun@gmail.com