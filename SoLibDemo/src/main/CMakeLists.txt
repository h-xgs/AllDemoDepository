cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/jniLibs/arm64-v8a/)

add_library(mysolib SHARED jni/mysolib.c)

# 可以添加其他源文件和依赖项

find_library(log-lib log)

target_link_libraries(mysolib ${log-lib})
