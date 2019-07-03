package com.qianmo.androidarch.basic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A generic class that holds a value with its loading status.
 * 通用包括状态的数据封装类，可用于配合LiveData<T>实现mvvm
 *
 * @param <T>
 */
public class Resource<T> {

    /**
     * Status of a resource that is provided to the UI.
     * <p>
     * These are usually created by the Repository classes where they return
     * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
     */
    public enum Status {
        /**
         * 空闲状态，初始状态
         */
        IDEL,
        /**
         * 加载状态
         */
        LOADING,
        /**
         * 成功获取结果状态
         */
        SUCCESS,
        /**
         * 错误状态，异常状态
         */
        ERROR
    }

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status) {
        this(status, null, null);
    }

    public Resource(@NonNull Status status, @Nullable String message) {
        this(status, null, message);
    }

    public Resource(@NonNull Status status, @Nullable T data) {
        this(status, data, null);
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource<?> resource = (Resource<?>) o;
        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}