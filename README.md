# Question set

WIP

## Objective

Nowadays, the requirement for a software dev is changing and developers are required to provide solution for different problems with the help of different tools. The resources to obtain those skill sets can be origin from different sites, which makes it hard to track the progress and perform self evaluation.

The tool aims at providing an integrated place for user to gather the required information and perform self evaluation.

## Feature Highlights

- Questions are collected with tags indicating the category of those questions.
- User can do self evaluation based on pool of questions marked with specific tags to gain confidence on knowledge related to a certain topic.
- Evaluation results can be tracked by date so users can check their improvement.

## ToolChain

- Springboot with Kotlin
- JPA
- Docker
- Keycloak(SSO)

## Endpoints

### Tags

| Method | Path          | Description         | User authenticated | Available For User |
| ------ | ------------- | ------------------- | :----------------: | :----------------: |
| GET    | /tags/        | List available tags |         x          |
| GET    | /tags/{tagId} | Get tag detail      |         x          |         ×          |
| POST   | /tags/        | Create new Tag      |         x          |         ×          |
| PUT    | /tags/{tagId} | Update Tag Info     |         ×          |         ×          |
| DELETE | /tags/        | Delete Tag          |         x          |         ×          |

### Collections

| Method | Path                        | Description                | User authenticated | Available For User |
| ------ | --------------------------- | -------------------------- | :----------------: | :----------------: |
| GET    | /collections/               | List available collections |         x          |         x          |
| GET    | /collections/{collectionId} | Get current account data   |         ×          |         ×          |
| POST   | /collections/               | Create new Tag             |         x          |         ×          |
| PUT    | /collections/{collectionId} | Save current account data  |         ×          |         ×          |
| DELETE | /collections/               | Register new account       |         x          |         ×          |

### Pools

| Method | Path                                   | Description                | User authenticated | Available For User |
| ------ | -------------------------------------- | -------------------------- | :----------------: | :----------------: |
| GET    | /pools/                                | List available collections |                    |         x          |
| GET    | /pools/{poolId}                        | Get current account data   |         ×          |         ×          |
| POST   | /pools/                                | Create new Tag             |         x          |         ×          |
| PUT    | /pools/{poolId}                        | Save current account data  |         ×          |         ×          |
| DELETE | /pools/                                | Register new account       |         x          |         ×          |
| POST   | /pools/{poolId}/questions              | Create Question For Pool   |         x          |         ×          |
| POST   | /pools/{poolId}/questions/{questionId} | Add Question To Pool       |         x          |         ×          |
| DELETE | /pools/{poolId}/questions/{questionId} | Remove Question From Pool  |         x          |         ×          |

### Questions

| Method | Path                     | Description                       | User authenticated | Available For User |
| ------ | ------------------------ | --------------------------------- | :----------------: | :----------------: |
| GET    | /questions/              | List available questions          |                    |         x          |
| GET    | /questions/{questionId}  | Get question By Id                |         ×          |         ×          |
| POST   | /questions/              | Create new Question               |         x          |         ×          |
| PUT    | /questions/{questionId}  | Update Question                   |         ×          |         ×          |
| DELETE | /questions/{questionId}  | Delete Question                   |         x          |         ×          |
| POST   | /questions/answers       | Create single Answer for Question |         x          |         ×          |
| POST   | /questions/answers/batch | Create batch answers for Question |         x          |         ×          |

### Answers

| Method | Path                | Description      | User authenticated | Available For User |
| ------ | ------------------- | ---------------- | :----------------: | :----------------: |
| GET    | /answers/           | List answers     |                    |         x          |
| GET    | /answers/{answerId} | Get answer by Id |         ×          |         ×          |
| PUT    | /answers/{answerId} | Update Answer    |         ×          |         ×          |
| DELETE | /answers/{answerId} | Delete Answer    |         x          |         ×          |

## Run Locally

`./mvnw springboot:run` or `make run`

## Environment variables required

Please fill empty `application.properties` with related variable.

The project relies on existing postgres instance and keycloak instance.
