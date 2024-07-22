package com.zyp.learn.mock;

import kotlin.Metadata;

/* compiled from: MockWaveData.kt */
@Metadata(m31d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\tJ$\u0010\n\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\tJ\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\t¨\u0006\u000e"}, m30d2 = {"Lcom/zyp/learn/mock/MockWaveData;", "", "()V", "generateFloatArray", "", "sampleRate", "", "time", "amplitude", "", "generateFloatArrayCoords", "generateRandomData", "count", "range", "app_debug"}, m29k = 1, m28mv = {1, 9, 0}, m26xi = 48)
/* loaded from: classes13.dex */
public final class MockWaveData {
    public static final MockWaveData INSTANCE = new MockWaveData();

    private MockWaveData() {
    }

    public static /* synthetic */ float[] generateFloatArray$default(MockWaveData mockWaveData, int i, int i2, float f, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 1000;
        }
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            f = 500.0f;
        }
        return mockWaveData.generateFloatArray(i, i2, f);
    }

    public final float[] generateFloatArray(int sampleRate, int time, float amplitude) {
        float[] doubleArray = new float[1 * sampleRate * time];
        for (int i = 0; i < time; i++) {
            int numSamples = 1 * sampleRate;
            double frequency = 1.0d / 1;
            for (int j = 0; j < numSamples; j++) {
                double t = j / sampleRate;
                double sample = amplitude * Math.sin(6.283185307179586d * frequency * t);
                doubleArray[(i * numSamples) + j] = (float) sample;
            }
        }
        return doubleArray;
    }

    public static /* synthetic */ float[] generateFloatArrayCoords$default(MockWaveData mockWaveData, int i, int i2, float f, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 1000;
        }
        if ((i3 & 2) != 0) {
            i2 = 1;
        }
        if ((i3 & 4) != 0) {
            f = 500.0f;
        }
        return mockWaveData.generateFloatArrayCoords(i, i2, f);
    }

    public final float[] generateFloatArrayCoords(int sampleRate, int time, float amplitude) {
        float[] array = new float[1 * sampleRate * time * 2];
        for (int i = 0; i < time; i++) {
            int numSamples = 1 * sampleRate;
            double frequency = 1.0d / 1;
            for (int j = 0; j < numSamples; j++) {
                double t = j / sampleRate;
                double sample = amplitude * Math.sin(6.283185307179586d * frequency * t);
                array[((i * numSamples) + j) * 2] = (i * numSamples) + j;
                array[(((i * numSamples) + j) * 2) + 1] = (float) sample;
            }
        }
        return array;
    }

    public final float[] generateRandomData(int count, float range) {
        float[] array = new float[count * 2];
        for (int i = 0; i < count; i++) {
            float x = i * 1.0f;
            float y = ((float) (Math.random() * (1 + range))) + 3;
            array[i * 2] = x;
            array[(i * 2) + 1] = y;
        }
        return array;
    }
}