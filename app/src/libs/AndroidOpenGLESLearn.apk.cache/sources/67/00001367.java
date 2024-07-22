package com.zyp.opengl.test.util;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: ShaderUtil.kt */
@Metadata(m31d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u0018\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0002J\u000e\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004J\u001e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006J\u001e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004J\u0016\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004J\u0016\u0010\u0014\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0004J\u0016\u0010\u0016\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0006J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001b"}, m30d2 = {"Lcom/zyp/opengl/test/util/ShaderUtil;", "", "()V", "TAG", "", "compileFragmentShader", "", "shaderCode", "compileShader", "shaderType", "compileVertexShader", "linkProgram", "context", "Landroid/content/Context;", "vertexResId", "fragmentResId", "vertexAssetsFile", "fragmentAssetsFile", "vertexShaderCode", "fragmentShaderCode", "loadFromAssetsFile", "name", "loadFromRawFile", "resId", "validateProgram", "", "programObjectId", "app_debug"}, m29k = 1, m28mv = {1, 9, 0}, m26xi = 48)
/* loaded from: classes13.dex */
public final class ShaderUtil {
    public static final ShaderUtil INSTANCE = new ShaderUtil();
    private static final String TAG = "ShaderUtil";

    private ShaderUtil() {
    }

    public final int compileVertexShader(String shaderCode) {
        Intrinsics.checkNotNullParameter(shaderCode, "shaderCode");
        return compileShader(35633, shaderCode);
    }

    public final int compileFragmentShader(String shaderCode) {
        Intrinsics.checkNotNullParameter(shaderCode, "shaderCode");
        return compileShader(35632, shaderCode);
    }

    public final int linkProgram(Context context, String vertexAssetsFile, String fragmentAssetsFile) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(vertexAssetsFile, "vertexAssetsFile");
        Intrinsics.checkNotNullParameter(fragmentAssetsFile, "fragmentAssetsFile");
        return linkProgram(loadFromAssetsFile(context, vertexAssetsFile), loadFromAssetsFile(context, fragmentAssetsFile));
    }

    public final int linkProgram(Context context, int vertexResId, int fragmentResId) {
        Intrinsics.checkNotNullParameter(context, "context");
        return linkProgram(loadFromRawFile(context, vertexResId), loadFromRawFile(context, fragmentResId));
    }

    public final int linkProgram(String vertexShaderCode, String fragmentShaderCode) {
        Intrinsics.checkNotNullParameter(vertexShaderCode, "vertexShaderCode");
        Intrinsics.checkNotNullParameter(fragmentShaderCode, "fragmentShaderCode");
        int vertexShader = compileVertexShader(vertexShaderCode);
        int fragmentShader = compileFragmentShader(fragmentShaderCode);
        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            GLES20.glAttachShader(program, fragmentShader);
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
            if (linkStatus[0] != 1) {
                Log.e(TAG, "Could not link program:" + GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                return 0;
            }
            return program;
        }
        return program;
    }

    private final int compileShader(int shaderType, String shaderCode) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, 35713, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader:" + shaderType);
                Log.e(TAG, "GLES20 Error:" + GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                return 0;
            }
            return shader;
        }
        return shader;
    }

    public final String loadFromAssetsFile(Context context, String name) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(name, "name");
        StringBuilder result = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().getAssets().open(name);
            Intrinsics.checkNotNullExpressionValue(inputStream, "open(...)");
            byte[] buffer = new byte[1024];
            while (true) {
                int it = inputStream.read(buffer);
                Unit unit = Unit.INSTANCE;
                if (-1 != it) {
                    result.append(new String(buffer, 0, it, Charsets.UTF_8));
                } else {
                    String sb = result.toString();
                    Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
                    return new Regex("\\r\\n").replace(sb, "\n");
                }
            }
        } catch (Exception e) {
            return "";
        }
    }

    public final String loadFromRawFile(Context context, int resId) {
        Intrinsics.checkNotNullParameter(context, "context");
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(resId);
            Intrinsics.checkNotNullExpressionValue(inputStream, "openRawResource(...)");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String it = reader.readLine();
                if (it == null) {
                    break;
                }
                sb.append(it);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    public final boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);
        int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, 35715, validateStatus, 0);
        Log.v(TAG, StringsKt.trimIndent("\n     Results of validating program: " + validateStatus[0] + "\n     Log:" + GLES20.glGetProgramInfoLog(programObjectId) + "\n     "));
        return validateStatus[0] != 0;
    }
}