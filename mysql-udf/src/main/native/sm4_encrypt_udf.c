#include <stdio.h>
#include <string.h>

#include "mysql.h"
#include "libsm4_udf.h"

bool sm4_encrypt_udf_init(UDF_INIT *initid, UDF_ARGS *args, char *message) {
    return false;
}

char * sm4_encrypt_udf(UDF_INIT *initid, UDF_ARGS *args, char *result, unsigned long *length, char *is_null, char *error) {
    graal_isolate_t *isolate = NULL;
    graal_isolatethread_t *thread = NULL;
    if (0 != graal_create_isolate(NULL, &isolate, &thread)) {
        fprintf(stderr, "initialization error\n" );
        return "";
    }
    sm4_encrypt(thread, args -> args[0], result);
    *length = strlen(result);
    graal_tear_down_isolate(thread);
    return result;
}

bool sm4_decrypt_udf_init(UDF_INIT *initid, UDF_ARGS *args, char *message) {
    return false;
}

char * sm4_decrypt_udf(UDF_INIT *initid, UDF_ARGS *args, char *result, unsigned long *length, char *is_null, char *error) {
    graal_isolate_t *isolate = NULL;
    graal_isolatethread_t *thread = NULL;
    if (0 != graal_create_isolate(NULL, &isolate, &thread)) {
        fprintf(stderr, "initialization error\n" );
        return "";
    }
    sm4_decrypt(thread, args -> args[0], result);
    *length = strlen(result);
    graal_tear_down_isolate(thread);
    return result;
}
