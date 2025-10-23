#!/bin/bash

# Build and Docker script for HTTP Inspector
echo "🚀 Building HTTP Inspector application..."

# Build the application
echo "📦 Building JAR file..."
./gradlew clean build -x test

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    
    # Build Docker image
    echo "🐳 Building Docker image..."
    docker build -t httpinspector .
    
    if [ $? -eq 0 ]; then
        echo "✅ Docker image built successfully!"
        echo ""
        echo "🎉 Ready to run:"
        echo "   docker run -p 8080:8080 httpinspector"
        echo "   or"
        echo "   docker-compose up"
    else
        echo "❌ Docker build failed!"
        exit 1
    fi
else
    echo "❌ Build failed!"
    exit 1
fi
