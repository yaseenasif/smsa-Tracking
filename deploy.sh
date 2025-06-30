#!/bin/bash

set -e  # Exit immediately on any error

SERVICE_NAME="springboot_fms.smsaexpress.com.service"
JAR_PATH="../public_html/tms.jar"
LOG_PATH=~/logs/tms.smsaexpress.com_spring.log
TARGET_JAR="./target/Cargo-Tracking-0.0.1-SNAPSHOT.jar"

echo "Stopping service: $SERVICE_NAME"
sudo systemctl stop "$SERVICE_NAME"

echo "Removing old JAR: $JAR_PATH"
sudo rm -f "$JAR_PATH"

echo "Pulling latest code..."
sudo git pull

echo "Running Maven build..."
if sudo mvn clean install; then
    echo "Build successful."

    echo "Deploying new JAR..."
    sudo mv "$TARGET_JAR" "$JAR_PATH"

    echo "Removing old log file: $LOG_PATH"
    sudo rm -f "$LOG_PATH"

    echo "Starting service: $SERVICE_NAME"
    sudo systemctl start "$SERVICE_NAME"

    echo "Deployment complete."
else
    echo "Build failed. Aborting deployment."
    exit 1
fi