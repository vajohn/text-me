# Getting Started

# Author

- **Name:** Lajabu John
- **Email:** lajaub.john@outlook.com
- **LinkedIn:** [Profile](https://www.linkedin.com/in/lajabu-john-546743123/)

## Table of Contents

- [Pre-requisites to starting application](#pre-requisites-to-starting-application)
- [Using the Application](#using-the-application)
    - [Authentication](#authentication)
    - [Messaging](#messaging)
- [API Documentation](#api-documentation)
    - [UserController](#user-controller)
        - [Get All Users](#get-all-users)
        - [Get User by ID](#get-user-by-id)
        - [Get User by Username](#get-user-by-username)
        - [Get User by Email](#get-user-by-email)
        - [Check If Username Exists](#check-if-username-exists)
        - [Check If Email Exists](#check-if-email-exists)
        - [Update User Email](#update-user-email)
    - [MessageController](#message-controller)
        - [Create Message](#create-message)
        - [Save Message](#save-message)
        - [Update Message](#update-message)
        - [Delete Message](#delete-message)
        - [Get Message by ID](#get-message-by-id)
        - [Get Messages by Room and Visibility](#get-messages-by-room-and-visibility)
    - [ChatRoomController](#chat-room-controller)
        - [Create Default Chat Room](#create-chat-room)
        - [Get All Chat Rooms](#get-all-chat-rooms)
        - [Get Chat Room by ID](#get-chat-room-by-id)
        - [Update Chat Room](#update-chat-room)
        - [Delete Chat Room](#delete-chat-room)
    - [AuthenticationController](#authentication-controller)
        - [User Signup](#user-signup)
        - [User Login](#user-login)

## Pre-requisites to starting application

- Java 17 or later
- Maven 3.0+
- MySQL with a database/schema named `text_me`
- Postman or any other API testing tool

**ℹ️ Info:** Please check the applications resources/textMe.postman_collection.json file for the API collection. You can find the websocket collection [here](https://interstellar-water-8096.postman.co/workspace/Team-Workspace~7ecf4a18-6c10-4dd5-820b-f5a8b82ff95b/collection/6602bfc4576521afcb09370d?action=share&creator=2396352)

## Using the Application
### Authentication
- To use the application, start by signing up for an account. Make a POST request to [signup](#user-signup). Provide a username, email, and password in the request body. Upon successful signup, you will receive a response with a token.
- After signing up, you can log in to your account. Make a POST request to [login](#user-login). Provide your username and password in the request body. Upon successful login, you will receive a response with a token.

### Messaging
- To create a chat room, make a POST request to [create chat room](#create-chat-room). Provide the chat room name and description in the request body. The chat room will be created with the user who created it as the owner.
- To view all chat rooms, make a GET request to [get all chat rooms](#get-all-chat-rooms).
- To view a specific chat room, make a GET request to [get chat room by ID](#get-chat-room-by-id). Provide the chat room ID in the URL.
- With a chat room created, you can now send messages. Make a POST request to [create message](#create-message). Provide the message content, sender ID, chat room ID, and other required details in the request body.
- To view all messages in a chat room, make a GET request to [get messages by room and visibility](#get-messages-by-room-and-visibility). Provide the chat room ID and visibility status in the URL.
- To delete a message, make a DELETE request to [delete message](#delete-message). Provide the message ID and the user ID of the message sender in the URL. If the user ID provided is not the same as the message sender ID, the message will not be deleted but its visibility will be set to false.

### Websocket Chat
- To use the websocket chat, you can use the [websocket collection](https://interstellar-water-8096.postman.co/workspace/Team-Workspace~7ecf4a18-6c10-4dd5-820b-f5a8b82ff95b/collection/6602bfc4576521afcb09370d?action=share&creator=2396352) in Postman. The collection contains requests to connect to the websocket server, send messages, and receive messages in real-time.
- Note that you will have to use the rest APIs to authenticate and everything else except for messaging.

**⚠️ Warning:** You can use the rest APIs to send, retrieve and delete messages, the web-sockets just make the experience better.

## API Documentation

This document provides an overview of the APIs exposed by the Spring project.

### User Controller

#### Get All Users

- **URL:** `/api/v1/user`
- **Method:** GET
- **Description:** Retrieves all users.
- **Example:**
  ```bash
  GET /api/v1/user
  ```

#### Get User by ID

- URL: `/api/v1/user/{id}`
- Method: GET
- Description: Retrieves user details by ID.
- **Example:**
  ```bash
  GET /api/v1/user/1
    ```

#### Get User by Username

- URL: `/api/v1/user/username/{username}`
- Method: GET
- Description: Retrieves user details by username.
- **Example:**

 ```bash
  GET /api/v1/user/username/lajabu.john
```

#### Get User by Email

- URL: `/api/v1/user/email/{email}`
- Method: GET
- Description: Retrieves user details by email.
- **Example:**

 ```bash
  GET /api/v1/user/email/lajabu.john@aol.com
```

#### Check if Username Exists

- URL: `/api/v1/user/exists/username/{username}`
- Method: GET
- Description: Checks if a username exists.
- **Example:**

 ```bash
  GET /api/v1/user/exists/username/lajabu.john
```

#### Check if Email Exists

- URL: `/api/v1/user/exists/email/{email}`
- Method: GET
- Description: Checks if an email exists.
- **Example:**
  ```bash
  GET /api/v1/user/exists/email/lajabu.john@aol.com
    ```

#### Update User Email

- URL: `/api/v1/user/update/email`
- Method: PUT
- Description: Updates user email.
- **Example:**
  ```bash
  PUT /api/v1/user/update/email
  Body:
  {
  "id": 1,
  "username": "lajabu.john",
  "email": "newemail@aol.com",
  "password": "password123"
  }
    ```

### Message Controller

#### Create Message

- URL: `/api/v1/message`
- Method: POST
- Description: Creates a new message.
- **Example:**

```bash
  POST /api/v1/message
  Body:
{
  "content": "Hello World!",
  "visible": true,
  "senderId": 1,
  "sender": "lajabu.john",
  "modifiedById": 1,
  "chatRoomId": 1,
  "senderDto": {
    "id": 1,
    "username": "lajabu.john",
    "email": "lajabu.john@aol.com",
    "password": "password123"
  },
  "modifiedBy": {
    "id": 1,
    "username": "lajabu.john",
    "email": "lajabu.john@aol.com",
    "password": "password123"
  },
  "chatRoom": {
    "id": 1,
    "name": "General Chat",
    "description": "General discussion room",
    "createdBy": {
      "id": 1,
      "username": "lajabu.john",
      "email": "lajabu.john@aol.com",
      "password": "password123"
    }
  }
}
```

#### Save Message

- URL: `/api/v1/message/save`
- Method: POST
- Description: Saves a message.
- **Example:**
```bash
  POST /api/v1/message/save
  Body:
    {
      "id": 1,
      "content": "Hello World!",
      "visible": true,
      "sender": "lajabu.john",
      "senderUser": {
        "id": 1,
        "username": "lajabu.john",
        "email": "lajabu.john@aol.com",
        "password": "password123"
      },
      "modifiedByUser": {
        "id": 1,
        "username": "lajabu.john",
        "email": "lajabu.john@aol.com",
        "password": "password123"
      },
      "chatRoom": {
        "id": 1,
        "name": "General Chat",
        "description": "General discussion room",
        "createdBy": {
          "id": 1,
          "username": "lajabu.john",
          "email": "lajabu.john@aol.com",
          "password": "password123"
        }
      }
    }
```
#### Update Message

- URL: `/api/v1/message`
- Method: PUT
- Description: Updates a message.
- **Example:**
```bash
  PUT /api/v1/message
  Body:
  {
    "id": 1,
    "content": "Updated Message",
    "visible": true,
    "sender": "lajabu.john",
    "senderUser": {
      "id": 1,
      "username": "lajabu.john",
      "email": "lajabu.john@aol.com",
      "password": "password123"
    },
    "modifiedByUser": {
      "id": 1,
      "username": "lajabu.john",
      "email": "lajabu.john@aol.com",
      "password": "password123"
    },
    "chatRoom": {
      "id": 1,
      "name": "General Chat",
      "description": "General discussion room",
      "createdBy": {
        "id": 1,
        "username": "lajabu.john",
        "email": "lajabu.john@aol.com",
        "password": "password123"
      }
    }
  }
```
#### Delete Message
- URL: `/api/v1/message/{id}/{userId}`
- Method: DELETE
- Description: Deletes a message. If the user ID provided is not the same as the message sender ID, the message will not be deleted but its visibility will be set to false.
- Example:
```bash
  DELETE /api/v1/message/1/1
```
#### Get Message by ID
- URL: `/api/v1/message/{id}`
- Method: GET
- Description: Retrieves message details by ID.
- Example:
```bash
  GET /api/v1/message/1
```
#### Get Messages by Room and Visibility
- URL: `/api/v1/message/{roomId}/{visible}`
- Method: GET
- Description: Retrieves messages by Chat Room ID and visibility.
- Example:
```bash
  GET /api/v1/message/1/true
```
### Chat Room Controller

#### Create Chat Room

- URL: `/api/v1/chat-room`
- Method: POST
- Description: Creates a default chat room.
- **Example:**
```bash
  POST /api/v1/chat-room
  Body:
  {
    "id": null,
    "name": "Default Room",
    "description": "Default description",
    "createdBy": {
      "id": 1,
      "username": "lajabu.john",
      "email": "lajabu.john@aol.com",
      "password": "password123"
      }
  }
```
#### Get All Chat Rooms

- URL: `/api/v1/chat-room`
- Method: GET
- Description: Retrieves all chat rooms.
- **Example:**
```bash
  GET /api/v1/chat-room
```
#### Get Chat Room by ID

- URL: `/api/v1/chat-room/{id}`
- Method: GET
- Description: Retrieves chat room details by ID.
- **Example:**
```bash
  GET /api/v1/chat-room/1
```
#### Update Chat Room

- URL: `/api/v1/chat-room`
- Method: PUT
- Description: Updates a chat room.
- **Example:**
```bash
  PUT /api/v1/chat-room
  Body:
  {
    "id": 1,
    "name": "Updated Room Name",
    "description": "Updated description",
    "createdBy": {
      "id": 1,
      "username": "lajabu.john",
      "email": "lajabu.john@aol.com",
      "password": "password123"
    }
  }
```
#### Delete Chat Room

- URL: `/api/v1/chat-room/{id}/{userId}`
- Method: DELETE
- Description: Deletes a chat room.
- **Example:**
```bash
  DELETE /api/v1/chat-room/1/1
```
### Authentication Controller

#### User Signup

- URL: `/api/v1/auth/signup`
- Method: POST
- Description: Signs up a user.
- **Example:**
```bash
  POST /api/v1/auth/signup
  Body:
  {
  "id": null,
  "username": "newuser",
  "email": "newuser@aol.com",
  "password": "password123"
  }
```
#### User Login

- URL: `/api/v1/auth/login`
- Method: POST
- Description: Logs in a user.
- **Example:**
```bash
  POST /api/v1/auth/login
  Body:
  {
  "id": null,
  "username": "lajabu.john",
  "email": "lajabu.john@aol.com",
  "password": "password123"
  }
```