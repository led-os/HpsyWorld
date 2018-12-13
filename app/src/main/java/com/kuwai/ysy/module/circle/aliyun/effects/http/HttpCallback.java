/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.kuwai.ysy.module.circle.aliyun.effects.http;

public interface HttpCallback<T> {
    void onSuccess(T result);
    void onFailure(Throwable e);
}
