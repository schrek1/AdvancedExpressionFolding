package com.intellij.advancedExpressionFolding.folding.util

/** Signature-only copies of the null-safe utility classes used by `NullOrEmptyTestData`. */
val NULL_OR_EMPTY_UTILITY_STUBS = arrayOf(
    """
    package org.apache.commons.lang3;
    public class StringUtils {
        public static boolean isEmpty(CharSequence cs) { return false; }
        public static boolean isNotEmpty(CharSequence cs) { return false; }
        public static boolean isBlank(CharSequence cs) { return false; }
    }
    """,
    """
    package org.apache.commons.collections4;
    public class CollectionUtils {
        public static boolean isEmpty(java.util.Collection<?> coll) { return false; }
        public static boolean isNotEmpty(java.util.Collection<?> coll) { return false; }
    }
    """,
    """
    package org.apache.commons.collections4;
    public class MapUtils {
        public static boolean isEmpty(java.util.Map<?, ?> map) { return false; }
        public static boolean isNotEmpty(java.util.Map<?, ?> map) { return false; }
    }
    """,
    """
    package org.springframework.util;
    public abstract class CollectionUtils {
        public static boolean isEmpty(java.util.Collection<?> collection) { return false; }
        public static boolean isEmpty(java.util.Map<?, ?> map) { return false; }
    }
    """,
    """
    package org.springframework.util;
    public abstract class ObjectUtils {
        public static boolean isEmpty(Object obj) { return false; }
    }
    """,
)
