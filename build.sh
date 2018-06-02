#!/bin/bash

mvn clean compile assembly:single
mv target/McInjector-1.0-SNAPSHOT-jar-with-dependencies.jar McInjector.jar
