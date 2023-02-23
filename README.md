# ShopCatalog
### Project Description
This is an imitation of online mobile shops.

### Features
- Registration and login on local server;
- Changing profile settings such as profile image, gender and phone number;
- List of products;
- Cart;

### Libraries
- Navigation
- RecyclerView
- Retrofit
- Hilt
- Kotlin Flow
- Serialization
- Coroutines
- Room
- Timber

# Start instruction
1. Сlone this repository to your computer and open it in Android Studio. Open file "ShopCatalog/domain/src/main/java/com/example/shopcatalog/domain/utils/StringConstants.kt" and change "base_url" param to ***10.0.2.2:8080***;
2. For local authentication system on this step you must start local server on same computer: https://github.com/inimitab1e/studenttasks-backend;
3. Open project from step above in IntelliJ IDEA or in other Kotlin development environment. Open file "studenttasks-backend/src/main/kotlin/ru/studenttask/Application.kt" and change "host" param to ***10.0.2.2***;
4. Run local server and android application after.

Now you good to go.
