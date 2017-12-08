package com.waasche.lawnmower.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class AssetLoader {

    private static String assetsPrefix = "";

    public static String getInternalPath(final String resourcePath) {
        return String.format("%s%s", assetsPrefix, resourcePath);
    }

    public static FileHandle getInternalFileHandler(final String resourcePath) {
        return Gdx.files.internal(getInternalPath(resourcePath));
    }

}
