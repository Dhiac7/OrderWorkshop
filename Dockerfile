# Use JDK 22
FROM eclipse-temurin:22-jdk

# Install system libraries + tools needed for JavaFX and downloading SDK
RUN apt-get update && apt-get install -y \
    libxext6 libxrender1 libxtst6 libxi6 libgl1 libx11-xcb1 \
    curl unzip \
    && rm -rf /var/lib/apt/lists/*

# Download and extract JavaFX SDK
WORKDIR /opt
RUN curl -L -o javafx.zip https://download2.gluonhq.com/openjfx/22.0.1/openjfx-22.0.1_linux-x64_bin-sdk.zip \
    && unzip javafx.zip \
    && rm javafx.zip

# Copy app
WORKDIR /app
COPY target/Order-1.0-SNAPSHOT.jar app.jar

# Run with JavaFX modules
CMD ["java", \
     "--module-path", "/opt/javafx-sdk-22.0.1/lib", \
     "--add-modules", "javafx.controls,javafx.fxml", \
     "-jar", "app.jar"]
