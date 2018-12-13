package com.kuwai.ysy.net.version;

import java.io.File;


public interface VersionListner {
    void isLatest();
    void updateSuccess(File file);
    void updateFailed(Throwable throwable);
    void onProgress(int progress, long total);
}
