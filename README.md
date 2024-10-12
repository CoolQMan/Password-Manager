# 🔐 Password Manager

A secure and user-friendly Android application designed to help users manage their passwords effectively. This project includes features like user authentication, local database management, and CRUD operations for password management.

## Key Features

- 🔑 **User Authentication with Firebase**: Secure user login and registration using Firebase Authentication. Future plans include migrating to a cloud database for storing passwords, enhancing accessibility across devices.
  
- 🗄️ **Local Database for Password Storage**: 
  - Store passwords locally using SQLite.
  - Future migration to a cloud database for password synchronization across multiple devices.

- ✏️ **Add/Edit/Delete Passwords**: Users can easily add, edit, and delete passwords stored in the app.

- 🔍 **Search Functionality**: Quickly find specific passwords using a search feature, utilizing the Filterable interface.

- 📧 **Forget Password Feature**: Users can reset their passwords via email if they forget their credentials.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/password-manager.git
   cd password-manager
   ```
2. **Open the project in Android Studio**.
3. **Set up Firebase**:
    - Create a Firebase project at Firebase Console.
    - Add your Android app to the project and follow the setup instructions.
    - Download `google-services.json` and place it in the `app/` directory.
4. **Build and run the app** on an emulator or physical device.

## 🌟 Future Improvements

- Implement cloud database functionality to store passwords in the cloud, allowing access from multiple devices and enhancing data synchronization.

## ⚠️ Challenges Faced

- Learning to implement the search feature and how the Filterable interface works was initially challenging.
- Working with Firebase for the first time required a learning curve, especially regarding user authentication and database integration.

## Screenshots

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/6c2f09f1-66d4-4417-a1ee-c1e7cc29c491" alt="Login/Register Screen" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/9c3b1bdd-ccdd-4555-8436-635c73f7317b" alt="Main Screen" width="250"/></td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/a01f3134-3da8-4017-8ecd-0f5cfc4e6066" alt="Add Password" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/77560391-ab3a-412e-9d62-68b687778589" alt="Edit Password" width="250"/></td>
  </tr>
</table>

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements

- [Firebase](https://firebase.google.com/) for providing the authentication framework.
- [Android Developers](https://developer.android.com/) for the documentation and resources.
