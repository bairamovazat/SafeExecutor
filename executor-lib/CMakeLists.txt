cmake_minimum_required(VERSION 3.14)
project(SafeExecutor VERSION 1.0 LANGUAGES C)
set(CMAKE_C_STANDARD 11)
set(SRC_DIR src/main/c/ru/ivmiit/judge)
set(ETC_DIR src/main/c/ru/ivmiit/etc)

set(HEADERS target/headers)
set(JVM_DIR /usr/lib/jvm/java-8-openjdk-amd64)

include_directories(ejudge)

include_directories(${JVM_DIR}/include)
include_directories(${JVM_DIR}/include/linux)

link_directories(${JVM_DIR}/include)
link_directories(${JVM_DIR}/include/linux)


#TARGET_LINK_LIBRARIES(cgroup cgroup_init)
#ADD_DEPENDENCIES(cgroup cgroup_init)

#find_path([fcntl.h stdlib.h string.h sys/param.h sys/time.h syslog.h termios.h unistd.h magic.h libcgroup.h])

set(SRC_LIST ${SRC_DIR}/runguard.c
        ${SRC_DIR}/config.h
        ${SRC_DIR}/runguard-config.h
        ${HEADERS}/ru_ivmiit_executor_SafeExecutor.h
        ${SRC_DIR}/ru_ivmiit_executor_SafeExecutor.c
        )

add_library(EjudgeExecutorDll SHARED ${SRC_LIST})

add_executable(EjudgeExecutor ${SRC_LIST})

target_link_libraries(EjudgeExecutor m)
target_link_libraries(EjudgeExecutor cgroup)
