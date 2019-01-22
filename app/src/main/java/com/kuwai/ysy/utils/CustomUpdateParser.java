/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kuwai.ysy.utils;

import com.google.gson.Gson;
import com.kuwai.ysy.bean.CustomResult;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.proxy.IUpdateParser;

/**
 * 自定义更新解析器
 *
 * @author xuexiang
 * @since 2018/7/12 下午3:46
 */
public class CustomUpdateParser implements IUpdateParser {

    private boolean update = true;

    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        CustomResult result = new Gson().fromJson(json, CustomResult.class);
        if (Utils.getVerName().equals(result.getData().getApp_version())) {
            update = false;
        }
        if (result != null) {
            return new UpdateEntity()
//                    .setHasUpdate(result.hasUpdate)
//                    .setIsIgnorable(result.isIgnorable)
//                    .setVersionName(result.versionName)
//                    .setUpdateContent(result.updateLog)
//                    .setDownloadUrl(result.apkUrl)
//                    .setSize(result.apkSize);
                    .setHasUpdate(update)
                    .setVersionName(result.getData().getApp_version())
                    .setUpdateContent(result.getData().getApp_content())
                    .setDownloadUrl(result.getData().getApp_url())
                    .setSize(result.getData().getApp_size());
        }
        return null;
    }
}
