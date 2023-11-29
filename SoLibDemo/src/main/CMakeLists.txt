# 指定构建所需的最低 CMake 版本。在这个示例中，至少需要 CMake 版本 3.4.1
cmake_minimum_required(VERSION 3.4.1)

# 设置目标库（.so 文件）的输出目录，${CMAKE_SOURCE_DIR} 是当前CMakeLists文件的目录
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/jniLibs/arm64-v8a/)
# 定义要构建的动态库。在这个示例中，将 jni/mysolib.c 文件编译为一个共享库，名字为 mysolib
add_library(mysolib SHARED jni/mysolib.c)



# 可以添加其他源文件和依赖项
# 查找名为 log 的系统库。这是 Android NDK 提供的一个标准库，用于记录日志。
find_library(log-lib log)
# 将 mysolib 目标库与 log-lib 库进行链接。这意味着在构建 mysolib 时，将使用 log-lib 库中的符号。
target_link_libraries(mysolib ${log-lib})
