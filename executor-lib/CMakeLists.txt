cmake_minimum_required(VERSION 3.14)
project(SafeExecutor C)

set(CMAKE_C_STANDARD 11)
set(SRC_DIR src/main/c/ru/ivmiit/ejudge-executor)
set(REUSE_DIR ${SRC_DIR}/ejudge-reuse)
set(HEADERS target/headers)
set(JVM_DIR /usr/lib/jvm/java-8-openjdk-amd64)

include_directories(ejudge)

find_package(ZLIB)

include_directories(${JVM_DIR}/include)
include_directories(${JVM_DIR}/include/linux)
link_directories(${JVM_DIR}/include)
link_directories(${JVM_DIR}/include/linux)

set(SRC_LIST ${REUSE_DIR}/reuse_exec.c
        ${REUSE_DIR}/reuse_logger.c
        ${REUSE_DIR}/reuse_geterrorstring.c
        ${REUSE_DIR}/reuse_errorstring.c
        ${REUSE_DIR}/reuse_errormsg.c
        ${REUSE_DIR}/reuse_getsignalstring.c
        ${REUSE_DIR}/reuse_isabsolutepath.c
        ${REUSE_DIR}/reuse_checkaccess.c
        ${REUSE_DIR}/fileutl.c
        ${REUSE_DIR}/reuse_isfile.c
        ${REUSE_DIR}/reuse_normalize_path.c
        ${REUSE_DIR}/reuse_nodename.c
        ${REUSE_DIR}/reuse_getworkingdir.c
        ${REUSE_DIR}/reuse_rgetworkingdir.c
        ${SRC_DIR}/pathutl.c
        ${SRC_DIR}/testinfo.c
        ${SRC_DIR}/errlog.c
        ${SRC_DIR}/random.c
        ${SRC_DIR}/xalloc.c
        ${SRC_DIR}/compile.c
        ${HEADERS}/ru_ivmiit_executor_SafeExecutor.h
        ${ZLIB_LIBRARIES})

add_executable(EjudgeExecutor ${SRC_DIR}/execute.c ${SRC_LIST})

add_library(EjudgeExecutorDll SHARED ${SRC_DIR}/ru_ivmiit_executor_SafeExecutor.c ${SRC_LIST})
target_link_libraries(EjudgeExecutor ${ZLIB_LIBRARIES})
target_link_libraries(EjudgeExecutorDll ${ZLIB_LIBRARIES})
