# UniTech Backend Developer README

## Services and Functionalities

UniTech consists of several backend services, each serving a specific functionality.

### 1. Authentication Service

#### Register API
- User registration with a unique PIN.
- Proper error handling for duplicate PINs.
- Comprehensive unit tests.

#### Login API
- User login using PIN and password.
- Proper error handling for incorrect credentials.
- Comprehensive unit tests.

### 2. Account Service

#### Get Accounts API
- Retrieve a list of active user accounts.
- Comprehensive unit tests.

#### Account to Account API
- Transfer funds between user accounts.
- Proper error handling for insufficient funds, transfers to the same account, and transfers to deactivated or non-existing accounts.
- Comprehensive unit tests.

### 3. Currency Service

#### Currency Rates API
- Retrieve selected currency rates as currency pairs.
- Ensure up-to-date rates.
- Optimize cost with mock third-party service.
- Comprehensive unit tests.

## Implementation and Unit Testing

Ensure that each service's implementation aligns with its specified functionalities. Write comprehensive unit tests to validate the correctness and reliability of the UniTech backend.

## Usage

Provide instructions on setting up and running the UniTech backend locally for development or testing purposes.

## Contributing

Contributions to the development of UniTech are welcome.


